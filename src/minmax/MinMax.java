package minmax;

import minmax.tree.Node;
import minmax.tree.Tree;
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

    public static double MAX_WEIGHT = 100;
    public static double MIN_WEIGHT = -100;

    private final MinMaxStatsTracker tracker;
    private final MinMaxAlgorithm minMaxAlgorithm;

    public MinMax(MinMaxAlgorithm algorithm){
        super();
        this.tracker = new MinMaxStatsTracker();

        this.minMaxAlgorithm = algorithm;
    }

    /**
     * Determine which moves can be done
     * @param boardStatus The current status of the board
     */
    public void buildCurrentTree(BoardStatus boardStatus, int currentPlayerId, Point lastOpponentMovement) {
        this.buildCurrentTree(boardStatus, currentPlayerId, lastOpponentMovement, DEPTH);
    }

    public void buildCurrentTree(BoardStatus boardStatus, int currentPlayerId, Point lastOpponentMovement, int depth) {
        buildCurrentTree(boardStatus, currentPlayerId, lastOpponentMovement, depth, false);
    }

    /**
     * Determine which moves can be done
     * @param depth The current depth, we decrease the depth each time we get on a deeper level
     * @param boardStatus The current status of the board
     */
    public void buildCurrentTree(BoardStatus boardStatus, int currentPlayerId, Point lastOpponentMovement, int depth, boolean calculateAllNodes) {
        this.tracker.reset();
        this.tracker.setDepth(depth);
        this.tracker.startCounter();

        List<MinMaxPawn> movableMinMaxPawns = determineWhichMinMaxPawnsCanBeMove(boardStatus, currentPlayerId, lastOpponentMovement);

        assert movableMinMaxPawns != null;

        for (MinMaxPawn movableMinMaxPawn: movableMinMaxPawns) {
            List<Point> movesAllowed = MinMaxNode.determinePossibleMoves(movableMinMaxPawn.getCoords(), boardStatus, currentPlayerId);

            for (Point move : movesAllowed) {
                this.tracker.newNode(0);

                MinMaxNode node = new MinMaxNode(movableMinMaxPawn, move, currentPlayerId, minMaxAlgorithm);
                node.buildTree(boardStatus, depth, tracker, calculateAllNodes);
                this.getRoot().add(node);
            }
        }


        this.tracker.endTimer();
    }

    /**
     *
     * @param botID The bot's ID
     * @param isFull Whether it should use the weight of each node and not only the leafs
     * @return The better node
     */
    public MinMaxNode minimax(int botID, boolean isFull) {
        tracker.startPathFinder();

        // We are maximizing the player
        MinMaxNode max = (MinMaxNode) this.getRoot().get(0);
        max.minimax(botID);

        for (Node node: this.getRoot()) {
            double evaluated;
            if (isFull) evaluated = ((MinMaxNode) node).minimaxFull(botID);
            else evaluated = ((MinMaxNode) node).minimax(botID);

            if (max.getWeight() < evaluated) {
                max = (MinMaxNode) node;
            }
        }

        tracker.endPathFinder();

        return max;
    }

    /**
     * Determine which pawns can be moved based on the board, the player ID and which move the last opponent did
     * @param boardStatus The current status of the board
     * @param currentPlayerId The ID of the bot
     * @param lastOpponentMovement The last move played by the opponent. Can be null.
     * @return The list of movable pawns
     */
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

    public void reset(){
        this.getRoot().clear();
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

        MinMax minMax = new MinMax(MinMaxAlgorithm.DeterministicAlgorithm);
        minMax.buildCurrentTree(boardStatus, currentPlayerId, lastMove, DEPTH, true);

        MinMaxNode nextMove = minMax.minimax(currentPlayerId, true);
        System.out.println("nextMove: ");
        System.out.println("  pawn: " + nextMove.getPawn());
        System.out.println("  move: " + nextMove.getMoveDone());

        minMax.getTracker().displayStatistics();
    }
}