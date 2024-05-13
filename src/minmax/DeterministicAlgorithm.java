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
            MinMaxPawn pawn,
            Point move
    )
    {
        if (boardStatus.getPawns(0).stream().anyMatch(p -> p.getCoords().y == 7)
            || boardStatus.getPawns(1).stream().anyMatch(p -> p.getCoords().y == 0))
            return 100;

        return random.nextDouble(-90, 90);
//        double weight = 0.0;
//
//        int[][] matrix = boardStatus.generateMatrix();
//
//        // Pawn advancement
//        int distanceToGoal = Math.abs(move.y - (playerID == 0 ? 7 : 0));
//        weight += distanceToGoal;
//
//        // How is the opponent a threat or not
//        List<MinMaxPawn> opponentPawns = boardStatus.getPawns((playerID + 1) % 2);
//        for (MinMaxPawn opponentPawn : opponentPawns) {
//            if (canWin(matrix, move, opponentPawn.getCoords(), (playerID + 1) % 2)) {
//                weight -= 20; // Significant penalty for being captured
//            }
//        }
//
//        // Proximity to the goal
//        weight += 5 - Math.abs(move.y - (playerID == 0 ? 7 : 0));
//
//        // Defensive positioning (bonus for surrounding pawns)
//        int friendlyNeighbours = 0;
//        for (int dx = -1; dx <= 1; dx++) {
//            for (int dy = -1; dy <= 1; dy++) {
//                int newX = move.x + dx;
//                int newY = move.y + dy;
//                if (isOnBoard(newX, newY) && matrix[newX][newY] == playerID) {
//                    friendlyNeighbours++;
//                }
//            }
//        }
//        weight += friendlyNeighbours * 3;
//
//        // Opponent movement restriction (bonus for limiting opponent options)
//        int opponentMovesBefore = MinMaxNode.determinePossibleMoves(
//                opponentPawns.get(0).getCoords(),
//                boardStatus,
//                (playerID + 1) % 2
//        ).size();
//
//        int opponentMovesAfter = MinMaxNode.determinePossibleMoves(
//                opponentPawns.get(0).getCoords(),
//                boardStatus.cloneBoard().movePawn(playerID, pawn.getColorIndex(), move),
//                (playerID + 1) % 2
//        ).size();
//        weight += (opponentMovesBefore - opponentMovesAfter) * 2;
//
//        return weight;
    }

    private static boolean isOnBoard(int newX, int newY) {
        return newX >= 0 && newY >= 0 && newX < 7 && newY < 7;
    }

    public static boolean canWin(int[][] matrix, Point move, Point pawn, int playerId) {
        if (playerId == 0) {
            for (int x = pawn.x; x <= 7; x++) {
                if (matrix[x][pawn.y] != 0 && x != move.x && pawn.y != move.y)
                    return false;

                if (x == 7)
                    return true;
            }
        } else {
            for (int x = pawn.x; x >= 0; x--) {
                if (matrix[x][pawn.y] != 0 && x != move.x && pawn.y != move.y)
                    return false;

                if (x == 0)
                    return true;
            }
        }
        return false;
    }
}
