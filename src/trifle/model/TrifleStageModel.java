package trifle.model;

import trifle.boardifier.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrifleStageModel extends GameStageModel {
    TrifleBoard board;
    private List<Pawn> bluePlayer;
    private List<Pawn> cyanPlayer;

    private Optional<Point> lastBluePlayerMove;
    private Optional<Point> lastCyanPlayerMove;

    public TrifleStageModel(String name, Model model) {
        super(name, model);

        this.bluePlayer = new ArrayList<>();
        this.cyanPlayer = new ArrayList<>();
    }

    public StageElementsFactory getDefaultElementFactory() {
        return new TrifleStageFactory(this);
    }

    public ContainerElement getBoard() {
        return board;
    }

    public void setBoard(TrifleBoard board) {
        this.board = board;
        addContainer(board);
    }

    public List<Pawn> getBluePlayer() { return this.bluePlayer; }
    public void setBluePawns(List<Pawn> bluePawns) {
        this.bluePlayer = bluePawns;
        for (Pawn pawn: bluePawns) addElement(pawn);
    }

    public List<Pawn> getCyanPlayer() { return this.cyanPlayer; }
    public void setCyanPawns(List<Pawn> cyanPawns) {
        this.cyanPlayer = cyanPawns;
        for (Pawn pawn: cyanPawns) addElement(pawn);
    }

    public Optional<Point> getLastBluePlayerMove() { return this.lastBluePlayerMove; }
    public void setLastBluePlayerMove(Point lastBluePlayerMove) {
        this.lastBluePlayerMove = Optional.of(lastBluePlayerMove);
    }
    public Optional<Point> getLastCyanPlayerMove() { return this.lastCyanPlayerMove; }
    public void setLastCyanPlayerMove(Point lastCyanPlayerMove) {
        this.lastCyanPlayerMove = Optional.of(lastCyanPlayerMove);
    }
}
