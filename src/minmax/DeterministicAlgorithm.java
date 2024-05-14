package minmax;

import java.util.List;
import java.awt.*;
import java.util.Random;

/**
 * A "simple" algorithm that can calculate a weight based on the move
 */
public class DeterministicAlgorithm {
    private static Random random = new Random();

    /**
     * Determine if whether the move is good or bad
     * <br>
     * The weight can be from 100 and -100, where 100 is a VERY GOOD move, and -100 the worst decision of your life
     * @param boardStatus The status of the board at this point
     * @param playerID The player's id
     * @param pawn The pawn who we want to move
     * @param move The move that will be calculated
     * @return The calculated weight between [100; -100]
     */
    public static double determineWeight(
            BoardStatus boardStatus,
            int playerID,
            Pawn pawn,
            Point move,
            int depth
    )
    {

        double w = 0.0;

        if (boardStatus.getPawns(0).stream().anyMatch(p -> p.getCoords().y == 7)
            || boardStatus.getPawns(1).stream().anyMatch(p -> p.getCoords().y == 0))
            w = 100;
        else w = random.nextDouble(-90, 90);

        w *= (depth / (double) MinMax.DEPTH);

        return w;
    }
}
