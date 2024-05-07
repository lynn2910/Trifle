package bots.banoffeepie;

import java.util.ArrayList;
import java.util.List;
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

        // We have 8 + 8 + 2 inputs, so 16 + 2 = 18
        List<Neuron> inputNeurons = new ArrayList<>();
        for (int i = 0; i < 18; i++) inputNeurons.add(Neuron.random(random).setInput(i));

        for (Neuron neuron: inputNeurons) {
            output.addChild(new NeuronLink(neuron, random.nextDouble()));
        }

        return network;
    }
}
