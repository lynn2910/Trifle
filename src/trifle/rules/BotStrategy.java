package trifle.rules;

import bots.DeterministicMinMaxBot;
import trifle.boardifier.control.Controller;
import trifle.boardifier.model.Model;
import trifle.control.TrifleDecider;

public enum BotStrategy {
    BotEurDeCul,
    MinMaxDeterministic;

    public static final BotStrategy DEFAULT = BotStrategy.MinMaxDeterministic;

    /**
     * @return The name of the strategy
     */
    public String toString() {
        return switch (this) {
            case BotEurDeCul -> "BotEur de cul";
            case MinMaxDeterministic -> "Mellie (MinMax)";
        };
    }

    public String getDescription(){
        return switch (this) {
            case BotEurDeCul -> ""; // TODO
            case MinMaxDeterministic -> "MinMax with a deterministic algorithm";
        };
    }

    public TrifleDecider initComputer(Model model, Controller controller){
        return switch (this) {
            case MinMaxDeterministic -> new DeterministicMinMaxBot(model, controller);
            default -> new TrifleDecider(model, controller);
        };
    }
}
