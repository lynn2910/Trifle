package bots;

import minmax.BoardStatus;
import minmax.MinMaxPawn;
import trifle.model.Pawn;
import trifle.model.TrifleStageModel;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static BoardStatus boardStatusFromBoard(TrifleStageModel stageModel) {
        List<Pawn> bluePawns = stageModel.getBluePlayer();

        List<MinMaxPawn> blueMMPawns = new ArrayList<>();
        for (Pawn pawn : bluePawns) {
            blueMMPawns.add(new MinMaxPawn(pawn));
        }

        List<Pawn> cyanPawns = stageModel.getCyanPlayer();

        List<MinMaxPawn> cyanMMPawns = new ArrayList<>();
        for (Pawn pawn : cyanPawns) {
            cyanMMPawns.add(new MinMaxPawn(pawn));
        }

        return new BoardStatus(blueMMPawns, cyanMMPawns, stageModel);
    }
}
