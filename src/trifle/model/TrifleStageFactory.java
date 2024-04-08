package trifle.model;

import trifle.boardifier.model.GameStageModel;
import trifle.boardifier.model.StageElementsFactory;

public class TrifleStageFactory extends StageElementsFactory {
    private TrifleStageModel stageModel;

    public TrifleStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (TrifleStageModel) gameStageModel;
    }

    @Override
    public void setup() {

    }
}
