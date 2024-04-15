package trifle.view;

import trifle.boardifier.model.GameElement;
import trifle.boardifier.view.ConsoleColor;
import trifle.boardifier.view.ElementLook;
import trifle.model.Pawn;

public class PawnLook extends ElementLook {
    public static int WIDTH = 7;
    public static int HEIGHT = 3;

    public PawnLook(GameElement element){
        super(element, WIDTH, HEIGHT);
    }

    protected void render() {
        Pawn pawn = (Pawn) element;

        int colorIndex = pawn.getColorIndex();
        String color = Pawn.COLORS[colorIndex];

        char pawnChar = getCharacter(pawn.getPlayerNumber());

        shape[1][1] = color + pawnChar + ConsoleColor.RESET;
    }

    private static char getCharacter(int playerNb){
        return switch (playerNb) {
            case 1 -> 'o';
            case 2 -> 'x';
            default -> ' ';
        };
    }
}
