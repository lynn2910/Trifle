package trifle.model;

import trifle.boardifier.model.ElementTypes;
import trifle.boardifier.model.GameElement;
import trifle.boardifier.model.GameStageModel;
import trifle.boardifier.view.ConsoleColor;

/**
 * This is a pawn in the game which store what color he is.
 */
public class Pawn extends GameElement {
    public static int PAWN_ELEMENT_ID = 50;
    public static int BLUE_PLAYER = 1;
    public static int CYAN_PLAYER = 2;

    public static String[] COLORS = new String[]{
            ConsoleColor.CYAN,
            ConsoleColor.BLUE,
            ConsoleColor.PURPLE,
            ConsoleColor.WHITE,
            ConsoleColor.YELLOW,
            ConsoleColor.RED,
            ConsoleColor.GREEN,
            ConsoleColor.BLACK
    };

    public static String[] BG_COLORS = new String[]{
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

    public Pawn(int colorIndex, int playerNumber, GameStageModel gameStageModel) {
        super(gameStageModel);

        ElementTypes.register("pawn", PAWN_ELEMENT_ID);

        this.type = ElementTypes.getType("pawn");

        this.colorIndex = colorIndex;
        this.playerNumber = playerNumber;
    }

    public int getColorIndex(){
        return colorIndex;
    }

    public int getPlayerNumber(){
        return playerNumber;
    }
}
