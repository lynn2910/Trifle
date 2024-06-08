package trifleGraphic.model;

import trifleGraphic.boardifierGraphic.model.ElementTypes;
import trifleGraphic.boardifierGraphic.model.GameElement;
import trifleGraphic.boardifierGraphic.model.GameStageModel;

import java.awt.*;

import static trifleGraphic.view.TrifleBoardLook.PAWN_SIZE;

/**
 * This is a pawn in the game which store what color he is.
 */
public class Pawn extends GameElement {
    public static final int PAWN_ELEMENT_ID = 50;
    public static final int BLUE_PLAYER = 1;
    public static final int CYAN_PLAYER = 2;

    // FIXME on peut enfin mettre les vraies couleurs!
    public static final Color[] COLORS = new Color[]{
            Color.CYAN,
            Color.BLUE,
            Color.PINK,
            Color.WHITE,
            Color.YELLOW,
            Color.RED,
            Color.GREEN,
            Color.DARK_GRAY
    };

    public static final Color[] BG_COLORS = COLORS;

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
        this.setLocation((x + 1) * PAWN_SIZE - 2, (y + 1) * PAWN_SIZE + 7);
    }

    public Point getCoords(){
        return coords;
    }
    public void setCoords(Point coords){
        this.coords = coords;
        // IMPORTANT Update the location every fucking time!
        this.setLocation((coords.x + 1) * PAWN_SIZE - 2, (coords.y + 1) * PAWN_SIZE + 7);
    }

    public int getColorIndex(){
        return colorIndex;
    }

    /**
     * Return the chinese
     * @return The corresponding pin-yang
     */
    public String getChinesePawnName(){
        return switch (this.getColorIndex()) {
            case 0 -> "褐";
            case 1 -> "青";
            case 2 -> "赤";
            case 3 -> "黄";
            case 4 -> "红";
            case 5 -> "紫";
            case 6 -> "蓝";
            case 7 -> "橙";
            default -> "?";
        };
    }

    public int getPlayerNumber(){
        return playerNumber;
    }

    /**
     * Format the pawn information
     * @return Return a string in the form of "Cyan (A1)
     */
    public String getFormattedPawnId() {
        // TODO jsp comment je vais devoir faire
//        Color sb = COLORS[colorIndex];
//        sb += switch (this.getColorIndex()) {
//            case 0 -> "Cyan  ";
//            case 1 -> "Blue  ";
//            case 2 -> "Purple";
//            case 3 -> "White ";
//            case 4 -> "Yellow";
//            case 5 -> "Red   ";
//            case 6 -> "Green ";
//            case 7 -> "Black ";
//            default -> "Unknown";
//        };
//        sb += ConsoleColor.RESET;
//
//        sb +=" (";
//        sb += (char) (this.getCoords().x + 65);
//        sb += (this.getCoords().y + 1);
//        sb += ')';
//
//        return sb;
        return "";
    }

    @Override
    public String toString(){
        return "Pawn { colorIndex: " + colorIndex
                + ", playerNumber: " + playerNumber
                + ", coords: (" + coords.x + ", " + coords.y  + ") }";
    }
}
