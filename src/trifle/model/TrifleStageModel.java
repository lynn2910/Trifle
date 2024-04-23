package trifle.model;

import trifle.boardifier.model.*;
import trifle.rules.GameMode;
import trifle.rules.PlayerMode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static trifle.view.TrifleStageView.BOARD_WIDTH;

public class TrifleStageModel extends GameStageModel {
    TrifleBoard board;
    private List<Pawn> bluePlayer;
    private List<Pawn> cyanPlayer;

    private List<String> playerNames = List.of("Unknown", "Unknown");

    private final List<BackgroundCell> backgroundCells;

    private Point lastBluePlayerMove;
    private Point lastCyanPlayerMove;

    private TextElement playerName;
    private TextElement roundCounter;
    private TextElement playerPoints;

    private GameMode gameMode = GameMode.defaultValue();
    private PlayerMode playerMode = PlayerMode.defaultValue();

    private int bluePlayerPoints = 0;
    private int cyanPlayerPoints = 0;

    private final List<TextElement> movesHistory;
    private final List<OldMove> oldMovesList;





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
        this.oldMovesList    = new ArrayList<>();
        this.movesHistory    = new ArrayList<>();
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
        for (Pawn pawn: bluePawns) addElement(pawn);;
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

    public Point getLastBluePlayerMove() { return this.lastBluePlayerMove; }
    public void setLastBluePlayerMove(Point lastBluePlayerMove) {
        this.lastBluePlayerMove = lastBluePlayerMove;
    }

    public Point getLastCyanPlayerMove() { return this.lastCyanPlayerMove; }
    public void setLastCyanPlayerMove(Point lastCyanPlayerMove) {
        this.lastCyanPlayerMove = lastCyanPlayerMove;
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

    public PlayerMode getPlayerMode(){
        return this.playerMode;
    }
    public void setPlayerMode(PlayerMode playerMode){
        this.playerMode = playerMode;
    }

    public TextElement getRoundCounter() {
        return this.roundCounter;
    }
    public void setRoundCounter(TextElement roundCounter) {
        this.roundCounter = roundCounter;
        addElement(roundCounter);
    }

    public int getBluePlayerPoints(){
        return this.bluePlayerPoints;
    }
    public void incBluePlayerPoints(){
        this.bluePlayerPoints++;
    }

    public int getCyanPlayerPoints(){
        return this.cyanPlayerPoints;
    }
    public void incCyanPlayerPoints(){
        this.cyanPlayerPoints++;
    }

    public List<String> getPlayerNames(){
        return this.playerNames;
    }
    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }

    public TextElement getPlayerPoints() {
        return playerPoints;
    }
    public void setPlayerPoints(TextElement playerPoints) {
        this.playerPoints = playerPoints;
        addElement(playerPoints);
    }

    /**
     * Create or update the TextElement which store the number of points for each player
     */
    public void updatePlayerPoints() {
        String text = playerNames.get(0) + ": " + this.getBluePlayerPoints()
                + "   " + playerNames.get(1) + ": " + this.getBluePlayerPoints();

        if (this.getPlayerPoints() == null) {
            TextElement playerPointsCounter = new TextElement(text, this);
            playerPointsCounter.setLocation(BOARD_WIDTH + 2 + 2, 4);
            this.setPlayerPoints(playerPointsCounter);
        } else {
            // update the text
            this.getPlayerPoints().setText(text);
        }
    }

    public List<TextElement> getMovesHistory() {
        return this.movesHistory;
    }
    public void addOldMove(OldMove move){
        this.oldMovesList.add(move);
    }

    public static final int MAX_HISTORY_SIZE = 14;

    public void updateHistory(){
        // 13 -> 12, 12 -> 11, ...
        for (int i = 0; i < MAX_HISTORY_SIZE - 1; i++) {
            TextElement thisText = this.movesHistory.get(i);
            thisText.setText(this.movesHistory.get(i + 1).getText());
        }

        TextElement newMove = this.movesHistory.get(MAX_HISTORY_SIZE - 1);
        newMove.setText(oldMovesList.get(oldMovesList.size() - 1).toString());
    }
}
