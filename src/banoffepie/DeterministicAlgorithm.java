package banoffepie;

import java.awt.*;

/**
 * A "simple" algorithm that can calculate a weight based on the move
 */
public class DeterministicAlgorithm {
    /**
     * Determine if whether the move is good or bad
     * @param boardStatus The status of the board at this point
     * @param playerId The player's id
     * @param pawn The pawn who we want to move
     * @param move The move that will be calculated
     * @return The calculated weight
     */
    public static double determineWeight(BoardStatus boardStatus, int playerId, Point pawn, Point move){
        return 0.0;
    }
}
