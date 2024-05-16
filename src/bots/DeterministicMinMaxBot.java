package bots;

import minmax.BoardStatus;
import minmax.MinMax;
import minmax.Node;
import trifle.boardifier.control.ActionFactory;
import trifle.boardifier.control.Controller;
import trifle.boardifier.model.Model;
import trifle.boardifier.model.action.ActionList;
import trifle.control.TrifleController;
import trifle.control.TrifleDecider;
import trifle.model.Pawn;
import trifle.model.TrifleBoard;
import trifle.model.TrifleStageModel;

import java.awt.*;
import java.util.List;

public class DeterministicMinMaxBot extends TrifleDecider {
    private MinMax minMax;

    public DeterministicMinMaxBot(Model model, Controller controller) {
        super(model, controller);

        this.minMax = new MinMax();
    }

    @Override
    public ActionList decide() {
        TrifleStageModel stageModel = (TrifleStageModel) model.getGameStage();
        TrifleController controller = (TrifleController) this.control;

        BoardStatus boardStatus = new BoardStatus(
                stageModel.getBluePlayer(),
                stageModel.getCyanPlayer(),
                (TrifleBoard) stageModel.getBoard(),
                stageModel
        );

        this.minMax.reset();
        this.minMax.buildTree(boardStatus, model.getIdPlayer());

        Node nextMove = this.minMax.minimax(model.getIdPlayer());
        if (nextMove == null) {
            System.out.println("The bot " + model.getIdPlayer() + " cannot move his pawn.");
            return new ActionList();
        }
        System.out.println("Choice of the MinMax:\n" + nextMove);

        this.minMax.getTracker().displayStatistics();
        this.minMax.getTracker().sendStatisticsToApi();

        List<Pawn> pawns = stageModel.getPlayerPawns(model.getIdPlayer());
        Pawn pawnInvolved = null;

        for (Pawn pawn : pawns) {
            if (pawn.getColorIndex() == nextMove.getPawnInvolved().getColorIndex()) {
                pawnInvolved = pawn;
                break;
            }
        }

        assert pawnInvolved != null;

        ActionList actions = ActionFactory.generatePutInContainer(
                model,
                pawnInvolved,
                TrifleBoard.BOARD_ID,
                nextMove.getMoveDone().y,
                nextMove.getMoveDone().x
        );
        actions.setDoEndOfTurn(true);

        controller.registerMove(
                stageModel,
                nextMove.getMoveDone(),
                TrifleController.normalizeCoordinate(pawnInvolved.getCoords(), false)
                    + TrifleController.normalizeCoordinate(nextMove.getMoveDone(), false),
                pawnInvolved
        );

        return actions;
    }
}
