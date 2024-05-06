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

        this.minMax.reset();
        this.minMax.buildCurrentTree(
                boardStatus,
                model.getIdPlayer(),
                model.getIdPlayer() == 0 ?
                        stageModel.getLastCyanPlayerMove()
                        : stageModel.getLastBluePlayerMove(),
                MinMax.DEPTH,
                true
        );

        MinMaxNode nextMove = this.minMax.minimax(model.getIdPlayer(), true);

        this.minMax.getTracker().displayStatistics();

        List<Pawn> pawns = stageModel.getPlayerPawns(model.getIdPlayer());
        Pawn pawnInvolved = pawns.get(nextMove.getPawn().getColorIndex());

        System.out.println(nextMove.getMoveDone());

        int y = nextMove.getMoveDone().y;
        int x = nextMove.getMoveDone().x;

        if (model.getIdPlayer() != 0) {
            x = 7 - x;
        }

        pawnInvolved.setCoords(nextMove.getMoveDone());

        ActionList actions = ActionFactory.generatePutInContainer(
                model,
                pawnInvolved,
                TrifleBoard.BOARD_ID,
                x,
                y
        );
        actions.setDoEndOfTurn(true);

        // IMPORTANT call `registerMove` from TrifleController, because we need to register the last move and to detect the win
        controller.registerMove(
                stageModel,
                nextMove.getMoveDone(),
                "a1a2",
                pawnInvolved
        );

        return actions;
    }
}
