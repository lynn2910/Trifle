package trifle.control;

import trifle.boardifier.control.ActionPlayer;
import trifle.boardifier.control.Controller;
import trifle.boardifier.model.Model;
import trifle.boardifier.model.Player;
import trifle.boardifier.model.action.ActionList;
import trifle.boardifier.view.ConsoleColor;
import trifle.boardifier.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class TrifleController extends Controller {

    /**
     * The
     */
    BufferedReader consoleSysIn;

    public TrifleController(Model model, View view) {
        super(model, view);
    }

    /**
     * The game loop :O
     */
    @Override
    public void stageLoop() {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        this.consoleSysIn = new BufferedReader(inputStreamReader);

        this.update();

        while(!this.model.isEndStage()) {
            this.playTurn();
            this.endOfTurn();
            this.update();
        }

        // close the streams at the end of the game :)
        try {
            this.consoleSysIn.close();
            inputStreamReader.close();
        } catch (IOException e) {
            System.out.println("Error closing input stream");
            e.printStackTrace();
            System.exit(1);
        }

        this.endGame();
    }

    private void playTurn() {
        Player p = model.getCurrentPlayer();

        switch (p.getType()) {
            case Player.HUMAN: {
                playerTurn(p);
                break;
            }
            case Player.COMPUTER: {
                botTurn(p);
                break;
            }
            default: {
                System.out.println(ConsoleColor.RED + "Player type is nether a player or a human, what?\nThis error is critical, the game will exit." + ConsoleColor.RESET);
                System.exit(1);
            }
        }
    }

    private static void printMoveHelp(){
        System.out.println("\nHow to move my pawn?");
        System.out.println("We will take an example. If you are Player 1 and want to move your cyan pawn at A1 to G7, you input will be:");
        System.out.println("  CG7 or A1G7 (where A1 is the pawn position)");
        System.out.println("*`C` is for `cyan` and where you want to move it.*");
        System.out.println("Here is each color code:");
        System.out.println("- Cyan:   `c`");
        System.out.println("- Blue:   `b`");
        System.out.println("- Purple: `p`");
        System.out.println("- White:  `d`");
        System.out.println("- Orange: `e`");
        System.out.println("- Red:    `f`");
        System.out.println("- Green:  `g`");
        System.out.println("- Black:  `n`");
        System.out.println("You can view again this message by typing `?` in your input.\n");
    }

    private static final Pattern MOVE_PATTERN = Pattern.compile("^([a-hA-H][1-8]|[cbpdefgnCBPDEFGN])([a-hA-H][1-8])$");

    private void playerTurn(Player p) {
        boolean ok = false;

        while (!ok) {
            System.out.print(p.getName() + " > ");
            try {
                String move = consoleSysIn.readLine().toUpperCase().trim();

                if (move.equals("?")) {
                    printMoveHelp();
                    continue;
                }

                if ((move.length() != 3 && move.length() != 4) || !MOVE_PATTERN.matcher(move).find()) {
                    System.out.println(ConsoleColor.RED + "Your input must be like `A1G7` or `cG7`, where the first is whether the color code or the coordinate of the move, and after, where you want to move it." + ConsoleColor.RESET);
                    System.out.println(ConsoleColor.YELLOW + "The match is insensitive and doesn't differentiate upper and lower case" + ConsoleColor.RESET);
                    continue;
                }

                ok = this.analyseAndPlay(move);
            } catch (IOException e) {
                System.out.println(ConsoleColor.RED + e.getMessage());
                e.printStackTrace();
                System.out.println(ConsoleColor.RESET);
                System.exit(1);
            }
        }
    }

    private void botTurn(Player p) {
        // TODO computer play?
    }

    private boolean analyseAndPlay(String move) {
        // TODO

        ActionList actions = new ActionList();
        actions.setDoEndOfTurn(true);
        ActionPlayer play = new ActionPlayer(model, this, actions);

        play.start();

        return true;
    }

    @Override
    public void endOfTurn(){
        model.setNextPlayer();

        Player p = model.getCurrentPlayer();
        // TODO set the current player name
    }
}
