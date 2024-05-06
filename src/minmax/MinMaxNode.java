package minmax;

import minmax.tree.Node;
import trifle.model.TrifleBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static minmax.MinMax.MAX_WEIGHT;
import static minmax.MinMax.MIN_WEIGHT;

/**
 * This class will be used to represent a move made by one of the players.
 * It can store the board status and the information related to what move was done.
 */
public class MinMaxNode extends Node {
    /**
     * What move has been lastly played to be here
     */
    private final Point moveDone;
    private final MinMaxPawn pawn;
    private final int playerID;
    private final MinMaxAlgorithm algorithm;

    private double weight = 0.0;

    public MinMaxNode(MinMaxPawn pawn, Point moveDone, int playerID, MinMaxAlgorithm algorithm) {
        super();
        this.pawn = pawn;
        this.moveDone = moveDone;
        this.playerID = playerID;
        this.algorithm = algorithm;
    }

    public double getWeight() {
        return weight;
    }

    public MinMaxPawn getPawn(){
        return pawn;
    }

    public Point getMoveDone(){
        return moveDone;
    }

    public boolean isMaximizingPlayer(int botPlayerID) {
        return botPlayerID == playerID;
    }

    /**
     * The MiniMax algorithm
     * <br>The tree must have been built before using the `buildTree` method.
     * @param botPlayerID The ID of the bot
     * @return The weight
     */
    public double minimax(int botPlayerID) {
        if (getChildren().isEmpty())
            return getWeight();

        if (isMaximizingPlayer(botPlayerID)) {
            double maxEval = MIN_WEIGHT;

            for (Node children: getChildren()) {
                double evaluated = ((MinMaxNode) children).minimax(botPlayerID);
                maxEval = Math.max(maxEval, evaluated);
            }
            return maxEval;
        } else {
            double minEval = MAX_WEIGHT;

            for (Node children: getChildren()) {
                double evaluated = ((MinMaxNode) children).minimax(botPlayerID);
                minEval = Math.min(minEval, evaluated);
            }
            return minEval;
        }
    }

    /**
     * The MiniMax algorithm
     * <br>This version will use the default minimax algorithm and add his own weight.
     * <br>The tree must have been built before using the `buildTree` method.
     * @param botPlayerID The ID of the bot
     * @return The weight
     */
    public double minimaxFull(int botPlayerID) {
        double weight = this.minimax(botPlayerID);
        return (weight + this.weight) / 2.0;
    }

    public void buildTree(BoardStatus boardStatus, int depth, MinMaxStatsTracker tracker) {
        buildTree(boardStatus, depth, tracker, false);
    }

    private void computeWeight(BoardStatus boardStatus, MinMaxStatsTracker tracker){
        // Determine the current weight
        switch (this.algorithm) {
            case DeterministicAlgorithm: {
                long beforeWeight = System.nanoTime();
                this.weight = DeterministicAlgorithm.determineWeight(boardStatus, this.playerID, this.pawn, this.moveDone);
                long afterWeight = System.nanoTime();

                tracker.addTimeToCalculateWeight(afterWeight - beforeWeight);
                break;
            }
        }
    }

    /**
     * Calculate the weight for this node and, based on the depth, it may also create children
     * @param boardStatus The current status of the board
     * @param depth The current depth
     * @param tracker The tracker, used for statistics purposes
     */
    public void buildTree(BoardStatus boardStatus, int depth, MinMaxStatsTracker tracker, boolean calculateAllNodes) {
        tracker.newNode(depth);

        // If the depth is at 0 or if the player can win, we don't want to have another layout, so we return,
        // But first we calculate the weight!
        if (depth < 1
                ||((playerID == 0 && moveDone.x == 7) || (playerID == 1 && moveDone.x == 0)))
        {
            this.computeWeight(boardStatus, tracker);
            return;
        }

        if (calculateAllNodes) {
            this.computeWeight(boardStatus, tracker);
        }


        // Move the pawn in question
        boardStatus.movePawn(playerID, pawn.getColorIndex(), moveDone);

        int opponentID = (this.playerID + 1) % 2;
        int moveColorIndexPosition = TrifleBoard.BOARD[this.moveDone.y][this.moveDone.x];

        MinMaxPawn opponentPawn = opponentID == 0 ?
                boardStatus.bluePawns().get(moveColorIndexPosition)
                : boardStatus.cyanPawns().get(7 - moveColorIndexPosition);

        List<Point> possibleMoves = determinePossibleMoves(opponentPawn.getCoords(), boardStatus, opponentID);

        for (Point moveToDo: possibleMoves) {
            MinMaxNode opponentNode = new MinMaxNode(opponentPawn, moveToDo, opponentID, this.algorithm);
            opponentNode.buildTree(boardStatus, depth - 1, tracker, calculateAllNodes);
            this.addChild(opponentNode);
        }

        // Move back the pawn :D
        boardStatus.movePawn(playerID, pawn.getColorIndex(), pawn.getCoords());
    }

    /**
     * Determine all possible moves that can be done with this node.
     * <br><br>
     * It'll play as the `currentPlayer` (defined by `currentPlayerId`), so it can either be all pawns (start of the game) of the pawn with the color blablabla you know the drill
     * @param pawn The pawn that can be played
     * @param boardStatus The current status of the board
     * @return The list of allowed movements
     */
    public static List<Point> determinePossibleMoves(Point pawn, BoardStatus boardStatus, int playerID){
        List<Point> possibleMoves = new ArrayList<>();

        System.out.println("pawn = " + pawn);

        int[][] boardMatrix = boardStatus.generateMatrix();
        if (playerID == 0) {
            // check on the vertical
            for (int x = pawn.x + 1; x < 8; x++) {
                if (boardMatrix[x][pawn.y] == 0)
                    possibleMoves.add(new Point(x, pawn.y));
                else break;
            }

            int x = pawn.x, y = pawn.y;

            // check for the right diagonal
            while (x < 7 && y < 7){
                x++;
                y++;

                if (boardMatrix[x][y] == 0)
                    possibleMoves.add(new Point(x, y));
                else break;
            }

            x = pawn.x;
            y = pawn.y;

            // check for the left diagonal
            while (x < 7 && y > 0) {
                x++;
                y--;

                if (boardMatrix[x][y] == 0)
                    possibleMoves.add(new Point(x, y));
                else break;
            }

        }
        else {
            // check on the vertical
            for (int x = pawn.x - 1; x >= 0; x--) {
                if (boardMatrix[x][pawn.y] == 0)
                    possibleMoves.add(new Point(x, pawn.y));
                else break;
            }

            int x = pawn.x, y = pawn.y;

            // check right diagonal
            while (x > 0 && y < 7) {
                x--;
                y++;

                if (boardMatrix[x][y] == 0)
                    possibleMoves.add(new Point(x, y));
                else break;
            }

            x = pawn.x;
            y = pawn.y;

            // check left diagonal
            while (x > 0 && y > 0) {
                x--;
                y--;

                if (boardMatrix[x][y] == 0)
                    possibleMoves.add(new Point(x, y));
                else break;
            }
        }

        return possibleMoves;
    }

    @Override
    public String toString(){
        return "Node (" + this.getId() + ", " + this.weight
                + ", (" + this.pawn.getCoords().x + "," + this.pawn.getCoords().y + "), "
                + "(" + this.moveDone.x + "," + this.moveDone.y + "))";
    }
}
