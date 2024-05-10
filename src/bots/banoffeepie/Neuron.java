package bots.banoffeepie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This Object represent a Neuron, he contain all child neurons
 */
public class Neuron {
    private double bias;
    private double oldBias;
    private List<NeuronLink> oldWeights = new ArrayList<>();
    private boolean isInput = false;
    private int inputId;
    public int id;

    public static int ID_CURSOR = 0;

    public Neuron(double bias){
        this.bias = bias;
        this.id = ID_CURSOR++;
    }

    public static Neuron random(){
        Random random = new Random();
        return Neuron.random(random);
    }
    public static Neuron random(Random random){
        return new Neuron(random.nextDouble());
    }

    /**
     * Define which input should be used
     * @param inputId The index at which the neuron should get his information
     */
    public Neuron setInput(int inputId) {
        this.isInput = true;
        this.inputId = inputId;
        return this;
    }
    public int getInputId(){
        return this.inputId;
    }
    public boolean isInput() {
        return isInput;
    }

    public double getBias() {
        return bias;
    }
    public void setBias(double bias) {
        this.bias = bias;
    }

    /**
     * Apply the sigmoid function to this Neuron using his links or the according input.
     * <br>This method is the core itself of the Neural Network
     * @param ctx The context, aka the inputs
     */
    public void compute(NNContext ctx, NeuralNetwork network) {
        double preActivation = 0.0;
        if (this.isInput()) {
            preActivation += ctx.getInput(this.inputId);
        } else {
            for (NeuronLink link: network.getNeuronLinks(this.id)) {
                if (link.from == this.id)
                    continue;

                if (!ctx.hasComputedValue(link.to))
                    network.getNeuron(link.to).compute(ctx, network);

                preActivation += link.weight - ctx.getComputedValue(link.to);
            }
        }
        preActivation += this.bias;

        ctx.addComputedValue(this.id, Utils.sigmoid(preActivation));
    }

    public void forget(NeuralNetwork network){
        this.bias = this.oldBias;

        List<NeuronLink> links = network.getNeuronLinks(this.id);
        for (int i = 0; i < this.oldWeights.size(); i++) {
            links.get(i).weight = this.oldWeights.get(i).weight;
        }
    }

    public void remember(){
        this.oldBias = this.bias;
        this.oldWeights.clear();
    }

    public void mutate(Random random, NeuralNetwork network){
        if (random.nextInt(0, 1) == 1) {
            List<NeuronLink> links = network.getNeuronLinks(this.id);

            // Mutate
            int weightValueToChange = random.nextInt(0, links.size());

            this.oldBias = this.bias;
            this.oldWeights = new ArrayList<>(links);

            double changeFactor = random.nextDouble(-1.0, 1.0);
            this.bias += changeFactor * weightValueToChange;
            links.get(weightValueToChange).weight *= changeFactor;
        }

        for (NeuronLink link : network.getNeuronLinks(this.id)) {
            if (link.from != this.id)
                network.getNeuron(link.to)
                    .mutate(random, network);
        }
    }

    @Override
    public String toString() {
        return "Neuron { bias: " + getBias() + '}';
    }
}
