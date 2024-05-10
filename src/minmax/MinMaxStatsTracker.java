package minmax;

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
    private long endTime;
    private int numberOfNodes;
    private int depth;
    private int[] numberOfNodesPerLayout;

    private long pathFinderStart;
    private long pathFinderEnd;

    /**
     * Contains each time of calculation used by the chosen algorithm to calculate the weight with the state given.
     * <br>
     * The time is also expressed in nanoseconds, as the algorithm can be really fast
     */
    private final List<Long> timeToCalculateWeight;

    public MinMaxStatsTracker() {
        this.startTime = 0;
        this.endTime = 0;
        this.pathFinderStart = 0;
        this.pathFinderEnd = 0;
        this.numberOfNodes = 0;
        this.timeToCalculateWeight = new ArrayList<>();
    }

    public void setDepth(int depth) {
        this.depth = depth;
        this.numberOfNodesPerLayout = new int[depth];
    }

    public void startCounter(){
        this.startTime = System.nanoTime();
    }

    public void newNode(){
        this.numberOfNodes++;
    }

    public void newNode(int layout){
        this.numberOfNodes++;

        if (layout < depth)
            this.numberOfNodesPerLayout[depth - 1 - layout]++;
    }

    public int[] getNumberOfNodesPerLayout(){
        return numberOfNodesPerLayout;
    }

    public void addTimeToCalculateWeight(long time){
        this.timeToCalculateWeight.add(time);
    }

    public void endTimer(){
        this.endTime = System.nanoTime();
    }

    public long getTotalTreeBuilderTime(){
        return this.endTime - this.startTime;
    }

    public void startPathFinder(){
        this.pathFinderStart = System.nanoTime();
    }

    public void endPathFinder(){
        this.pathFinderEnd = System.nanoTime();
    }

    public long getPathFinderTime(){
        return this.pathFinderEnd - this.pathFinderStart;
    }

    public int getNumberOfNodes(){
        return this.numberOfNodes;
    }

    public void displayStatistics() {
        System.out.println("\nMin-Max performances stats: ");
        System.out.println("  Time:  " + formatTime(getTotalTreeBuilderTime()));
        System.out.println("  Nodes: " + this.numberOfNodes);
        System.out.println("  Depth: " + this.depth);
        System.out.println();

        System.out.println("  Time to find the better path: " + formatTime(getPathFinderTime()));
        System.out.println();

        long totalWeightCalculationTime = 0;
        for (Long time : this.timeToCalculateWeight) { totalWeightCalculationTime += time;}
        System.out.println("  Total time to calculate weight:  " + formatTime(totalWeightCalculationTime));
        System.out.println("  Number of weights:            :  " + this.timeToCalculateWeight.size());

        long median = this.timeToCalculateWeight.isEmpty() ? 0 : totalWeightCalculationTime / this.timeToCalculateWeight.size();
        System.out.println("  Median time to calculate weight: "
                + formatTime(median));
        System.out.println();

//        System.out.println("  Number of nodes per Layout: ");
//        for (int layoutId = 0; layoutId < this.numberOfNodesPerLayout.length; layoutId++) {
//            if (layoutId % 2 == 0) System.out.print(ConsoleColor.GREEN);
//            else System.out.print(ConsoleColor.RED);
//
//            String layoutIdFormatted = "" + layoutId;
//            if (layoutIdFormatted.length() < 2)
//                layoutIdFormatted = " " + layoutIdFormatted;
//
//            System.out.print("    " + layoutIdFormatted + " - " + this.numberOfNodesPerLayout[layoutId]);
//
//            System.out.println(ConsoleColor.RESET);
//        }
//        System.out.println();
    }


    /**
     * Format the given time
     * @param nanoseconds The time in nanoseconds
     * @return The formatted time
     */
    public static String formatTime(long nanoseconds) {
        long hours = nanoseconds / (3600L * 1_000_000_000L);
        long minutes = (nanoseconds % (3600L * 1_000_000_000L)) / (60L * 1_000_000_000L);
        long seconds = (nanoseconds % (60L * 1_000_000_000L)) / (1_000_000_000L);
        long milliseconds = (nanoseconds % (1_000_000_000L)) / 1_000_000L;
        long remainingNanos = nanoseconds % 1_000_000L;

        StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            sb.append(hours).append("h ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m ");
        }
        if (seconds > 0) {
            sb.append(seconds).append("s ");
        }
        if (milliseconds > 0) {
            sb.append(milliseconds);
            if (remainingNanos > 0 || sb.isEmpty()) {
                sb.append('.').append(remainingNanos % 10000);
            }

            sb.append("ms ");
        }

        if (sb.isEmpty() && remainingNanos > 0) {
            sb.append("0.").append(remainingNanos % 10000).append("ms");
        }

        return sb.toString().trim();
    }


    public void reset(){
        this.startTime = 0;
        this.numberOfNodes = 0;
        this.timeToCalculateWeight.clear();
        this.endTime = 0;
        this.depth = 1;
        this.numberOfNodesPerLayout = new int[1];
    }
}
