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

        // We have 8 + 8 + 2 inputs, so 16 + 2 = 18
        List<Neuron> inputNeurons = new ArrayList<>();
        for (int i = 0; i < 18; i++) inputNeurons.add(Neuron.random(random).setInput(i));

        List<Neuron> firstLayerNeurons = new ArrayList<>();
        for (int i = 0; i < 16; i++) firstLayerNeurons.add(Neuron.random(random));

        List<Neuron> secondLayerNeurons = new ArrayList<>();
        for (int i = 0; i < 8; i++) secondLayerNeurons.add(Neuron.random(random));

        // register all neurons in the network
        for (Neuron neuron : inputNeurons) network.addNeuron(neuron);
        for (Neuron neuron : firstLayerNeurons) network.addNeuron(neuron);
        for (Neuron neuron : secondLayerNeurons) network.addNeuron(neuron);

        // add links
        for (Neuron first: firstLayerNeurons) {
            for (Neuron input: inputNeurons) {
                network.addLink(new NeuronLink(first.id, input.id, random.nextDouble()));
            }
        }
        for (Neuron second: secondLayerNeurons) {
            for (Neuron first: firstLayerNeurons) {
                network.addLink(new NeuronLink(second.id, first.id, random.nextDouble()));
            }

            network.addLink(new NeuronLink(second.id, inputNeurons.get(16).id, random.nextDouble()));
            network.addLink(new NeuronLink(second.id, inputNeurons.get(17).id, random.nextDouble()));
        }
        for (Neuron second: secondLayerNeurons) {
            network.addLink(new NeuronLink(network.getOutputNeuron().id, second.id, random.nextDouble()));

            network.addLink(new NeuronLink(second.id, inputNeurons.get(16).id, random.nextDouble()));
            network.addLink(new NeuronLink(second.id, inputNeurons.get(17).id, random.nextDouble()));
        }

        return network;
    }
}
