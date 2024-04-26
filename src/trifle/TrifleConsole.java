package trifle;

import trifle.boardifier.control.Logger;
import trifle.boardifier.control.StageFactory;
import trifle.boardifier.model.GameException;
import trifle.boardifier.model.Model;
import static trifle.boardifier.view.ConsoleColor.*;
import trifle.boardifier.view.View;
import trifle.control.TrifleController;
import trifle.rules.GameMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrifleConsole {
    /**
     * Say helllooooo<br>
     * Send the content of the file at "resources/kamisado.asset.text"
     */
    private static void hello(){
        try {
            String kamisadoAsset = Utils.readFile(new File("src/trifle/resources/kamisado.asset.text"));
            System.out.println("\n");
            System.out.println(kamisadoAsset);
        } catch (IOException e) {
            System.out.println(RED_BOLD + "Cannot load the asset `kamisado.asset.text`." + RESET);
        }
    }

    public static void main(String[] args) {
        hello();

        Logger.setLevel(Logger.LOGGER_INFO);
        Logger.setVerbosity(Logger.VERBOSE_BASIC);

        Optional<String> outputMovesDir = Optional.empty();


        // Parse the internal arguments, such as `--output-moves`
        List<String> externalArgs = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            switch (arg) {
                case "": {break;}
                case "--output-moves": {
                    outputMovesDir = Optional.of(args[i + 1]);
                    // remove this arg
                    args[i] = "";
                    args[i + 1] = "";
                    break;
                }
                default:
                    externalArgs.add(arg);
            }
        }


        Tui tui = new Tui();
        GameMode gameMode = GameMode.defaultValue();

        // get the required data
        if (externalArgs.isEmpty()) {
            tui.run();
            gameMode = tui.getGameMode();
        }
        else {
            switch (externalArgs.get(0)) {
                case "0": break;
                case "1": {
                    gameMode = GameMode.Standard;
                    break;
                }
                case "2": {
                    gameMode = GameMode.Marathon;
                    break;
                }
                default: {
                    System.out.println("Le gameMode de jeu que vous souhaitez jouer (" + externalArgs.get(0) + ") n'est pas compris en 0 et 2 (inclus).\nLe gameMode de jeu a été automatiquement passé en Humain vs Humain.");
                }
            }
        }

        Model model = new Model();

        // Add the players to the model
        List<String> playerNames = getPlayerNames(tui, gameMode);
        switch (getPlayerMode(tui, gameMode, externalArgs.isEmpty())) {
            case 0: {
                model.addHumanPlayer(playerNames.get(0));
                model.addHumanPlayer(playerNames.get(1));
                break;
            }
            case 1: {
                model.addHumanPlayer(playerNames.get(0));
                model.addComputerPlayer(playerNames.get(1));
                break;
            }
            case 2: {
                model.addComputerPlayer(playerNames.get(0));
                model.addComputerPlayer(playerNames.get(1));
                break;
            }
            default: {
                System.out.println(RED + "Un problème est survenue dans la configuration du gameMode de jeux." + RESET);
                System.exit(1);
            }
        }

        // Initiate the required instances, such as the view, StageFactory and the controller
        StageFactory.registerModelAndView("trifle", "trifle.model.TrifleStageModel", "trifle.view.TrifleStageView");
        View view = new View(model);
        TrifleController controller = new TrifleController(model, view, gameMode, playerNames);
        controller.setFirstStageName("trifle");

        if (outputMovesDir.isPresent()) {
            try {
                controller.addOutputMovesFileWriter(outputMovesDir.get());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }

        try {
            int numberOfRounds = gameMode.numberOfRounds();

            System.out.println("You'll play " + numberOfRounds + " rounds.");

            while (controller.getCurrentRound() < numberOfRounds) {
                System.out.println("Round no." + (controller.getCurrentRound() + 1));

                controller.startGame();
                controller.stageLoop();

                controller.increaseRoundCounter();

                if (controller.getCurrentRound() < numberOfRounds - 1) {
                    System.out.println("End of round. Next round start in 5s...");
                    Thread.sleep(5000);
                    System.out.println("\n\n");
                }
            }

            controller.endGame();
        } catch (GameException | InterruptedException e) {
            System.out.println(RED + e.getMessage());
            e.printStackTrace();
            System.out.println("Cannot start the game. Abort" + RESET);
            System.exit(1);
        } finally {
            Logger.trace("Closing InputReader stream...");
            controller.closeStreams();
            Logger.trace("Controller's streams closed successfully");
        }
    }

    /**
     * Get the player Mode
     * @param tui The TUI instance
     * @param mode The player-mode given as argument, if any
     * @param tuiEnabled If whether the tui was called
     * @return The player mode (between zero and two, included)
     */
    private static int getPlayerMode(Tui tui, GameMode mode, boolean tuiEnabled) {
        if (tuiEnabled)
            return tui.getPlayerMode().ordinal();
        else return mode.ordinal();
    }

    /**
     * Get the player (or bot) names
     * <br>
     * Bot names will be generated automatically if there are less than two names registered in the TUI
     * @param tui The TUI instance
     * @param mode The player-mode given as argument, if any
     * @return The list of the names
     */
    private static List<String> getPlayerNames(Tui tui, GameMode mode) {
        List<String> tuiPlayerNames = tui.getPlayerNames();
        if (tuiPlayerNames.isEmpty()) {
            return switch (mode.ordinal()) {
                case 1 -> List.of("Player", "Computer");
                case 2 -> List.of("Computer1", "Computer2");
                default -> List.of("Player1", "Player2");
            };
        } else {
            if (2 - tuiPlayerNames.size() != tui.getPlayerMode().getBotNumber()) {
                for (int i = 0; i < 2 - tuiPlayerNames.size(); i++)
                    tuiPlayerNames.add("Computer" + (i + 1));
            }

            return tuiPlayerNames;
        }
    }
}
