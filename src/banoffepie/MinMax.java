package banoffepie;

import banoffepie.tree.Tree;
import trifle.model.TrifleBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will be used as the main algorithm for this bot.
 * It implements the MinMax algorithm with either a neural network or a deterministic algorithm
 */
public class MinMax extends Tree {
    public static int DEPTH = 50;

    private final MinMaxStatsTracker tracker;

    public MinMax(){
        super();
        this.tracker = new MinMaxStatsTracker();
    }

    public void buildCurrentTree(BoardStatus boardStatus, int currentPlayerId, Point lastOpponentMovement) {
        this.buildCurrentTree(boardStatus, currentPlayerId, lastOpponentMovement, DEPTH);
    }

    /**
     * Determine which moves can be done
     * @param depth The current depth, we decrease the depth each time we get on a deeper level
     * @param boardStatus The current status of the board
     */
    public void buildCurrentTree(BoardStatus boardStatus, int currentPlayerId, Point lastOpponentMovement, int depth) {
        this.tracker.reset();
        this.tracker.setDepth(depth);
        this.tracker.startCounter();

        List<MinMaxPawn> movableMinMaxPawns = determineWhichMinMaxPawnsCanBeMove(boardStatus, currentPlayerId, lastOpponentMovement);

        assert movableMinMaxPawns != null;

        for (MinMaxPawn movableMinMaxPawn: movableMinMaxPawns) {
            List<Point> movesAllowed = MinMaxNode.determinePossibleMoves(movableMinMaxPawn.getCoords(), boardStatus, currentPlayerId);

            for (Point move : movesAllowed) {
                this.tracker.newNode(0);

                MinMaxNode node = new MinMaxNode(movableMinMaxPawn, move, currentPlayerId);
                node.buildTree(boardStatus, depth, tracker);
                this.getRoot().add(node);
            }
        }


        this.tracker.endTimer();
    }

    private List<MinMaxPawn> determineWhichMinMaxPawnsCanBeMove(BoardStatus boardStatus, int currentPlayerId, Point lastOpponentMovement){
        if (boardStatus.bluePawns().stream().allMatch(p -> p.getCoords().x == 0)
            && boardStatus.cyanPawns().stream().allMatch(p -> p.getCoords().x == 7)) {

            // No one has moved his pawns, so open bar, all pawns can be moved!
            if (currentPlayerId == 0){
                return boardStatus.bluePawns();
            } else {
                return boardStatus.cyanPawns();
            }
        } else {
            int opponentLastMoveMinMaxPawnColorIndices = TrifleBoard.BOARD[lastOpponentMovement.y][lastOpponentMovement.x];

            // return which pawn have this color
            List<MinMaxPawn> pawns =  currentPlayerId == 0 ? boardStatus.bluePawns() : boardStatus.cyanPawns();
            for (MinMaxPawn p: pawns) {
                if (p.getColorIndex() == opponentLastMoveMinMaxPawnColorIndices) {
                    return List.of(new MinMaxPawn(p));
                }
            }
        }
        return null;
    }

    public MinMaxStatsTracker getTracker() {
        return tracker;
    }

    public static void main(String[] args) {
        List<MinMaxPawn> bluePawns = new ArrayList<>();
        for (int y = 0; y < 8; y++) {
            bluePawns.add(new MinMaxPawn(y, 0, 0, y));
        }

        List<MinMaxPawn> cyanPawns = new ArrayList<>();
        for (int y = 0; y < 8; y++) {
            cyanPawns.add(new MinMaxPawn(y, 1, 7, y));
        }

        bluePawns.get(0).getCoords().x++;
        cyanPawns.get(2).getCoords().x = 6;
        bluePawns.get(4).getCoords().x = 3;

        Point lastMove = new Point(3, 4);

        int currentPlayerId = 1;

        BoardStatus boardStatus = new BoardStatus(bluePawns, cyanPawns);

        MinMax minMax = new MinMax();
        minMax.buildCurrentTree(boardStatus, currentPlayerId, lastMove);

        minMax.getTracker().displayStatistics();
    }
}