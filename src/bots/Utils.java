package bots;

import minmax.BoardStatus;
import minmax.MinMaxPawn;
import trifle.model.Pawn;
import trifle.model.TrifleStageModel;

import java.util.List;

public class Utils {
    public static BoardStatus boardStatusFromBoard(TrifleStageModel stageModel) {
        List<Pawn> bluePawns = stageModel.getBluePlayer();
        List<Pawn> cyanPawns = stageModel.getCyanPlayer();

        return new BoardStatus(
            bluePawns.stream().map(MinMaxPawn::new).toList(),
            cyanPawns.stream().map(MinMaxPawn::new).toList()
        );
    }
}
