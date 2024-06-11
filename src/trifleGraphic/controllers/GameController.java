package trifleGraphic.controllers;

import trifleConsole.boardifier.view.ConsoleColor;
import trifleConsole.model.Pawn;
import trifleGraphic.boardifierGraphic.control.Controller;
import trifleGraphic.boardifierGraphic.model.Model;
import trifleGraphic.boardifierGraphic.model.Player;
import trifleGraphic.boardifierGraphic.view.View;
import trifleGraphic.model.TrifleBoard;
import trifleGraphic.model.TrifleStageModel;

import java.awt.*;

public class GameController extends Controller {
    public GameController(Model model, View view) {
        super(model, view);

        setControlAction(new GameActionController(model, view, this));
        setControlMouse(new GameMouseController(model, view, this));
    }

    @Override
    public void endOfTurn(){
        System.out.println("End of turn");
        model.setNextPlayer();

        TrifleStageModel stageModel = (TrifleStageModel) model.getGameStage();
        stageModel.setState(TrifleStageModel.SELECT_PAWN_STATE);

        System.out.println();
        model.setNextPlayer();

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

        if (colored)
            sb += ConsoleColor.RESET;

        return sb;
    }

}
