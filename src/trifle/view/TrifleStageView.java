package trifle.view;

import trifle.boardifier.control.ActionFactory;
import trifle.boardifier.control.Logger;
import trifle.boardifier.model.GameElement;
import trifle.boardifier.model.GameException;
import trifle.boardifier.model.GameStageModel;
import trifle.boardifier.view.ClassicBoardLook;
import trifle.boardifier.view.ElementLook;
import trifle.boardifier.view.GameStageView;
import trifle.model.TrifleStageModel;

public class TrifleStageView extends GameStageView {
    public TrifleStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks() throws GameException {
        TrifleStageModel model = (TrifleStageModel) this.gameStageModel;

        // Create the main board (8x8)
        ClassicBoardLook boardLook = new ClassicBoardLook(PawnLook.HEIGHT, PawnLook.WIDTH, model.getBoard(), 1, 1, true);

        boardLook.setPadding(0);
        boardLook.setColWidth(PawnLook.WIDTH + 1);
        boardLook.setRowHeight(PawnLook.HEIGHT + 1);
        addLook(boardLook);

//      TODO pour ajouter la couleur des cases??
//        boardLook.addInnerLook(..., x, y);

        // add look for all pawns
        for (int i = 0; i < 8; i++) {
            // Add blue
            GameElement bluePawn = model.getBluePlayer().get(i);
            ElementLook bluePawnLook = new PawnLook(bluePawn);
            addLook(bluePawnLook);
            model.getBoard().addElement(bluePawn, 0, i);
            boardLook.addInnerLook(bluePawnLook, 0, i);

            // Add cyan
            GameElement cyanPawn = model.getCyanPlayer().get(i);
            ElementLook cyanPawnLook = new PawnLook(cyanPawn);
            addLook(cyanPawnLook);
            model.getBoard().addElement(cyanPawn, 7, i);
            boardLook.addInnerLook(cyanPawnLook, 7, i);
        }


        Logger.debug("Finished creating game stage looks", this);
    }
}
