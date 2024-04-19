package trifle.model;

import trifle.boardifier.control.Logger;
import trifle.boardifier.model.ContainerElement;
import trifle.boardifier.model.GameStageModel;

import java.awt.Point;
import java.util.*;

public class TrifleBoard extends ContainerElement {
    public static final String BOARD_ID = "trifle_board";

    // FIXME Il y a sans doute des erreurs dedans
    public static int[][] BOARD = {
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

    public void setValidCells(int n) {
        Logger.debug("setting valid cells :D", this);
        resetReachableCells(false);

        List<Point> validCells = this.determineValidCells(n);

        // TODO définir les cases jouables par le joueur
    }

    public List<Point> determineValidCells(int n) {
        return new ArrayList<>();
    }
}
