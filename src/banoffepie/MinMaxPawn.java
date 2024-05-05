package banoffepie;

import trifle.boardifier.model.ElementTypes;
import trifle.boardifier.model.GameStageModel;

import java.awt.*;

public class MinMaxPawn {
    private final int colorIndex;
    private final int playerNumber;

    private Point coords;

    public MinMaxPawn(MinMaxPawn pawn) {
        this(pawn.getColorIndex(), pawn.getPlayerNumber(), pawn.getCoords().x, pawn.getCoords().y);
    }

    public MinMaxPawn(int colorIndex, int playerNumber, int x, int y) {
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

    @Override
    public String toString() {
        return "Pawn { id: " + this.colorIndex
                + ", player: " + this.playerNumber
                + ", coordinates: (" + this.coords.x + ", " + this.coords.y + ") }";
    }
}
