package bots;

import minmax.BoardStatus;
import minmax.MinMax;
import minmax.MinMaxAlgorithm;
import minmax.MinMaxNode;
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
import java.util.Arrays;
import java.util.List;

public class DeterministicMinMaxBot extends TrifleDecider {
    private MinMax minMax;

    public DeterministicMinMaxBot(Model model, Controller controller) {
        super(model, controller);

        this.minMax = new MinMax(MinMaxAlgorithm.DeterministicAlgorithm);
    }

    @Override
    public ActionList decide() {
        TrifleStageModel stageModel = (TrifleStageModel) model.getGameStage();
        BoardStatus boardStatus = Utils.boardStatusFromBoard(stageModel);
        TrifleController controller = (TrifleController) this.control;

        Point lastOpponentMove;
        if (model.getIdPlayer() == 0) {
            lastOpponentMove = stageModel.getLastCyanPlayerMove();
        } else {
            lastOpponentMove = stageModel.getLastBluePlayerMove();
        }

        this.minMax.reset();
        this.minMax.buildCurrentTree(
                boardStatus,
                model.getIdPlayer(),
                lastOpponentMove,
                MinMax.DEPTH,
                true
        );

        MinMaxNode nextMove = this.minMax.minimax(model.getIdPlayer(), true);

        this.minMax.getTracker().displayStatistics();

        List<Pawn> pawns = stageModel.getPlayerPawns(model.getIdPlayer());
        Pawn pawnInvolved = pawns.get(nextMove.getPawn().getColorIndex());

        // Don't much this thing
        int tempX = nextMove.getMoveDone().x;
        nextMove.getMoveDone().x = nextMove.getMoveDone().y;
        nextMove.getMoveDone().y = tempX;

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
                "a1a2",
                pawnInvolved
        );


        pawnInvolved.setCoords(nextMove.getMoveDone());

        return actions;
    }
}
