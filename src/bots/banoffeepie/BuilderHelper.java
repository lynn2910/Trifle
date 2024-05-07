package bots.banoffeepie;

import java.util.Random;

public class BuilderHelper {
    /**
     * Create a new Neural Network with the wanted architecture
     * @return The network built
     */
    public static NeuralNetwork createNetwork(){
        Random random = new Random();

        NeuralNetwork network = new NeuralNetwork(Neuron.random(random));

        Neuron output = network.getOutputNeuron();

        Neuron a = Neuron.random(random);
        a.addChild(Neuron.random(random), random.nextDouble());
        a.addChild(Neuron.random(random), random.nextDouble());

        output.addChild(a, random.nextDouble());

        Neuron b = Neuron.random(random);
        b.addChild(Neuron.random(random), random.nextDouble());
        b.addChild(Neuron.random(random), random.nextDouble());

        output.addChild(b, random.nextDouble());

        return network;
    }
}
