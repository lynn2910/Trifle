package trifle.model;

import trifle.boardifier.model.GameStageModel;
import trifle.boardifier.model.StageElementsFactory;

import java.util.ArrayList;
import java.util.List;

public class TrifleStageFactory extends StageElementsFactory {
    private final TrifleStageModel stageModel;

    public TrifleStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (TrifleStageModel) gameStageModel;
    }

    @Override
    public void setup() {
        // Create the board
        TrifleBoard board = new TrifleBoard(0, 1, this.stageModel);
        stageModel.setBoard(board);

        // Create the blue pawns
        List<Pawn> bluePawns = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            int colorIndex = TrifleBoard.BOARD[0][x];
            bluePawns.add(new Pawn(colorIndex, Pawn.BLUE_PLAYER, stageModel));
        }
        stageModel.setBluePawns(bluePawns);

        // Create the cyan pawns
        List<Pawn> cyanPawns = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            int colorIndex = TrifleBoard.BOARD[7][x];
            cyanPawns.add(new Pawn(colorIndex,Pawn.CYAN_PLAYER,  stageModel));
        }
        stageModel.setCyanPawns(cyanPawns);

        // Create all background cells
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int colorIndex = TrifleBoard.BOARD[y][x];

                BackgroundCell backgroundCell = new BackgroundCell(colorIndex, stageModel);
                stageModel.addBackgroundCell(backgroundCell);
            }
        }
    }
}
