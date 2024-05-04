package banoffepie;

import banoffepie.tree.Node;
import trifle.model.TrifleBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will be used to represent a move made by one of the players.
 * It can store the board status and the information related to what move was done.
 * <br><br>
 * It'll eat the ram like nothing else, later optimizations should be done
 */
public class MinMaxNode extends Node {
    /**
     * What move has been lastly played to be here
     */
    private Point moveDone;
    private Point pawn;
    private int playerID;

    private double weight = 0.0;

    public MinMaxNode(Point pawn, Point moveDone, int playerID) {
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

    /**
     * Calculate the weight for this node and, based on the depth, it may also create children
     * @param boardStatus The current status of the board
     * @param depth The current depth
     * @param tracker The tracker, used for statistics purposes
     */
    public void buildTree(BoardStatus boardStatus, int depth, MinMaxStatsTracker tracker) {
        long beforeWeight = System.nanoTime();

        // Determine the current weight
        this.weight = DeterministicAlgorithm.determineWeight(boardStatus, this.playerID, this.pawn, this.moveDone);

        long afterWeight = System.nanoTime();
        tracker.addTimeToCalculateWeight(afterWeight - beforeWeight);

        // If the depth is at 0, we don't want to have another layout, so we return
        if (depth < 1)
            return;

        // Clone the board
        BoardStatus newBoardStatus = boardStatus.cloneBoard();

        // Move the pawn (by this node)
        if (this.playerID == 0) {
            newBoardStatus.bluePawns().set(this.pawn.y, moveDone);
        } else {
            newBoardStatus.cyanPawns().set(this.pawn.y, moveDone);
        }


        int opponentID = this.playerID == 0 ? 1 : 0;
        int moveColorIndexPosition = TrifleBoard.BOARD[this.moveDone.y][this.moveDone.x];

        Point opponentPawn = opponentID == 0 ?
                newBoardStatus.bluePawns().get(moveColorIndexPosition)
                : newBoardStatus.cyanPawns().get(7 - moveColorIndexPosition);

        List<Point> possibleMoves = determinePossibleMoves(opponentPawn, boardStatus, opponentID);

        for (Point moveToDo: possibleMoves) {
            tracker.newNode();
            MinMaxNode opponentNode = new MinMaxNode(opponentPawn, moveToDo, opponentID);
            opponentNode.buildTree(newBoardStatus, depth - 1, tracker);
            this.addChild(opponentNode);
        }
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
                + ", (" + this.pawn.x + "," + this.pawn.y + "), "
                + "(" + this.moveDone.x + "," + this.moveDone.y + "))";
    }
}
