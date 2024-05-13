package bots;

import bots.banoffeepie.BuilderHelper;
import bots.banoffeepie.NeuralNetwork;
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
import java.util.List;

public class BanoffeePie extends TrifleDecider {
    protected static NeuralNetwork network;
    private MinMax minMax;

    public BanoffeePie(Model model, Controller controller) {
        super(model, controller);

        this.minMax = new MinMax(MinMaxAlgorithm.NeuralNetworkAlgorithm);
    }

    public static NeuralNetwork getNetwork(){
        if (network == null){
            // TODO add logic to import a Neural Network
            network = BuilderHelper.createNetwork();
        }

        return network;
    }

    @Override
    public ActionList decide(){
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


        MinMaxNode nextMove = this.minMax.minimax(model.getIdPlayer(), stageModel.getPlayerMode(), true);
        if (nextMove == null) {
            System.out.println("The bot " + model.getIdPlayer() + " cannot move his pawn.");
            return new ActionList();
        }

        this.minMax.getTracker().displayStatistics();

        List<Pawn> pawns = stageModel.getPlayerPawns(model.getIdPlayer());
        Pawn pawnInvolved = pawns.get(nextMove.getPawn().getColorIndex());

        // Don't touch this thing
        int tempX = nextMove.getMoveDone().x;
        nextMove.getMoveDone().x = nextMove.getMoveDone().y;
        nextMove.getMoveDone().y = tempX;

        System.out.println("Choice: " + nextMove.getMoveDone());

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


//        pawnInvolved.setCoords(nextMove.getMoveDone());
//
//        stageModel.getPlayerPawns(model.getIdPlayer())
//                .get(pawnInvolved.getColorIndex())
//                .setCoords(nextMove.getMoveDone());

        return actions;
    }
}
