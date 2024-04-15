package trifle.rules;

public enum BotStrategy {
    BanoffeePie,
    SecondStrategy; // TODO define the second strategy


    public static final BotStrategy DEFAULT = BotStrategy.SecondStrategy;

    /**
     * @return The name of the strategy
     */
    public String toString() {
        return switch (this) {
            case BanoffeePie -> "Project 'BanoffeePie'";
            case SecondStrategy -> "Yeet"; // TODO
        };
    }

    public String getDescription(){
        return switch (this) {
            case BanoffeePie -> "MinMax algorithm with neural network";
            case SecondStrategy -> "Work In Progress"; // TODO
        };
    }
}
