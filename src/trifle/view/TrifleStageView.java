package trifle.view;

import trifle.boardifier.model.GameException;
import trifle.boardifier.model.GameStageModel;
import trifle.boardifier.view.GameStageView;

public class TrifleStageView extends GameStageView {
    public TrifleStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks() throws GameException {

    }
}
