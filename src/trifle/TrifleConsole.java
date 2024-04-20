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
import java.util.List;

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

        Tui tui = new Tui();
        GameMode mode = GameMode.defaultValue();

        // get the required data
        if (args.length < 1) {
            tui.run();
            mode = tui.getGameMode();
        }
        else {
            switch (args[0]) {
                case "0": break;
                case "1": {
                    mode = GameMode.Standard;
                    break;
                }
                case "2": {
                    mode = GameMode.Marathon;
                    break;
                }
                default: {
                    System.out.println("Le mode de jeu que vous souhaitez jouer (" + args[0] + ") n'est pas compris en 0 et 2 (inclus).\nLe mode de jeu a été automatiquement passé en Humain vs Humain.");
                }
            }
        }

        GameMode gameMode = GameMode.valueOf(String.valueOf(mode));

        Model model = new Model();

        // Add the players to the model
        List<String> playerNames = getPlayerNames(tui, mode);
        switch (getPlayerMode(tui, mode, args.length < 1)) {
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
                System.out.println(RED + "Un problème est survenue dans la configuration du mode de jeux." + RESET);
                System.exit(1);
            }
        }

        // Initiate the required instances, such as the view, StageFactory and the controller
        StageFactory.registerModelAndView("trifle", "trifle.model.TrifleStageModel", "trifle.view.TrifleStageView");
        View view = new View(model);
        TrifleController controller = new TrifleController(model, view, gameMode, playerNames);
        controller.setFirstStageName("trifle");

        try {
            controller.startGame();
            controller.stageLoop();
        } catch (GameException e) {
            System.out.println(RED + e.getMessage());
            e.printStackTrace();
            System.out.println("Cannot start the game. Abort" + RESET);
            System.exit(1);
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
