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

        // create a second layer only for the 16 first inputs
        List<Neuron> firstLayer = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Neuron neuron = Neuron.random(random);

            for (Neuron input : inputNeurons)
                neuron.addChild(new NeuronLink(input, random.nextDouble()));

            firstLayer.add(neuron);
        };

        // Second layer that regroups everything
        List<Neuron> secondLayer = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Neuron neuron = Neuron.random(random);

            for (Neuron l1Neuron : firstLayer)
                neuron.addChild(new NeuronLink(l1Neuron, random.nextDouble()));

            secondLayer.add(neuron);
        }

        for (Neuron neuron: secondLayer) {
            output.addChild(new NeuronLink(neuron, random.nextDouble()));
        }

        return network;
    }
}
