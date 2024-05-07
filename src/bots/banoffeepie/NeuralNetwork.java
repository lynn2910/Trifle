package bots.banoffeepie;

import minmax.BoardStatus;
import minmax.MinMaxPawn;
import minmax.MinMaxStatsTracker;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This NeuralNetwork structure should not be used in other projects.
 * It uses a specific structure specially designed for the Kamisado with a MiniMax.
 */
public class NeuralNetwork {
    private final Neuron outputNeuron;

    public NeuralNetwork(Neuron outputNeuron){
        this.outputNeuron = outputNeuron;
    }

    public Neuron getOutputNeuron() {
        return outputNeuron;
    }

    public double compute(NNContext ctx){
        long before = System.nanoTime();
        double computed = this.outputNeuron.compute(ctx);
        long after = System.nanoTime();

        System.out.println("Prediction in " + MinMaxStatsTracker.formatTime(after - before));
        return computed;
    }

    public static void main(String[] _args) {
        // Create a fake board status
        List<MinMaxPawn> bluePawns = new ArrayList<>();
        for (int y = 0; y < 8; y++)
            bluePawns.add(new MinMaxPawn(y, 0, 0, y));

        List<MinMaxPawn> cyanPawns = new ArrayList<>();
        for (int y = 0; y < 8; y++)
            cyanPawns.add(new MinMaxPawn(y, 1, 7, y));


        BoardStatus boardStatus = new BoardStatus(bluePawns, cyanPawns);


        NeuralNetwork nn = BuilderHelper.createNetwork();

        NNContext ctx = new NNContext();
        // Player 1 (bot) try to move A1 to A2
        ctx.normalizeBoard(boardStatus, new Point(0, 0), new Point(1, 0));

        double predicted = nn.compute(ctx);
        System.out.println(predicted);
    }
}
