package trifle.control;

import trifle.boardifier.control.ActionPlayer;
import trifle.boardifier.control.Controller;
import trifle.boardifier.model.Model;
import trifle.boardifier.model.Player;
import trifle.boardifier.model.action.ActionList;
import trifle.boardifier.view.ConsoleColor;
import trifle.boardifier.view.View;
import trifle.model.OldMove;
import trifle.model.TrifleStageModel;
import trifle.rules.GameMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;

public class TrifleController extends Controller {
    // Store the gameMode. Useful to check how many rounds are left.
    private final GameMode gameMode;
    private final List<String> playerNames;

    /**
     * The Buffer used by the game
     */
    BufferedReader consoleSysIn;

    public TrifleController(Model model, View view, GameMode gameMode, List<String> playerNames) {
        super(model, view);
        this.gameMode = gameMode;
        this.playerNames = playerNames;
    }

    /**
     * This method is used to transmit information to the Model, such as the game mode
     */
    private void shareInformations() {
        TrifleStageModel stageModel = (TrifleStageModel) model.getGameStage();

        stageModel.setGameMode(gameMode);
        stageModel.setPlayerNames(playerNames);
        stageModel.updatePlayerPoints();
    }

    /**
     * The game loop
     */
    @Override
    public void stageLoop() {
        this.shareInformations();

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        this.consoleSysIn = new BufferedReader(inputStreamReader);

        this.update();

        int waitBeforeEnd = 0; // 2500
        if (System.getenv().containsKey("WAIT_BEFORE_END")) {
            waitBeforeEnd = Integer.parseInt(System.getenv().get("WAIT_BEFORE_END"));
        }

        while(!this.model.isEndStage()) {
            long before = System.currentTimeMillis();

            this.playTurn();
            this.endOfTurn();

            long after = System.currentTimeMillis();
            if (waitBeforeEnd > 0 && after - before < waitBeforeEnd) {
                // Sleep
                try { Thread.sleep(1000 - (after-before)); }
                catch (InterruptedException e) { System.out.println(e.getMessage()); e.printStackTrace(); }
            }

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

                if (ok) {
                    this.addMoveToOldMoves(p, "Cyan (A1)", "G7");
                }

            } catch (IOException e) {
                System.out.println(ConsoleColor.RED + e.getMessage());
                e.printStackTrace();
                System.out.println(ConsoleColor.RESET);
                System.exit(1);
            }
        }
    }

    private void addMoveToOldMoves(Player p, String pawn, String move){
        TrifleStageModel stageModel = (TrifleStageModel) model.getGameStage();

        OldMove oldMove = new OldMove(
                p.getName().equals(playerNames.get(0)) ? 0 : 1,
                p.getName(),
                pawn,
                move
        );

        stageModel.addOldMove(oldMove);
        stageModel.updateHistory();
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
        System.out.println();
        model.setNextPlayer();

        Player p = model.getCurrentPlayer();
        TrifleStageModel stageModel = (TrifleStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName() + " is playing.");
    }
}
