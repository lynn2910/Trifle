package trifle.boardifier.control;

import trifle.boardifier.model.action.ActionList;
import trifle.boardifier.model.Model;

public abstract class Decider {
    protected Model model;
    protected Controller control;

    public Decider(Model model, Controller control) {
        this.model = model;
        this.control = control;
    }

    public abstract ActionList decide();
}
