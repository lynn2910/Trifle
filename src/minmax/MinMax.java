package minmax;

import minmax.tree.Node;
import minmax.tree.Tree;
import trifle.model.TrifleBoard;
import trifle.rules.PlayerMode;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static String trainingPath;
    public static FileWriter trainingDataFileWriter;

    public MinMax(MinMaxAlgorithm algorithm){
        super();
        this.tracker = new MinMaxStatsTracker();

        this.minMaxAlgorithm = algorithm;
    }

    public static FileWriter getTrainingDataFileWriter() throws IOException {
        if (trainingPath == null)
            throw new NullPointerException("trainingDataFileWriter is null");

        if (trainingDataFileWriter == null){
            trainingDataFileWriter = new FileWriter(trainingPath, true);
        }

        return trainingDataFileWriter;
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

        if (movableMinMaxPawns.isEmpty()) {
            System.out.println("No move can be played, the bot cannot move...");
            return;
        }

        for (MinMaxPawn movableMinMaxPawn: movableMinMaxPawns) {
            List<Point> movesAllowed = MinMaxNode.determinePossibleMoves(
                    new Point(
                            movableMinMaxPawn.getCoords().y,
                            movableMinMaxPawn.getCoords().x
                    ),
                    boardStatus,
                    currentPlayerId
            );

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
    public MinMaxNode minimax(int botID, PlayerMode playerMode, boolean isFull) {
        if (this.getRoot().isEmpty())
            return null;

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

        max.getMoveDone().y = 7 - max.getMoveDone().y;

        if (botID == 0) return max;
        else {
            max.getMoveDone().y = 7 - max.getMoveDone().y;
            return max;
        }
    }

    /**
     * Determine which pawns can be moved based on the board, the player ID and which move the last opponent did
     * @param boardStatus The current status of the board
     * @param currentPlayerId The ID of the bot
     * @param lastOpponentMovement The last move played by the opponent. Can be null.
     * @return The list of movable pawns
     */
    private List<MinMaxPawn> determineWhichMinMaxPawnsCanBeMove(
            BoardStatus boardStatus,
            int currentPlayerId,
            Point lastOpponentMovement
    )
    {
        if (boardStatus.bluePawns().stream().allMatch(p -> p.getCoords().x == 0)
            && boardStatus.cyanPawns().stream().allMatch(p -> p.getCoords().x == 7)) {

            // No one has moved his pawns, so open bar, all pawns can be moved!
            return boardStatus.getPawns(currentPlayerId);
        } else {
            if (lastOpponentMovement == null)
                return boardStatus.getPawns(currentPlayerId);

            int opponentLastMoveMinMaxPawnColorIndices = TrifleBoard.BOARD[lastOpponentMovement.y][currentPlayerId == 0 ? 7 - lastOpponentMovement.x : lastOpponentMovement.x];

            // return which pawn have this color
            List<MinMaxPawn> pawns = boardStatus.getPawns(currentPlayerId);

            for (MinMaxPawn p: pawns) {
                if (p.getColorIndex() == opponentLastMoveMinMaxPawnColorIndices) {
                    return List.of(
                        new MinMaxPawn(
                                p.getColorIndex(),
                                currentPlayerId,
                                p.getCoords().x,
                                p.getCoords().y
                            )
                    );
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

//    public static void main(String[] args) {
//        List<MinMaxPawn> bluePawns = new ArrayList<>();
//        for (int y = 0; y < 8; y++) {
//            bluePawns.add(new MinMaxPawn(y, 0, 0, y));
//        }
//
//        List<MinMaxPawn> cyanPawns = new ArrayList<>();
//        for (int y = 0; y < 8; y++) {
//            cyanPawns.add(new MinMaxPawn(y, 1, 7, y));
//        }
//
//        bluePawns.get(0).getCoords().x++;
//        cyanPawns.get(2).getCoords().x = 6;
//        bluePawns.get(4).getCoords().x = 3;
//
//        Point lastMove = new Point(3, 4);
//
//        int currentPlayerId = 1;
//
//        BoardStatus boardStatus = new BoardStatus(bluePawns, cyanPawns, null);
//
//        MinMax minMax = new MinMax(MinMaxAlgorithm.DeterministicAlgorithm);
//        minMax.buildCurrentTree(boardStatus, currentPlayerId, lastMove, DEPTH, true);
//
//        MinMaxNode nextMove = minMax.minimax(currentPlayerId, true);
//
//        minMax.getTracker().displayStatistics();
//    }
}