package bots.banoffeepie;

import minmax.BoardStatus;
import minmax.MinMaxPawn;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This NeuralNetwork structure should not be used in other projects.
 * It uses a specific structure specially designed for the Kamisado with a MiniMax.
 */
public class NeuralNetwork {
    private final Neuron outputNeuron;

    private final HashMap<Integer, Neuron> neurons = new HashMap<>();
    private final List<NeuronLink>         links   = new ArrayList<>();

    public NeuralNetwork(Neuron outputNeuron){
        this.outputNeuron = outputNeuron;
    }

    public Neuron getOutputNeuron() {
        return outputNeuron;
    }
    public Neuron getNeuron(int id) {
        return neurons.get(id);
    }
    public HashMap<Integer, Neuron> getNeurons() {
        return neurons;
    }

    public void addNeuron(Neuron neuron){
        this.neurons.put(neuron.id, neuron);
    }
    public void addLink(NeuronLink link){
        this.links.add(link);
    }

    public List<NeuronLink> getNeuronLinks(int neuronID){
        List<NeuronLink> neuronLinks = new ArrayList<>();
        for (NeuronLink neuronLink : links) {
            if (neuronLink.from == neuronID)
                neuronLinks.add(neuronLink);
        }
        return neuronLinks;
    }

    public double compute(NNContext ctx){
        this.outputNeuron.compute(ctx, this);
        return ctx.getComputedValue(this.outputNeuron.id);
    }

    public String exportNetwork(){
        StringBuilder sb = new StringBuilder();

        sb.append("LINKS\n");
        for (NeuronLink link : links) {
            sb.append(link.to).append(',');
            sb.append(link.from).append(',');
            sb.append(link.weight).append('\n');
        }
        sb.append("\nNEURONS\n");
        for (Neuron neuron : neurons.values()) {
            sb.append(neuron.id).append(',');
            sb.append(neuron.getBias()).append(',');
            sb.append(neuron.isInput()).append(',');
            sb.append(neuron.getInputId()).append('\n');
        }

        return sb.toString();
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
