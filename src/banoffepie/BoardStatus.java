package banoffepie;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a "container" to store the game status.
 */
public class BoardStatus {
    /**
     * The index of the pawn is the ID.
     */
    private List<Point> bluePawns;
    /**
     * The index of the pawn is the ID.
     */
    private List<Point> cyanPawns;

    public BoardStatus(List<Point> bluePawns, List<Point> cyanPawns) {
        this.bluePawns = bluePawns;
        this.cyanPawns = cyanPawns;
    }

    public List<Point> getBluePawns() {
        return bluePawns;
    }

    public List<Point> getCyanPawns() {
        return cyanPawns;
    }

    public List<Point> getPawns(int playerID) {
        if (playerID == 0)
            return getBluePawns();
        else
            return getCyanPawns();
    }

    public BoardStatus movePawn(int playerID, Point pawn, Point move) {
        List<Point> pawns = getPawns(playerID);
        pawns.set(pawn.y, move);
        return this;
    }

    public int[][] generateMatrix(){
        int[][] matrix = new int[8][8];

        for (Point p : bluePawns) {
            matrix[p.x][p.y] = 1;
        }
        for (Point p : cyanPawns) {
            matrix[p.x][p.y] = 2;
        }

        return matrix;
    }

    public BoardStatus clone(){
        return new BoardStatus(
                new ArrayList<>(bluePawns),
                new ArrayList<>(cyanPawns)
        );
    }
}
