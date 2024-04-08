package trifle.model;

import trifle.boardifier.model.GameStageModel;
import trifle.boardifier.model.Model;
import trifle.boardifier.model.StageElementsFactory;

public class TrifleStageModel extends GameStageModel {
    public TrifleStageModel(String name, Model model) {
        super(name, model);
    }

    public StageElementsFactory getDefaultElementFactory() {
        return new TrifleStageFactory(this);
    }
}
