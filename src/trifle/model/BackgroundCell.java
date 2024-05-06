package trifle.model;

import trifle.boardifier.model.ElementTypes;
import trifle.boardifier.model.GameElement;
import trifle.boardifier.model.GameStageModel;

public class BackgroundCell extends GameElement {
    public static final int BACKGROUND_CELL_ID = 51;

    private final int colorIndex;

    public BackgroundCell(int colorIndex, GameStageModel gameStageModel) {
        super(gameStageModel);

        ElementTypes.register("background_cell", BACKGROUND_CELL_ID);
        this.type = ElementTypes.getType("background_cell");

        this.colorIndex = colorIndex;
    }

    public int getColorIndex() {
        return colorIndex;
    }
}