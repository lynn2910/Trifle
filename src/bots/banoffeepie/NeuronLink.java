package bots.banoffeepie;

public class NeuronLink {
    public int from;
    public int to;
    public double weight;

    public NeuronLink(int from, int to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int neuron1() {
        return from;
    }
    public int neuron2() {
        return to;
    }
    public double weight() {
        return weight;
    }
}
