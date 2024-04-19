package trifle.model;

import trifle.boardifier.model.*;
import trifle.rules.GameMode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrifleStageModel extends GameStageModel {
    TrifleBoard board;
    private List<Pawn> bluePlayer;
    private List<Pawn> cyanPlayer;

    private List<BackgroundCell> backgroundCells;

    private Optional<Point> lastBluePlayerMove;
    private Optional<Point> lastCyanPlayerMove;

    private TextElement playerName;
    private TextElement roundCounter;

    private GameMode gameMode = GameMode.defaultValue();





    /*
     *
     *
     *   ====== METHODS ======
     *
     *
     */

    public TrifleStageModel(String name, Model model) {
        super(name, model);

        this.bluePlayer      = new ArrayList<>();
        this.cyanPlayer      = new ArrayList<>();
        this.backgroundCells = new ArrayList<>();
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

    public void addBackgroundCell(BackgroundCell backgroundCell) {
        this.backgroundCells.add(backgroundCell);
    }
    public List<BackgroundCell> getBackgroundCells() {
        return this.backgroundCells;
    }

    public Optional<Point> getLastBluePlayerMove() { return this.lastBluePlayerMove; }
    public void setLastBluePlayerMove(Point lastBluePlayerMove) {
        this.lastBluePlayerMove = Optional.of(lastBluePlayerMove);
    }

    public Optional<Point> getLastCyanPlayerMove() { return this.lastCyanPlayerMove; }
    public void setLastCyanPlayerMove(Point lastCyanPlayerMove) {
        this.lastCyanPlayerMove = Optional.of(lastCyanPlayerMove);
    }

    public TextElement getPlayerName() {
        return this.playerName;
    }
    public void setPlayerName(TextElement playerName) {
        this.playerName = playerName;
        addElement(playerName);
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public TextElement getRoundCounter() {
        return this.roundCounter;
    }
    public void setRoundCounter(TextElement roundCounter) {
        this.roundCounter = roundCounter;
        addElement(roundCounter);
    }
}
