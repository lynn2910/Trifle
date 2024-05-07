package bots.banoffeepie;

import java.util.ArrayList;
import java.util.List;

/**
 * This Object represent a Neuron, he contain all child neurons
 */
public class Neuron {
    private final List<Neuron> children;

    private double bias;

    public Neuron(double bias){
        this.bias = bias;
        this.children = new ArrayList<>();
    }

    public void addChild(Neuron neuron){
        children.add(neuron);
    }
    public List<Neuron> getChildren() {
        return children;
    }

    public double getBias() {
        return bias;
    }
    public void setBias(double bias) {
        this.bias = bias;
    }
}
