package bots;

import minmax.BoardStatus;
import minmax.MinMax;
import minmax.Node;
import trifle.boardifier.control.ActionFactory;
import trifle.boardifier.control.Controller;
import trifle.boardifier.model.Model;
import trifle.boardifier.model.action.ActionList;
import trifle.control.TrifleController;
import trifle.control.TrifleDecider;
import trifle.model.Pawn;
import trifle.model.TrifleBoard;
import trifle.model.TrifleStageModel;

import java.awt.*;

public class DeterministicMinMaxBot extends TrifleDecider {
    private final MinMax minMax;

    public DeterministicMinMaxBot(Model model, Controller controller) {
        super(model, controller);

        this.minMax = new MinMax();
    }

    @Override
    public ActionList decide() {
        TrifleStageModel stageModel = (TrifleStageModel) model.getGameStage();
        TrifleController controller = (TrifleController) this.control;

        BoardStatus boardStatus = new BoardStatus(
                stageModel.getBluePlayer(),
                stageModel.getCyanPlayer(),
                (TrifleBoard) stageModel.getBoard(),
                stageModel
        );

        this.minMax.reset();
        this.minMax.buildTree(boardStatus, model.getIdPlayer());

        Node nextMove = this.minMax.minimax(model.getIdPlayer());
        if (nextMove == null) {
            System.out.println("The bot " + model.getIdPlayer() + " cannot move his pawn.");
            stageModel.setPlayerBlocked(model.getIdPlayer(), true);

            Point lastOpponentMove = boardStatus.getLastMove((model.getIdPlayer() + 1) % 2);
            if (lastOpponentMove != null) {

                int bgColorIndex = TrifleBoard.BOARD[lastOpponentMove.x][lastOpponentMove.y];

                Pawn pawn = stageModel.getPlayerPawn(model.getIdPlayer(), bgColorIndex);

                if (model.getIdPlayer() == 0) {
                    stageModel.setLastBluePlayerMove(pawn.getCoords());
                } else {
                    stageModel.setLastCyanPlayerMove(pawn.getCoords());
                }
            }

            return new ActionList();
        }
        stageModel.setPlayerBlocked(model.getIdPlayer(), false);
        System.out.println("Choice of the MinMax:\n" + nextMove);

        this.minMax.getTracker().displayStatistics();
        this.minMax.getTracker().sendStatisticsToApi();

        Pawn pawnInvolved = stageModel.getPlayerPawn(
                model.getIdPlayer(),
                nextMove.getPawnInvolved().getColorIndex()
        );

        assert pawnInvolved != null;

        ActionList actions = ActionFactory.generatePutInContainer(
                model,
                pawnInvolved,
                TrifleBoard.BOARD_ID,
                nextMove.getMoveDone().y,
                nextMove.getMoveDone().x
        );
        actions.setDoEndOfTurn(true);

        controller.registerMove(
                stageModel,
                nextMove.getMoveDone(),
                TrifleController.normalizeCoordinate(pawnInvolved.getCoords(), false)
                    + TrifleController.normalizeCoordinate(nextMove.getMoveDone(), false),
                pawnInvolved
        );

        return actions;
    }
}
