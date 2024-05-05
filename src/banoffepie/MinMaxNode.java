package banoffepie;

import banoffepie.tree.Node;
import trifle.model.TrifleBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static banoffepie.MinMax.MAX_WEIGHT;
import static banoffepie.MinMax.MIN_WEIGHT;

/**
 * This class will be used to represent a move made by one of the players.
 * It can store the board status and the information related to what move was done.
 */
public class MinMaxNode extends Node {
    /**
     * What move has been lastly played to be here
     */
    private Point moveDone;
    private MinMaxPawn pawn;
    private int playerID;

    private double weight = 0.0;

    public MinMaxNode(MinMaxPawn pawn, Point moveDone, int playerID) {
        super();
        this.pawn = pawn;
        this.moveDone = moveDone;
        this.playerID = playerID;
    }

    public MinMaxNode() {
        super();
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
     * Calculate the weight for this node and, based on the depth, it may also create children
     * @param boardStatus The current status of the board
     * @param depth The current depth
     * @param tracker The tracker, used for statistics purposes
     */
    public void buildTree(BoardStatus boardStatus, int depth, MinMaxStatsTracker tracker) {
        tracker.newNode(depth);

        // If the depth is at 0 or if the player can win, we don't want to have another layout, so we return,
        // But first we calculate the weight!
        if (depth < 1
                ||((playerID == 0 && moveDone.x == 7) || (playerID == 1 && moveDone.x == 0)))
        {
            long beforeWeight = System.nanoTime();

            // Determine the current weight
            this.weight = DeterministicAlgorithm.determineWeight(boardStatus, this.playerID, this.pawn, this.moveDone);

            long afterWeight = System.nanoTime();
            tracker.addTimeToCalculateWeight(afterWeight - beforeWeight);
            return;
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
            MinMaxNode opponentNode = new MinMaxNode(opponentPawn, moveToDo, opponentID);
            opponentNode.buildTree(boardStatus, depth - 1, tracker);
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
        } else {
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
