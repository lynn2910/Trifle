package trifle.tui;

import trifle.rules.GameMode;
import trifle.rules.PlayerMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tui {
    private List<String> playerNames = new ArrayList<>();
    private GameMode gameMode;
    private PlayerMode playerMode;

    private Scanner scanner = new Scanner(System.in);

    public void draw(){
        System.out.println("Vous êtes dans le menu de configuration interactif.\nSélectionnez une option");

        System.out.println("(1). Définir le mode de jeu");
        System.out.println("(2). Définir le mode de joueur (humain ou ordinateur)");
        System.out.println("(3). Définir le nom du/des joueur(s)");
        System.out.println("(4). Lancer la partie");

        System.out.println("Entrez 'q' pour quitter");
        System.out.print("\n> ");
    }

    public Tui() {}
}
