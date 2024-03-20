package trifle.boardifier.model.action;

import trifle.boardifier.model.GameElement;
import trifle.boardifier.model.Model;

public class RemoveFromStageAction extends GameAction {

    public RemoveFromStageAction(Model model, GameElement element) {
        super(model, element, "none");
    }

    public void execute() {
        element.removeFromStage();
        onEndCallback.execute();
    }

    public void createAnimation() {
    }
}
