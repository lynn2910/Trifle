package minmax;

import trifle.model.TrifleStageModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a "container" to store the game status.
 */
public record BoardStatus(List<MinMaxPawn> bluePawns, List<MinMaxPawn> cyanPawns, TrifleStageModel stageModel) {
    public List<MinMaxPawn> getPawns(int playerID) {
        if (playerID == 0) return bluePawns();
        else return cyanPawns();
    }

    public void movePawn(int playerID, int pawnIndex, Point move) {
        List<MinMaxPawn> pawns = getPawns(playerID);
        pawns.get(pawnIndex).setCoords(move);
    }

    public MinMaxPawn getPawn(int playerID, int pawnIndex) {
        if (playerID == 0) return bluePawns().get(pawnIndex);
        else return cyanPawns().get(pawnIndex);
    }

    public int[][] generateMatrix() {
        int[][] matrix = new int[8][8];

        for (MinMaxPawn p : bluePawns) {
            matrix[p.getCoords().y][p.getCoords().x] = 1;
        }
        for (MinMaxPawn p : cyanPawns) {
            matrix[p.getCoords().y][p.getCoords().x] = 2;
        }

        return matrix;
    }

    public BoardStatus cloneBoard() {
        return new BoardStatus(
                new ArrayList<>(bluePawns),
                new ArrayList<>(cyanPawns),
                this.stageModel
        );
    }
}
