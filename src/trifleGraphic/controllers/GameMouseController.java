package trifleGraphic.controllers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import trifleGraphic.boardifierGraphic.control.ActionFactory;
import trifleGraphic.boardifierGraphic.control.ActionPlayer;
import trifleGraphic.boardifierGraphic.control.Controller;
import trifleGraphic.boardifierGraphic.control.ControllerMouse;
import trifleGraphic.boardifierGraphic.model.Coord2D;
import trifleGraphic.boardifierGraphic.model.GameElement;
import trifleGraphic.boardifierGraphic.model.Model;
import trifleGraphic.boardifierGraphic.model.action.ActionList;
import trifleGraphic.boardifierGraphic.model.animation.AnimationTypes;
import trifleGraphic.boardifierGraphic.view.ElementLook;
import trifleGraphic.boardifierGraphic.view.View;
import trifleGraphic.model.Pawn;
import trifleGraphic.model.TrifleBoard;
import trifleGraphic.model.TrifleStageModel;
import trifleGraphic.view.TrifleBoardLook;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class GameMouseController extends ControllerMouse implements EventHandler<MouseEvent> {
    public GameMouseController(Model model, View view, Controller controller) {
        super(model, view, controller);
    }

    public void handle(MouseEvent event) {
        System.out.println(model.getSelected());
        System.out.println(model.isCaptureMouseEvent());
        if (!model.isCaptureMouseEvent()) return;

        Coord2D clic = new Coord2D(event.getSceneX(), event.getSceneY());

        List<GameElement> elementList = control.elementsAt(clic);

        TrifleStageModel stageModel = (TrifleStageModel) model.getGameStage();

        System.out.println("state: " + stageModel.getState());

        if (stageModel.getState() == TrifleStageModel.SELECT_PAWN_STATE) {
            handlePawnSelectionState(clic, stageModel);
        } else if (stageModel.getState() == TrifleStageModel.SELECT_DEST_STATE) {
            processPawnDestination(clic, stageModel, elementList);
        }
    }

    private void processPawnDestination(Coord2D clic, TrifleStageModel stageModel, List<GameElement> elementList){
        handlePawnSelectionState(clic, stageModel);

        boolean boardClicked = false;
        for (GameElement element : elementList) {
            if (element == stageModel.getBoard()) {
                boardClicked = true; break;
            }
        }

        if (!boardClicked || model.getSelected().isEmpty()) return;

        Pawn pawn = (Pawn) model.getSelected().get(0);

        TrifleBoard board = (TrifleBoard) stageModel.getBoard();
        board.setValidCells(pawn.getCoords(), model.getIdPlayer());

        TrifleBoardLook boardLook = (TrifleBoardLook) control.getElementLook(stageModel.getBoard());
        int[] dest = boardLook.getCellFromSceneLocation(clic);
        System.out.println("Mouse pawn destination: " + dest[0] + " " + dest[1]);

        if (board.canReachCell(dest[1], dest[0])) {
            System.out.println("The selected pawn can reach the cell at (" + dest[0] + ", " + dest[1] + ")");

            ActionList actionList = ActionFactory.generatePutInContainer(control, model, pawn, TrifleBoard.BOARD_ID, dest[1], dest[0], AnimationTypes.NONE, 1);
            actionList.setDoEndOfTurn(true);

            pawn.setCoords(new Point(dest[0], dest[1]));

            stageModel.unselectAll();
            stageModel.setState(TrifleStageModel.SELECT_PAWN_STATE);
            board.resetReachableCells(false);

            ActionPlayer play = new ActionPlayer(model, control, actionList);
            play.start();
            return;
        }

        // At the end, we remove the selected tag from the pawn and we reset the state
        stageModel.unselectAll();
        stageModel.setState(TrifleStageModel.SELECT_PAWN_STATE);

        board.resetReachableCells(false);
    }

    private Pawn getPawnFrom(Coord2D clic, TrifleStageModel stageModel) {
        System.out.println();
        System.out.println("Player ID: " + model.getIdPlayer());
        TrifleBoardLook boardLook = (TrifleBoardLook) control.getElementLook(stageModel.getBoard());

        int[] dest = boardLook.getCellFromSceneLocation(clic);
        System.out.println("Mouse destination: " + Arrays.toString(dest));
        for (Pawn p: stageModel.getPlayerPawns(model.getIdPlayer())) {
            System.out.println(p.getCoords());
            if (dest[0] == p.getCoords().getY() && dest[1] == p.getCoords().getX()) {
                System.out.println("Pawn: " + p.getColorIndex());
                return p;
            }
        }

        System.out.println();

        return null;
    }

    private void handlePawnSelectionState(Coord2D clic, TrifleStageModel stageModel) {
        System.out.println();
        Pawn pawn = getPawnFrom(clic, stageModel);
        System.out.println(clic);
        if (pawn == null ) return;

        pawn.toggleSelected();
        stageModel.setState(TrifleStageModel.SELECT_DEST_STATE);

        TrifleBoard board = (TrifleBoard) stageModel.getBoard();
        board.setValidCells(pawn.getCoords(), model.getIdPlayer());
        System.out.println();
    }
}
