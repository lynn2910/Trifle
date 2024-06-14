package bots;

import minmax.BoardStatus;
import trifleConsole.boardifier.control.ActionFactory;
import trifleConsole.boardifier.control.Controller;
import trifleConsole.boardifier.model.Model;
import trifleConsole.boardifier.model.action.ActionList;
import trifleConsole.control.TrifleController;
import trifleConsole.control.TrifleDecider;
import trifleConsole.model.Pawn;
import trifleConsole.model.TrifleBoard;
import trifleConsole.model.TrifleStageModel;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BotEurdeku extends TrifleDecider {

    public BotEurdeku(Model model, Controller controller) {
        super(model, controller);
    }

    @Override
    public ActionList decide() {
        TrifleStageModel stageModel = (TrifleStageModel) model.getGameStage();
        BoardStatus boardStatus = new BoardStatus(
                stageModel.getBluePlayer(),
                stageModel.getCyanPlayer(),
                (TrifleBoard) stageModel.getBoard(),
                stageModel);
        TrifleController controller = (TrifleController) this.control;

        final int victoryLine = (model.getIdPlayer() != 0) ? 0 : 7;

        ActionList actionList;

        Point lastOpponentMove;
        if (model.getIdPlayer() == 0) {
            lastOpponentMove = stageModel.getLastCyanPlayerMove();
        } else {
            lastOpponentMove = stageModel.getLastBluePlayerMove();
        }

        List<minmax.Pawn> allowedPawns = new ArrayList<>();
        minmax.Pawn pawnToMove;


        Point backgroundLastMoveCoords = boardStatus.getLastMove((model.getIdPlayer() + 1) % 2);

        if (boardStatus.isStartOfGame() || backgroundLastMoveCoords == null) {
            List<minmax.Pawn> pawns = boardStatus.getPawns(model.getIdPlayer());
            allowedPawns = List.of(pawns.get((int) Math.floor(Math.random() * pawns.size())));
        } else {
            int backgroundColorIndex = TrifleBoard.BOARD[backgroundLastMoveCoords.x][backgroundLastMoveCoords.y];

            // get the pawn
            minmax.Pawn pawn = boardStatus.getPawn(model.getIdPlayer(), backgroundColorIndex);
            allowedPawns.add(pawn);
        }

        pawnToMove = allowedPawns.get((int) (Math.random() * allowedPawns.size()));


        Pawn pawn = stageModel.getPlayerPawn(model.getIdPlayer(), pawnToMove.getColorIndex());

        System.out.println("Minmax pawn " + pawnToMove);
        System.out.println("Pawn pawn " + pawn);

        // Algorithm

        Point move = null;

        List<Point> possibleMoves = boardStatus.getPossibleMoves(model.getIdPlayer(), pawnToMove.getCoords());

        System.out.println("possible : " + possibleMoves);
        System.out.println("tkt c'est ouam " + pawn.getCoords() + " " + victoryLine);
        System.out.println("BOARD " + TrifleBoard.BOARD[1][0]);
        System.out.println("pion 1 " + stageModel.getPlayerPawns(model.getIdPlayer()).get(0).getCoords());
        System.out.println("pion 2 " + stageModel.getPlayerPawns(model.getIdPlayer()).get(1).getCoords());
        System.out.println("pions  " + stageModel.getPlayerPawns(model.getIdPlayer()));

        for (Point point : possibleMoves) {
            if (point.getY() == victoryLine) {
                move = point;
                break;
            }
        }

        if (move == null && (!orderMoves(possibleMoves, pawn, model.getIdPlayer() != 0).isEmpty())) {
            move = orderMoves(
                possibleMoves
                , pawn
                , model.getIdPlayer() != 0
            ).get(0);
        }


        System.out.println("move to " + move);


        if (move == null && !possibleMoves.isEmpty())
            move = possibleMoves.get(0);

        // End algorithm

        if (move == null) {
            System.out.println("The bot " + model.getIdPlayer() + " cannot move his pawn.");
            stageModel.setPlayerBlocked(model.getIdPlayer(), true);

            Point opponentMove = boardStatus.getLastMove((model.getIdPlayer() + 1) % 2);
            if (lastOpponentMove != null) {

                int bgColorIndex = TrifleBoard.BOARD[opponentMove.x][opponentMove.y];

                Pawn pawnInvolved = stageModel.getPlayerPawn(model.getIdPlayer(), bgColorIndex);

                if (model.getIdPlayer() == 0) {
                    stageModel.setLastBluePlayerMove(pawnInvolved.getCoords());
                } else {
                    stageModel.setLastCyanPlayerMove(pawnInvolved.getCoords());
                }
            }

            return new ActionList();
        }
        stageModel.setPlayerBlocked(model.getIdPlayer(), false);

        actionList = ActionFactory.generatePutInContainer(
            model,
            pawn,
            TrifleBoard.BOARD_ID,
            (int) move.getY(),
            (int) move.getX()
        );

        controller.registerMove(
            stageModel,
            move,
            TrifleController.normalizeCoordinate(pawn.getCoords(), false)
                    + TrifleController.normalizeCoordinate(move, false),
            pawn
        );

        return actionList;
    }

    List<Point> orderMoves(List<Point> initialMoves, Pawn pawn, boolean isBlack) {
        if (isBlack) {
            initialMoves.sort((p1, p2) -> (p2.y - p1.y) * 10 + p2.x - p1.x);
        } else {
            initialMoves.sort((p1, p2) -> (p1.y - p2.y) * 10 + p2.x - p1.x);
        }
        List<Point> left = new ArrayList<>();
        List<Point> middle = new ArrayList<>();
        List<Point> right = new ArrayList<>();

        for (Point move : initialMoves) {
            if (move.x < pawn.getX())
                left.add(move);
            if (move.x == pawn.getX())
                middle.add(move);
            else
                right.add(move);
        }

        List<Point> orderedMoves = new ArrayList<>();

        while (!(left.isEmpty() && middle.isEmpty() && right.isEmpty())) {
            if ((pawn.getX() < 4 && isBlack) || (pawn.getX() > 4 || !isBlack)) {
                if (!right.isEmpty())
                    orderedMoves.add(right.remove(0));
                if (!middle.isEmpty())
                    orderedMoves.add(middle.remove(0));
                if (!left.isEmpty())
                    orderedMoves.add(left.remove(0));
            } else {
                if (!left.isEmpty())
                    orderedMoves.add(left.remove(0));
                if (!middle.isEmpty())
                    orderedMoves.add(middle.remove(0));
                if (!right.isEmpty())
                    orderedMoves.add(right.remove(0));
            }
        }
        int posToSwitch = Math.min(orderedMoves.size(), 3);
        for (int i=0; i<posToSwitch; i++) {
            Point tmp = orderedMoves.get(i);
            for(int j=0; j<orderedMoves.size()-1; j++) {
                orderedMoves.set(j, orderedMoves.get(j+1));
            }
            orderedMoves.set(orderedMoves.size()-1, tmp);
        }

        return orderedMoves;
    }
}
