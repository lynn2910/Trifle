package trifle.control;

import trifle.boardifier.control.Controller;
import trifle.boardifier.control.Decider;
import trifle.boardifier.model.Model;
import trifle.boardifier.model.action.ActionList;

/**
 * A default implementation of a Decider, aka a computer player.
 */
public class TrifleDecider extends Decider {
    public TrifleDecider(Model model, Controller controller){
        super(model, controller);
    }

    /**
     * Decide which move can be done
     * @return The move calculated
     */
    @Override
    public ActionList decide(){
        ActionList actions = new ActionList();
        actions.setDoEndOfTurn(true);

        return actions;
    }
}
