package trifle.model;

import trifle.boardifier.model.ElementTypes;
import trifle.boardifier.model.GameElement;
import trifle.boardifier.model.GameStageModel;
import trifle.boardifier.view.ConsoleColor;

import java.awt.*;

/**
 * This is a pawn in the game which store what color he is.
 */
public class Pawn extends GameElement {
    public static final int PAWN_ELEMENT_ID = 50;
    public static final int BLUE_PLAYER = 1;
    public static final int CYAN_PLAYER = 2;

    public static final String[] COLORS = new String[]{
            ConsoleColor.CYAN,
            ConsoleColor.BLUE,
            ConsoleColor.PURPLE,
            ConsoleColor.WHITE,
            ConsoleColor.YELLOW,
            ConsoleColor.RED,
            ConsoleColor.GREEN,
            ConsoleColor.BLACK
    };

    public static final String[] BG_COLORS = new String[]{
            ConsoleColor.CYAN_BACKGROUND,
            ConsoleColor.BLUE_BACKGROUND,
            ConsoleColor.PURPLE_BACKGROUND,
            ConsoleColor.WHITE_BACKGROUND,
            ConsoleColor.YELLOW_BACKGROUND,
            ConsoleColor.RED_BACKGROUND,
            ConsoleColor.GREEN_BACKGROUND,
            ConsoleColor.BLACK_BACKGROUND
    };

    private final int colorIndex;
    private final int playerNumber;

    private Point coords;

    public Pawn(int colorIndex, int playerNumber, GameStageModel gameStageModel, int x, int y) {
        super(gameStageModel);

        ElementTypes.register("pawn", PAWN_ELEMENT_ID);

        this.type = ElementTypes.getType("pawn");

        this.colorIndex = colorIndex;
        this.playerNumber = playerNumber;

        this.coords = new Point(x, y);
    }

    public Point getCoords(){
        return coords;
    }
    public void setCoords(Point coords){
        this.coords = coords;
    }

    public int getColorIndex(){
        return colorIndex;
    }

    public int getPlayerNumber(){
        return playerNumber;
    }
}
