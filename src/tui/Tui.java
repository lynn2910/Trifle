package tui;

import boardifier.view.ConsoleColor;
import rules.GameMode;
import rules.PlayerMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tui {
    private List<String> playerNames = new ArrayList<>();
    private GameMode gameMode;
    private PlayerMode playerMode;

    public char readNextChar() throws IOException {
        return (char) System.in.read();
    }

    public void draw(){
        System.out.println("Configuration de la partie");
        System.out.printf("  Mode de jeu: %s", this.gameMode == null ? "Non défini" : this.gameMode.toString());
        System.out.printf("  Mode de joueur: %s", this.playerMode == null ? "Non défini" : this.playerMode.toString());
        System.out.println("  Joueurs enregistrés:");
        for (String playerName : this.playerNames) {
            System.out.println("    " + playerName);
        }

        System.out.println(ConsoleColor.BLUE + "  Entrez 'q' pour quitter" + ConsoleColor.RESET);
    }

    public Tui() {
        this.draw();
    }
}
