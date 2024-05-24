import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trifle.boardifier.model.Model;
import trifle.boardifier.model.TextElement;
import trifle.model.OldMove;
import trifle.model.Pawn;
import trifle.model.TrifleBoard;
import trifle.model.TrifleStageModel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTrifleStageModel {

    private TrifleStageModel stageModel;
    private TrifleBoard board;

    @BeforeEach
    public void setUp() {
        stageModel = new TrifleStageModel("Test Stage", new Model());
        board = new TrifleBoard(0, 0, stageModel);
        stageModel.setBoard(board);
    }

    @Test
    public void testPlayerPawns() {
        List<Pawn> bluePawns = new ArrayList<>();
        bluePawns.add(new Pawn(0, 0, stageModel, 0, 0));
        bluePawns.add(new Pawn(1, 0, stageModel, 1, 1));

        List<Pawn> cyanPawns = new ArrayList<>();
        cyanPawns.add(new Pawn(2, 1, stageModel, 2, 2));
        cyanPawns.add(new Pawn(3, 1, stageModel, 3, 3));

        stageModel.setBluePawns(bluePawns);
        stageModel.setCyanPawns(cyanPawns);

        assertEquals(2, stageModel.getBluePlayer().size());
        assertEquals(2, stageModel.getCyanPlayer().size());

        Pawn bluePawn = stageModel.getPlayerPawn(0, 0);
        assertNotNull(bluePawn);
        assertEquals(0, bluePawn.getColorIndex());

        Pawn cyanPawn = stageModel.getPlayerPawn(1, 2);
        assertNotNull(cyanPawn);
        assertEquals(2, cyanPawn.getColorIndex());
    }

    @Test
    public void testPlayerBlocked() {
        stageModel.setPlayerBlocked(0, true);
        assertTrue(stageModel.isBluePlayerBlocked());

        stageModel.setPlayerBlocked(1, true);
        assertTrue(stageModel.isCyanPlayerBlocked());

        stageModel.setPlayerBlocked(0, false);
        assertFalse(stageModel.isBluePlayerBlocked());

        stageModel.setPlayerBlocked(1, false);
        assertFalse(stageModel.isCyanPlayerBlocked());
    }

    @Test
    public void testLastPlayerMove() {
        Point blueMove = new Point(1, 2);
        Point cyanMove = new Point(3, 4);

        stageModel.setLastBluePlayerMove(blueMove);
        assertEquals(blueMove, stageModel.getLastBluePlayerMove());

        stageModel.setLastCyanPlayerMove(cyanMove);
        assertEquals(cyanMove, stageModel.getLastCyanPlayerMove());
    }

    @Test
    public void testUpdatePlayerPoints() {
        List<String> playerNames = List.of("Blue", "Cyan");
        stageModel.setPlayerNames(playerNames);

        stageModel.updatePlayerPoints(5, 10);
        TextElement playerPoints = stageModel.getPlayerPoints();
        assertNotNull(playerPoints);
        assertEquals("Blue: 5   Cyan: 10", playerPoints.getText());
    }

    @Test
    public void testMovesHistory() {
        List<OldMove> oldMoves = new ArrayList<>();
        oldMoves.add(new OldMove(1, new Point(1, 1), new Point(1, 2)));
        oldMoves.add(new OldMove(2, new Point(2, 2), new Point(2, 3)));

        for (OldMove move : oldMoves) {
            stageModel.addOldMove(move);
        }

        stageModel.updateHistory();
        List<TextElement> movesHistory = stageModel.getMovesHistory();
        assertEquals(oldMoves.size(), movesHistory.size());

        for (int i = 0; i < oldMoves.size(); i++) {
            assertEquals(oldMoves.get(i).toString(), movesHistory.get(i).getText());
        }
    }
}
