package trifle.rules;

import bots.DeterministicMinMaxBot;
import trifle.boardifier.control.Controller;
import trifle.boardifier.model.Model;
import trifle.control.TrifleDecider;

public enum BotStrategy {
    BanoffeePie,
    MinMaxDeterministic;

    public static final BotStrategy DEFAULT = BotStrategy.MinMaxDeterministic;

    /**
     * @return The name of the strategy
     */
    public String toString() {
        return switch (this) {
            case BanoffeePie -> "Project 'BanoffeePie'";
            case MinMaxDeterministic -> "Classic MinMax"; // TODO
        };
    }

    public String getDescription(){
        return switch (this) {
            case BanoffeePie -> "MinMax algorithm with neural network";
            case MinMaxDeterministic -> "with a classic algorithm";
        };
    }

    public TrifleDecider initComputer(Model model, Controller controller){
        return switch (this) {
            case MinMaxDeterministic -> new DeterministicMinMaxBot(model, controller);
            default -> new TrifleDecider(model, controller);
        };
    }
}
