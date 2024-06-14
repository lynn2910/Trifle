package rules;

import bots.BotEurdeku;
import bots.DeterministicMinMaxBot;
import trifleConsole.boardifier.control.Controller;
import trifleConsole.boardifier.model.Model;
import trifleConsole.control.TrifleDecider;

public enum BotStrategy {
    BotEurDeCul,
    MinMaxDeterministic;

    public static final BotStrategy DEFAULT = BotStrategy.BotEurDeCul;

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
            case BotEurDeCul -> "A simple deterministic algorithm";
            case MinMaxDeterministic -> "MinMax with a deterministic algorithm";
        };
    }

    public TrifleDecider initComputer(Model model, Controller controller){
        return switch (this) {
            case MinMaxDeterministic -> new DeterministicMinMaxBot(model, controller);
            case BotEurDeCul -> new BotEurdeku(model, controller);
        };
    }
}
