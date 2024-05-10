package bots.banoffeepie;

import minmax.BoardStatus;
import minmax.MinMaxPawn;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Store useful information for the Neural Network
 */
public class NNContext {
    public List<Double> normalizedInputs;
    public HashMap<Integer, Double> computedValues = new HashMap<>();


    public NNContext() {
        this(new ArrayList<>());
    }
    public NNContext(List<Double> normalizedInputs) {
        this.normalizedInputs = normalizedInputs;
    }

    public void addComputedValue(int id, double value){
        computedValues.put(id, value);
    }
    public boolean hasComputedValue(int id){
        return computedValues.containsKey(id);
    }
    public double getComputedValue(int id){
        return computedValues.get(id);
    }

    public void normalizeBoard(BoardStatus boardStatus, Point pawnCoords, Point move) {
        for (MinMaxPawn pawn: boardStatus.bluePawns())
            normalizedInputs.add(normalizeCoordinates(pawn.getCoords()));

        for (MinMaxPawn pawn: boardStatus.cyanPawns())
            normalizedInputs.add(normalizeCoordinates(pawn.getCoords()));

        normalizedInputs.add(normalizeCoordinates(pawnCoords));
        normalizedInputs.add(normalizeCoordinates(move));
    }

    public static double normalizeCoordinates(Point p) {
        return (p.getX() * 8.0 + p.getY()) / 64.0;
    }

    public Double getInput(int i) {
        return normalizedInputs.get(i);
    }
}
