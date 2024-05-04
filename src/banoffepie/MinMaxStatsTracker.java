package banoffepie;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will keep some traces while the min-max is performing, allowing to have the memory use, performances etc...
 */
public class MinMaxStatsTracker {
    /**
     * Store the time at which the min-max starts performing.
     * <br><br>
     * The time is stored as nanoseconds, allowing for high-precision values
     */
    private long startTime;
    private long totalTime;
    private int numberOfNodes;
    private int depth;
    /**
     * Contains each time of calculation used by the chosen algorithm to calculate the weight with the state given.
     * <br>
     * The time is also expressed in nanoseconds, as the algorithm can be really fast
     */
    private final List<Long> timeToCalculateWeight;

    public MinMaxStatsTracker() {
        this.startTime = 0;
        this.totalTime = 0;
        this.numberOfNodes = 0;
        this.timeToCalculateWeight = new ArrayList<>();
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void startCounter(){
        this.startTime = System.nanoTime();
    }

    public void newNode(){
        this.numberOfNodes++;
    }

    public void addTimeToCalculateWeight(long time){
        this.timeToCalculateWeight.add(time);
    }

    public void endTimer(){
        this.totalTime = System.nanoTime() - this.startTime;
    }

    public void displayStatistics() {
        System.out.println("\nMin-Max performances stats: ");
        System.out.println("  Time:  " + formatTime(this.totalTime));
        System.out.println("  Nodes: " + this.numberOfNodes);
        System.out.println("  Depth: " + this.depth);
        System.out.println();

        long totalWeightCalculationTime = 0;
        for (Long time : this.timeToCalculateWeight) { totalWeightCalculationTime += time;}
        System.out.println("  Total time to calculate weight:  " + formatTime(totalWeightCalculationTime));

        System.out.println("  Median time to calculate weight: "
                + formatTime(totalWeightCalculationTime / this.timeToCalculateWeight.size()));
    }


    private String formatTime(long time){
        String t = "";

        long hours = (long) (time / 1e6 / 1000 / 60 / 60 % 24);
        if (hours > 0)
            t += hours+"h ";

        long minutes = (long) (time / 1e6 / 1000 / 60 % 60);
        if (minutes > 0)
            t += minutes+"m ";

        long seconds = (long) (time / 1e6 / 1000 % 60);
        if (seconds > 0)
            t += seconds+"s";

        long nanos = time % 1000;
        if (t.isEmpty() && nanos > 0)
            t += nanos+"ns";

        return t.trim();
    }

    public void reset(){
        this.startTime = 0;
        this.numberOfNodes = 0;
        this.timeToCalculateWeight.clear();
        this.totalTime = 0;
    }
}
