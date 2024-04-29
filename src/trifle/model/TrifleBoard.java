package trifle.model;

import trifle.boardifier.control.Logger;
import trifle.boardifier.model.ContainerElement;
import trifle.boardifier.model.Coord2D;
import trifle.boardifier.model.GameStageModel;

import java.awt.Point;
import java.util.*;

public class TrifleBoard extends ContainerElement {
    public static final String BOARD_ID = "trifle_board";

    // FIXME Il y a sans doute des erreurs dedans
    public static final int[][] BOARD = {
            {0, 1, 2, 3, 4, 5, 6, 7},
            {5, 0, 3, 6, 1, 4, 7, 2},
            {6, 3, 0, 5, 2, 7, 3, 1},
            {3, 2, 1, 0, 7, 6, 5, 4},
            {4, 5, 6, 7, 0, 1, 2, 3},
            {1, 4, 7, 2, 5, 0, 3, 6},
            {2, 7, 4, 1, 6, 3, 0, 5},
            {7, 6, 5, 4, 3, 2, 1, 0}
    };

    public TrifleBoard(int x, int y, GameStageModel gameStageModel) {
        super(BOARD_ID, x, y, 8, 8, gameStageModel);
    }

    public void setValidCells(Point coordinates, int playerId) {
        Logger.debug("setting valid cells :D", this);
        resetReachableCells(false);

        List<Point> validCells = this.computeValidCells(coordinates, playerId);
        if (validCells != null) {
            for (Point p: validCells) {
                reachableCells[p.x][p.y] = true;
            }
        }
    }

    public List<Point> computeValidCells(Point coords, int playerId) {
        List<Point> validCells = new ArrayList<>();

        if (playerId == 0) {
            // player is based on the top and must go to the bottom

            // check on the vertical
            for (int y = coords.y + 1; y < 8; y++) {
                if (getElement(y, coords.x) == null) {
                    validCells.add(new Point(coords.x, y));
                } else break;
            }

            // check on the right diagonal
            int x = coords.x, y = coords.y;

            while (x < 7 && y < 7) {
                x++;
                y++;
                if (getElement(y, x) == null) {
                    validCells.add(new Point(x, y));
                } else break;
            }

            // now reset x and y, and do the left diagonal
            x = coords.x; y = coords.y;
            while (x > 0 && y < 7) {
                x--;
                y++;
                if (getElement(y, x) == null) {
                    validCells.add(new Point(x, y));
                } else break;
            }

            // Done.
        }
        else {
            // player is based on the bottom and must go to the top

            // The same as the upper code, but with inverse conditions
            // edited line will be commented with `+` after

            for (int y = coords.y - 1; y >= 0; y--) { // +
                if (getElement(y, coords.x) == null) {
                    validCells.add(new Point(coords.x, y));
                } else break;
            }

            // check on the right diagonal
            int x = coords.x, y = coords.y;

            while (x < 7 && y > 0) {
                x++;
                y--; // +
                if (getElement(y, x) == null) {
                    validCells.add(new Point(x, y));
                } else break;
            }

            // now reset x and y, and do the left diagonal
            x = coords.x; y = coords.y;
            while (x > 0 && y > 0) { // +
                x--;
                y--; // +
                if (getElement(y, x) == null) {
                    validCells.add(new Point(x, y));
                } else break;
            }
        }

        return validCells;
    }

}
