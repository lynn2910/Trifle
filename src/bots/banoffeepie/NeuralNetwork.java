package bots.banoffeepie;

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
        NeuralNetwork nn = BuilderHelper.createNetwork();

        NNContext ctx = new NNContext(List.of(0.5, 0.5));

        double predicted = nn.compute(ctx);
        System.out.println(predicted);
    }
}
