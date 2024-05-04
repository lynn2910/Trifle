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
    public static int DEPTH = 4;

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


        List<Point> movablePawns = determineWhichPawnsCanBeMove(boardStatus, currentPlayerId, lastOpponentMovement);

        assert movablePawns != null;

        for (Point movablePawn: movablePawns) {
            List<Point> movesAllowed = MinMaxNode.determinePossibleMoves(movablePawn, boardStatus, currentPlayerId);

            for (Point move : movesAllowed) {
                this.tracker.newNode();

                MinMaxNode node = new MinMaxNode(movablePawn, move, currentPlayerId);
                node.buildTree(boardStatus, depth, tracker);
                this.getRoot().add(node);
            }
        }


        this.tracker.endTimer();
    }

    private List<Point> determineWhichPawnsCanBeMove(BoardStatus boardStatus, int currentPlayerId, Point lastOpponentMovement){
        if (boardStatus.bluePawns().stream().allMatch(p -> p.x == 0)
            && boardStatus.cyanPawns().stream().allMatch(p -> p.x == 7)) {

            // No one has moved his pawns, so open bar, all pawns can be moved!
            if (currentPlayerId == 0){
                return boardStatus.bluePawns();
            } else {
                return boardStatus.cyanPawns();
            }
        } else {
            int opponentLastMovePawnColorIndices = TrifleBoard.BOARD[lastOpponentMovement.y][lastOpponentMovement.x];

            // return which pawn have this color
            List<Point> pawns =  currentPlayerId == 0 ? boardStatus.bluePawns() : boardStatus.cyanPawns();
            for (Point p: pawns) {
                int thisColor = TrifleBoard.BOARD[p.y][p.x];
                if (thisColor == opponentLastMovePawnColorIndices) {
                    return List.of(new Point(p.x, p.y));
                }
            }
        }
        return null;
    }

    public MinMaxStatsTracker getTracker() {
        return tracker;
    }

    public static void main(String[] args) {
        List<Point> bluePawns = new ArrayList<>();
        for (int y = 0; y < 8; y++) {
            bluePawns.add(new Point(0, y));
        }

        List<Point> cyanPawns = new ArrayList<>();
        for (int y = 0; y < 8; y++) {
            cyanPawns.add(new Point(7, y));
        }

        bluePawns.get(0).x++;
        cyanPawns.get(2).x = 6;
        bluePawns.get(4).x = 3;

        Point lastMove = new Point(3, 4);

        int currentPlayerId = 1;

        BoardStatus boardStatus = new BoardStatus(bluePawns, cyanPawns);

        MinMax minMax = new MinMax();
        minMax.buildCurrentTree(boardStatus, currentPlayerId, lastMove);

        minMax.getTracker().displayStatistics();
    }
}