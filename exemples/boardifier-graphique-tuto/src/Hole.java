import boardifier.control.Logger;
import control.ControllerHole;
import boardifier.control.StageFactory;
import boardifier.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import view.HoleRootPane;
import view.HoleView;

public class Hole extends Application {

    private static int mode;
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                mode = Integer.parseInt(args[0]);
                if ((mode <0) || (mode>2)) mode = 0;
            }
            catch(NumberFormatException e) {
                mode = 0;
            }
        }
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Logger.setLevel(Logger.LOGGER_DEBUG); // show info and debug messages

        // create the global model
        Model model = new Model();
        // add some players taking mode into account
        if (mode == 0) {
            model.addHumanPlayer("player1");
            model.addHumanPlayer("player2");
        }
        else if (mode == 1) {
            model.addHumanPlayer("player");
            model.addComputerPlayer("computer");
        }
        else if (mode == 2) {
            model.addComputerPlayer("computer1");
            model.addComputerPlayer("computer2");
        }
        // register a single stage for the game, called hole
        StageFactory.registerModelAndView("hole", "model.HoleStageModel", "view.HoleStageView");
        // create the root pane, using the subclass HoleRootPane
        HoleRootPane rootPane = new HoleRootPane();
        // create the global view.
        HoleView view = new HoleView(model, stage, rootPane);
        // create the controllers.
        ControllerHole control = new ControllerHole(model,view);
        // set the name of the first stage to create when the game is started
        control.setFirstStageName("hole");
        // set the stage title
        stage.setTitle("The Hole");
        // show the JavaFx main stage
        stage.show();
    }
}
