package bots.banoffeepie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This Object represent a Neuron, he contain all child neurons
 */
public class Neuron {
    private final List<NeuronLink> children;
    private double bias;
    private boolean isInput = false;
    private int inputId;

    public Neuron(double bias){
        this.bias = bias;
        this.children = new ArrayList<>();
    }

    public static Neuron random(){
        Random random = new Random();
        return Neuron.random(random);
    }
    public static Neuron random(Random random){
        return new Neuron(random.nextDouble());
    }

    public void addChild(Neuron neuron, double weight) {
        this.addChild(new NeuronLink(neuron, weight));
    }
    public void addChild(NeuronLink link){
        children.add(link);
    }
    public List<NeuronLink> getChildren() {
        return children;
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
     * @return The value of the preAction after being passed to the sigmoid function
     */
    public double compute(NNContext ctx) {
        double preActivation = 0.0;
        if (this.isInput()) {
            preActivation += ctx.getInput(this.inputId);
        } else {
            for (NeuronLink link : this.children) {
                preActivation += link.weight() - link.neuron().compute(ctx);
            }
        }
        preActivation += this.bias;

        return Utils.sigmoid(preActivation);
    }

    @Override
    public String toString() {
        return "Neuron { bias: " + getBias() + ", children: " + getChildren().toString() + "}";
    }
}
