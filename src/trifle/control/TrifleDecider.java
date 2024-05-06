package trifle.control;

import trifle.boardifier.control.Controller;
import trifle.boardifier.control.Decider;
import trifle.boardifier.model.Model;
import trifle.boardifier.model.action.ActionList;
import trifle.model.Pawn;
import trifle.model.TrifleStageModel;

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
    public ActionList decide() {
        ActionList actions = new ActionList();
        actions.setDoEndOfTurn(true);

        // IMPORTANT call `registerMove` from TrifleController, because we need to register the last move and to detect the win

//        TrifleController controller = (TrifleController) this.control;
//
//        controller.registerMove(
//                (TrifleStageModel) controller.getStageModel(),
//                new Point(0, 0),
//                "a1a2",
//                // Pawn
//        );

        System.out.println("Bot called");

        return actions;
    }
}