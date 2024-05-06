package trifle.rules;

import trifle.boardifier.control.Controller;
import trifle.boardifier.model.Model;
import trifle.control.TrifleDecider;

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

    public TrifleDecider initComputer(Model model, Controller controller){
        return switch (this) {
            default -> new TrifleDecider(model, controller);
        };
    }
}
