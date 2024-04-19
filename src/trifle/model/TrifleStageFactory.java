package trifle.model;

import trifle.boardifier.model.GameStageModel;
import trifle.boardifier.model.StageElementsFactory;
import trifle.boardifier.model.TextElement;

import java.util.ArrayList;
import java.util.List;

import static trifle.view.TrifleStageView.BOARD_WIDTH;

public class TrifleStageFactory extends StageElementsFactory {
    private final TrifleStageModel stageModel;

    public TrifleStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (TrifleStageModel) gameStageModel;
    }

    @Override
    public void setup() {
        // Create the text that will be used to display the player name
        TextElement playerNameText = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        playerNameText.setLocation(BOARD_WIDTH + 2, 3);
        stageModel.setPlayerName(playerNameText);

        // Create the board
        TrifleBoard board = new TrifleBoard(0, 0, this.stageModel);
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
