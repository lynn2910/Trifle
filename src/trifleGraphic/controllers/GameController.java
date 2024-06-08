package trifleGraphic.controllers;

import trifleGraphic.boardifierGraphic.control.Controller;
import trifleGraphic.boardifierGraphic.model.Model;
import trifleGraphic.boardifierGraphic.view.View;

public class GameController extends Controller {
    public GameController(Model model, View view) {
        super(model, view);

        setControlAction(new GameActionController(model, view, this));
    }

    @Override
    public void endOfTurn(){
        // TODO end of turn method
    }
}
