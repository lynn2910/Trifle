package trifleGraphic.view;

import trifleGraphic.boardifierGraphic.view.ElementLook;
import trifleGraphic.model.BackgroundCell;
import trifleGraphic.boardifierGraphic.control.Logger;
import trifleGraphic.boardifierGraphic.model.TextElement;
import trifleGraphic.boardifierGraphic.model.GameStageModel;
import trifleGraphic.boardifierGraphic.view.GameStageView;
import trifleGraphic.boardifierGraphic.view.TextLook;
import trifleGraphic.model.Pawn;
import trifleGraphic.model.TrifleStageModel;

public class TrifleStageView extends GameStageView {
    public final static int BOARD_WIDTH = 600;

    public TrifleStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks(){
        TrifleStageModel model = (TrifleStageModel) gameStageModel;

        addLook(new TextLook(14, "0x000", model.getPlayerName()));
        addLook(new TextLook(14, "0x000", model.getRoundCounter()));
        addLook(new TextLook(14, "0x000", model.getPlayerPoints()));


        for (TextElement historyText: model.getMovesHistory())
            addLook(new TextLook(14, "0x000", historyText));

        TrifleBoardLook boardLook = new TrifleBoardLook(model.getBoard());
        boardLook.setPadding(0);
        addLook(boardLook);

        for (int col = 0; col < 8; col++) {
            Pawn bluePawn = model.getPlayerPawn(0, col);
            addLook(new PawnLook(bluePawn));
//            model.getBoard().addElement(bluePawn, 0, col);

            Pawn cyanPawn = model.getPlayerPawn(1, col);
            addLook(new PawnLook(cyanPawn));
//            model.getBoard().addElement(cyanPawn, 7, col);
        }

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int index = x + y * 8;
                BackgroundCell backgroundCell = model.getBackgroundCells().get(index);
                backgroundCell.setLocation(x, y);

                ElementLook look = new BackgroundCellLook(backgroundCell);

                addLook(look);
                boardLook.addInnerLook(look, x, y);
            }
        }

        boardLook.updateInners();

        Logger.debug("Finished creating game stage looks", this);
    }
}