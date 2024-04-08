package trifle.control;

import trifle.boardifier.control.Controller;
import trifle.boardifier.model.Model;
import trifle.boardifier.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    private void playTurn() {}
}
