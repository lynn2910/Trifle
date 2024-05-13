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
}
