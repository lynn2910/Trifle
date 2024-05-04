package banoffepie;

import banoffepie.tree.Node;
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
    public static int DEPTH = 0;

    public MinMax(){
        super();
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
        List<Point> movablePawns = determineWhichPawnsCanBeMove(boardStatus, currentPlayerId, lastOpponentMovement);

        assert movablePawns != null;

        System.out.println("movablePawns: ");
        for (Point movablePawn : movablePawns) {
            System.out.println(movablePawn);
        }
        System.out.println();

        for (Point movablePawn: movablePawns) {
            List<Point> movesAllowed = MinMaxNode.determinePossibleMoves(movablePawn, boardStatus, currentPlayerId);

            System.out.println("movablePawn  = " + movablePawn);
            System.out.println("movesAllowed = " + movesAllowed);

            for (Point move : movesAllowed) {
                MinMaxNode node = new MinMaxNode(movablePawn, move, currentPlayerId);
                node.buildTree(boardStatus, depth);
                this.getRoot().add(node);
            }
            System.out.println();
        }
    }

    private List<Point> determineWhichPawnsCanBeMove(BoardStatus boardStatus, int currentPlayerId, Point lastOpponentMovement){
        if (boardStatus.getBluePawns().stream().allMatch(p -> p.x == 0)
            && boardStatus.getCyanPawns().stream().allMatch(p -> p.x == 7)) {

            // No one has moved his pawns, so open bar, all pawns can be moved!
            if (currentPlayerId == 0){
                return boardStatus.getBluePawns();
            } else {
                return boardStatus.getCyanPawns();
            }
        } else {
            int opponentLastMovePawnColorIndices = TrifleBoard.BOARD[lastOpponentMovement.y][lastOpponentMovement.x];

            // return which pawn have this color
            List<Point> pawns =  currentPlayerId == 0 ? boardStatus.getBluePawns() : boardStatus.getCyanPawns();
            for (Point p: pawns) {
                int thisColor = TrifleBoard.BOARD[p.y][p.x];
                if (thisColor == opponentLastMovePawnColorIndices) {
                    return List.of(new Point(p.x, p.y));
                }
            }
        }
        return null;
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
        int currentPlayerId = 1; // 0

        BoardStatus boardStatus = new BoardStatus(bluePawns, cyanPawns);

        MinMax minMax = new MinMax();
        minMax.buildCurrentTree(boardStatus, currentPlayerId, new Point(1, 0));

//        minMax.getRoot().stream().map(Node::formatToString).forEach(System.out::println);
    }
}