package minmax;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.util.Optional;
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
        return boardStatus.isWin() ? 10000 * (depth / (double) MinMax.DEPTH)
                : notAWin(boardStatus, playerID, pawn, move, depth);
    }

    private static double notAWin(
            BoardStatus boardStatus,
            int playerID,
            Pawn pawn,
            Point move,
            int depth
    )
    {
        // offensive
        double w = 0.0;


        // block the pawns of the opponent
        for (Pawn opponentPawn: boardStatus.getPawns((playerID + 1) % 2)) {
            List<Point> possibleMoves = boardStatus.getPossibleMoves(
                    (playerID + 1) % 2,
                    opponentPawn.getCoords()
            );

            boolean canWin = possibleMoves.stream()
                    .anyMatch(m -> playerID == 0 ? m.x == 0 : m.x == 7);

            for (Point possibleMove: possibleMoves) {
                if (possibleMove == move)
                    w += canWin ? 50 : 25;
            }
        }


        // detect bot pawns that will be blocked by this move
        List<Pawn> pawnBlocked = getPawnsInTrajectory(
                boardStatus,
                move,
                (playerID + 1) % 2,
                boardStatus.getPawns(playerID)
        );

        w /= pawnBlocked.size();

        if (w > 100) {
            w = w / 100;
        } else if (w < -100) {
            w = w / 100;
        }

        return w;
    }

    private static List<Pawn> getPawnsInTrajectory(BoardStatus boardStatus, Point move, int playerID, List<Pawn> opponentPawns){
        List<Pawn> opponentPawnsInTrajectory = new ArrayList<>();

        if (playerID == 0) {

        } else {
            for (int x = move.x - 1; x > 0; x--) {
                Optional<Pawn> maybe = isOpponentAt(opponentPawns, x, move.y);
                if (maybe.isPresent()) {
                    opponentPawnsInTrajectory.add(maybe.get());
                    break;
                }
            }
        }

        return opponentPawnsInTrajectory;
    }

    private static Optional<Pawn> isOpponentAt(List<Pawn> opponentPawns, int x, int y) {
        for (Pawn op: opponentPawns) {
            if (op.getCoords().y == y && op.getCoords().x == x) {
                return Optional.of(op);
            }
        }
        return Optional.empty();
    }
}
