package banoffepie;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a "container" to store the game status.
 *
 * @param bluePawns The index of the pawn is the ID.
 * @param cyanPawns The index of the pawn is the ID.
 */
public record BoardStatus(List<MinMaxPawn> bluePawns, List<MinMaxPawn> cyanPawns) {
    public List<MinMaxPawn> getPawns(int playerID) {
        if (playerID == 0)
            return bluePawns();
        else
            return cyanPawns();
    }

    public BoardStatus movePawn(int playerID, int pawnIndex, Point move) {
        List<MinMaxPawn> pawns = getPawns(playerID);
        pawns.get(pawnIndex).setCoords(move);
        return this;
    }

    public int[][] generateMatrix() {
        int[][] matrix = new int[8][8];

        for (MinMaxPawn p : bluePawns) {
            matrix[p.getCoords().x][p.getCoords().y] = 1;
        }
        for (MinMaxPawn p : cyanPawns) {
            matrix[p.getCoords().x][p.getCoords().y] = 2;
        }

        return matrix;
    }

    public BoardStatus cloneBoard() {
        return new BoardStatus(
                new ArrayList<>(bluePawns),
                new ArrayList<>(cyanPawns)
        );
    }
}
