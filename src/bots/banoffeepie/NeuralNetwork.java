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
        return this.outputNeuron.compute(ctx);
    }

    public static void main(String[] _args) {
        // Create a fake board status
        List<MinMaxPawn> bluePawns = new ArrayList<>();
        for (int y = 0; y < 8; y++)
            bluePawns.add(new MinMaxPawn(y, 0, 0, y));

        List<MinMaxPawn> cyanPawns = new ArrayList<>();
        for (int y = 0; y < 8; y++)
            cyanPawns.add(new MinMaxPawn(y, 1, 7, y));


        BoardStatus boardStatus = new BoardStatus(bluePawns, cyanPawns, null);

        NNContext ctx = new NNContext();
        // Player 1 (bot) try to move A1 to A2
        ctx.normalizeBoard(boardStatus, new Point(0, 0), new Point(1, 0));

        int iters = 10;
        double sum = 0.0;
        for (int i = 0; i < iters; i++) {
            NeuralNetwork nn = BuilderHelper.createNetwork();

            double predicted = nn.compute(ctx) * 100;
            sum += predicted;
            System.out.println(predicted);
        }
        System.out.println("Mean: " + (sum / (double) iters));
    }
}
