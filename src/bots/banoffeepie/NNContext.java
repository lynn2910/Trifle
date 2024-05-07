package bots.banoffeepie;

import minmax.BoardStatus;
import minmax.MinMaxPawn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Store useful information for the Neural Network
 */
public class NNContext {
    public List<Double> normalizedInputs;


    public NNContext() {
        this(new ArrayList<>());
    }
    public NNContext(List<Double> normalizedInputs) {
        this.normalizedInputs = normalizedInputs;
    }

    public void normalizeBoard(BoardStatus boardStatus, Point pawnCoords, Point move) {
        List<MinMaxPawn> bluePawns = boardStatus.bluePawns();
        for (MinMaxPawn pawn: bluePawns) {
            normalizedInputs.add(normalizeCoordinates(pawn.getCoords()));
        }

        normalizedInputs.add(normalizeCoordinates(pawnCoords));
        normalizedInputs.add(normalizeCoordinates(move));
    }

    public static double normalizeCoordinates(Point p) {
        return p.getX() * 8 + p.getY();
    }

    public Double getInput(int i) {
        return normalizedInputs.get(i);
    }
}
