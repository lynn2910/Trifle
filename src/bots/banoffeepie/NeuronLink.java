package bots.banoffeepie;

public class NeuronLink {
    private final Neuron neuron;
    private final double weight;

    public NeuronLink(Neuron neuron, double weight) {
        this.neuron = neuron;
        this.weight = weight;
    }

    public Neuron neuron() {
        return neuron;
    }

    public double weight() {
        return weight;
    }
}
