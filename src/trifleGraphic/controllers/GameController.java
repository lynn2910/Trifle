package trifleGraphic.controllers;

import trifleGraphic.boardifierGraphic.control.Controller;
import trifleGraphic.boardifierGraphic.model.Model;
import trifleGraphic.boardifierGraphic.model.Player;
import trifleGraphic.boardifierGraphic.view.View;
import trifleGraphic.model.Pawn;
import trifleGraphic.model.TrifleBoard;
import trifleGraphic.model.TrifleStageModel;

import java.awt.*;

public class GameController extends Controller {
    public GameController(Model model, View view) {
        super(model, view);

        setControlAction(new GameActionController(model, view, this));
        setControlMouse(new GameMouseController(model, view, this));
    }

    public void registerMove(TrifleStageModel gameStage, Point moveCoordinates, String move, Pawn pawn){
        if (model.getIdPlayer() == 0) {
            gameStage.setLastBluePlayerMove(moveCoordinates);
        } else {
            gameStage.setLastCyanPlayerMove(moveCoordinates);
        }

        pawn.setCoords(moveCoordinates);

//        this.addOldMoveToFile(move);
//        this.addMoveToOldMoves(model.getCurrentPlayer(), pawn.getFormattedPawnId(), normalizeCoordinate(moveCoordinates, true));

        // !! Last one !!
//        this.detectWin();
    }

    @Override
    public void endOfTurn(){
        System.out.println("End of turn");
        System.out.println("PlayerID: " + model.getIdPlayer());
        model.setNextPlayer();
        System.out.println("PlayerID: " + model.getIdPlayer());

        TrifleStageModel stageModel = (TrifleStageModel) model.getGameStage();
        stageModel.setState(TrifleStageModel.SELECT_PAWN_STATE);

        Player p = model.getCurrentPlayer();
        String text = p.getName() + " is playing.";

        Point lastOpponentMove = model.getIdPlayer() == 0 ? stageModel.getLastCyanPlayerMove() : stageModel.getLastBluePlayerMove();
        if (lastOpponentMove == null) {
            text += " You start, so you can choose which pawn your moving.";
        }
        else {
            // Tell which color must be moved
            int colorIndex = TrifleBoard.BOARD[lastOpponentMove.y][lastOpponentMove.x];

            text += " You must play the ";

            text += switch (colorIndex) {
                case 0 -> "Cyan";
                case 1 -> "Blue";
                case 2 -> "Purple";
                case 3 -> "White";
                case 4 -> "Yellow";
                case 5 -> "Red";
                case 6 -> "Green";
                case 7 -> "Black";
                default -> "Unknown";
            };

            text += " at ";
            text += normalizeCoordinate(
                    stageModel.getPlayerPawn(model.getIdPlayer(), colorIndex).getCoords(),
                    true
            );
        }

        stageModel.getPlayerName().setText(text);

        super.endOfTurn();
    }

    /**
     * Normalize a coordinate from a `Point` to a String like `G7`
     * @param coordinates The coordinates to normalize
     * @param colored If the output string should have colors
     * @return The normalized coordinate
     */
    public static String normalizeCoordinate(Point coordinates, boolean colored) {
        String sb = "";

        System.out.println(coordinates);

        if (colored) {
            int colorIndex = trifleConsole.model.TrifleBoard.BOARD[coordinates.y][coordinates.x];
            sb += Pawn.COLORS[colorIndex];
        }

        sb += ((char) (coordinates.x + 65)) + "";
        sb += (coordinates.y + 1);

        return sb;
    }

}
