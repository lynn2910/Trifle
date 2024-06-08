package trifleGraphic;

import javafx.application.Application;
import javafx.stage.Stage;
import rules.GameMode;
import rules.PlayerMode;
import trifleGraphic.boardifierGraphic.control.Logger;
import trifleGraphic.boardifierGraphic.control.StageFactory;
import trifleGraphic.boardifierGraphic.model.Model;
import trifleGraphic.controllers.GameController;
import trifleGraphic.view.TrifleRootPane;
import trifleGraphic.view.TrifleView;

import java.util.ArrayList;
import java.util.List;

public class TrifleGraphic extends Application {
    private static GameMode gameMode;
    private static String outputMovesDir;
    private static final List<String> externalArgs = new ArrayList<>();
    public static List<String> playerNames  = new ArrayList<>();

    public static final String FIRST_STAGE_NAME = "trifleGraphic";

    public static void main(String[] args) {
        Logger.setLevel(Logger.LOGGER_INFO);
        Logger.setVerbosity(Logger.VERBOSE_BASIC);

        // Parse the internal arguments, such as `--output-moves`
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            switch (arg) {
                case "": {break;}
                case "--output-moves": {
                    TrifleGraphic.outputMovesDir = args[i + 1];
                    // remove this arg
                    args[i] = "";
                    args[i + 1] = "";
                    break;
                }
                default:
                    externalArgs.add(arg);
            }
        }

        TrifleGraphic.gameMode = GameMode.defaultValue();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();

        // Add the players to the model
        playerNames = getPlayerNames(gameMode);
        PlayerMode playerMode = getPlayerMode(externalArgs.isEmpty() ? null : externalArgs.get(0));
        switch (playerMode) {
            case HumanVsHuman: {
                model.addHumanPlayer(playerNames.get(0));
                model.addHumanPlayer(playerNames.get(1));
                break;
            }
            case HumanVsComputer: {
                model.addHumanPlayer(playerNames.get(0));
                model.addComputerPlayer("Computer");
                break;
            }
            case ComputerVsComputer: {
                model.addComputerPlayer("Computer1");
                model.addComputerPlayer("Computer2");
                break;
            }
            default: {
                System.out.println("\u001b[31mA problem has occurred in the gameMode configuration.\u001b[0m");
                System.exit(1);
            }
        }

        // Initiate the required instances, such as the view, StageFactory and the controller
        StageFactory.registerModelAndView(FIRST_STAGE_NAME, "trifleGraphic.model.TrifleStageModel", "trifleGraphic.view.TrifleStageView");

        TrifleRootPane rootPane = new TrifleRootPane();

        TrifleView view = new TrifleView(model, primaryStage, rootPane);

        GameController controller = new GameController(model, view);

        controller.setFirstStageName(FIRST_STAGE_NAME);
        primaryStage.setTitle("Trifle - Kamisado");

        // FIXME changer le thème? J'ai pris le premier qui est arrivé sur Google :')
        Application.setUserAgentStylesheet("trifleGraphic/themes/nord-light.css");
        primaryStage.show();
//        try {
//            int numberOfRounds = gameMode.numberOfRounds();
//
//            System.out.println("You'll play " + numberOfRounds + " rounds.");
//
//            while (controller.getCurrentRound() < numberOfRounds) {
//                System.out.println("Round no." + (controller.getCurrentRound() + 1));
//
//                controller.startGame();
//                controller.stageLoop();
//
//                controller.increaseRoundCounter();
//
//                if (controller.getCurrentRound() < numberOfRounds - 1) {
//                    System.out.println("End of round. Next round start in 5s...");
//                    Thread.sleep(5000);
//                    System.out.println("\n\n");
//                }
//            }
//
//            controller.endGame();
//        } catch (GameException | InterruptedException e) {
//            System.out.println(RED + e.getMessage());
//            e.printStackTrace();
//            System.out.println("Cannot start the game. Abort" + RESET);
//            System.exit(1);
//        } finally {
//            Logger.trace("Closing InputReader stream...");
//            controller.closeStreams();
//            Logger.trace("Controller's streams closed successfully");
//        }
    }

    /**
     * Get the player Mode
     * @param mode The player-mode given as argument, if any
     * @return The player mode (between zero and two, included)
     */
    private static PlayerMode getPlayerMode(String mode) {
        return switch (mode) {
            case "0" -> PlayerMode.HumanVsHuman;
            case "1" -> PlayerMode.HumanVsComputer;
            case "2" -> PlayerMode.ComputerVsComputer;
            default -> throw new IllegalStateException("Unexpected value: " + mode);
        };
    }

    /**
     * Get the player (or bot) names
     * <br>
     * Bot names will be generated automatically if there are less than two names registered in the TUI
     * @param mode The player-mode given as argument, if any
     * @return The list of the names
     */
    private static List<String> getPlayerNames(GameMode mode) {
        return switch (mode.ordinal()) {
            case 1 -> List.of("Player", "Computer");
            case 2 -> List.of("Computer1", "Computer2");
            default -> List.of("Player1", "Player2");
        };
    }
}
