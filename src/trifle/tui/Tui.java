package trifle.tui;

import trifle.boardifier.view.ConsoleColor;
import trifle.rules.GameMode;
import trifle.rules.PlayerMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tui {
    private List<String> playerNames = new ArrayList<>();
    private GameMode gameMode;
    private PlayerMode playerMode;

    private final Scanner scanner = new Scanner(System.in);

    private void draw(){
        System.out.println();
        System.out.println("Vous êtes dans le menu de configuration interactif.\nSélectionnez une option");

        System.out.println("(1). Définir le mode de jeu");
        System.out.println("(2). Définir le mode de joueur (humain ou ordinateur)");
        System.out.println("(3). Définir le nom du/des joueur(s)");
        System.out.println("(4). Afficher la configuration actuelle");
        System.out.println("(5). Lancer la partie");

        System.out.println("Entrez 'q' pour quitter");
        System.out.print("> ");
    }

    private boolean isConfigReady(){
        return false; // TODO
    }

    public void run() {
        boolean run = true;

        while (run){
            this.draw();
            String line = scanner.nextLine().toLowerCase().trim();

            switch (line) {
                case "q": {
                    System.out.println(ConsoleColor.GREEN + "Reçu, à bientôt!" + ConsoleColor.RESET);
                    System.exit(0);
                }
                // Définir le mode de jeu
                case "1": {
                    this.setGameMode();
                    break;
                }
                // Définir le mode de joueur.
                // Il faut noter qu'on va réinitialiser les joueurs enregistrés (sauf si on passe de 2 à 1, là, on va "crop" et prévenir l'utilisateur)
                case "2": {
                    this.setPlayerMode();
                    break;
                }
                // Définir les joueurs.
                // On va afficher la liste, la réinitialiser puis demander le nombre N de joueurs requis pour le mode de joueur
                case "3": {
                    // TODO
                    break;
                }
                // On affiche la configuration actuelle
                case "4": {
                    // TODO
                    break;
                }
                // Lancer la partie
                case "5": {
                    if (isConfigReady())
                        run = false;
                    else
                        this.notConfiguredTui();

                    break;
                }
                // I'm blue dabedi dabeda
                case "trifle": {
                    this.recette();
                    break;
                }
                default: {
                    System.out.println(ConsoleColor.RED + "Je ne comprend pas cette action." + ConsoleColor.RESET);
                }
            }
        }
    }

    private void setPlayerMode(){}

    private void setGameMode(){
        while (true){
            System.out.println();
            System.out.println("Définition du mode de jeu");
            System.out.println("Modes de jeu disponibles:");
            System.out.println("(a) Rapide\n      Une seule manche");
            System.out.println("(b) Standard"); // TODO ajouter les infos de chaque mode de jeu...
            System.out.println("(c) Marathon");
            System.out.println("Tapez 'exit' pour quitter ce menu");
            System.out.print(">> ");

            String selection = scanner.nextLine().toLowerCase().trim();
            switch (selection){
                case "a": {
                    this.gameMode = GameMode.Fast;
                    System.out.println("\nMode de jeu sélectionné: " + ConsoleColor.WHITE_BOLD + "Rapide" + ConsoleColor.RESET);
                    return;
                }
                case "b": {
                    this.gameMode = GameMode.Standard;
                    System.out.println("\nMode de jeu sélectionné: " + ConsoleColor.WHITE_BOLD + "Standard" + ConsoleColor.RESET);
                    return;
                }
                case "c": {
                    this.gameMode = GameMode.Marathon;
                    System.out.println("\nMode de jeu sélectionné: " + ConsoleColor.WHITE_BOLD + "Marathon" + ConsoleColor.RESET);
                    return;
                }
                case "exit": {
                    System.out.println("Retour au menu principal.");
                    return;
                }
                default: {
                    System.out.println(ConsoleColor.RED + "Votre sélection n'est pas valide." + ConsoleColor.RESET);
                }
            }
        }
    }

    private void notConfiguredTui(){
        System.out.println(ConsoleColor.RED + "Certains éléments doivent être configurés:");
        if (this.gameMode == null)
            System.out.println("- Mode de jeu (rapide, standard, marathon)");
        if (this.playerMode == null)
            System.out.println("- Mode de joueurs (Humain vs Humain, Humain vs Bot, Bot vs Bot)");
        else {
            if ((this.playerMode == PlayerMode.HumanVsHuman) && (this.playerNames.size() != 2))
                System.out.println("- Le mode de joueur spécifié requiert 2 joueurs humains, hors vous en avez " + this.playerNames.size());

            if ((this.playerMode == PlayerMode.HumanVsComputer) && (this.playerNames.size() != 1))
                System.out.println("- Le mode de joueur spécifié requiert 1 joueurs humain, hors vous en avez " + this.playerNames.size());
        }
        System.out.print(ConsoleColor.RESET);
    }

    private void recette(){
        System.out.println("\n");
        System.out.println("\033[1mIngrédients:\033[0m");
        System.out.println("- 1 paquet de biscuits sablés (environ 200g)");
        System.out.println("- 500ml de crème fraîche liquide");
        System.out.println("- 3 cuillères à soupe de sucre");
        System.out.println("- 1 sachet de sucre vanillé");
        System.out.println("- 1 pot de confiture de framboises (environ 350g)");
        System.out.println("- 100g de chocolat noir");

        System.out.println();

        System.out.println("\033[1mPréparation:\033[0m");
        System.out.println("1. \033[1mPréparer la crème Chantilly:\033[0m");
        System.out.println("    * Fouetter la crème fraîche liquide avec le sucre et le sucre vanillé jusqu'à obtenir une chantilly ferme.");

        System.out.println();

        System.out.println("2. \033[1mMontage du trifle:\033[0m");
        System.out.println("    * Émietter les biscuits sablés dans le fond d'un plat transparent.");
        System.out.println("    * Recouvrir de confiture de framboises.");
        System.out.println("    * Étaler une couche de crème Chantilly.");
        System.out.println("    * Répéter les couches jusqu'à épuisement des ingrédients.");
        System.out.println("    * Terminer par une couche de crème Chantilly.");

        System.out.println();

        System.out.println("3. \033[1mDécoration:\033[0m");
        System.out.println("    * Râper le chocolat noir et le parsemer sur la crème Chantilly.");
        System.out.println("    * Placer le trifle au réfrigérateur pendant au moins 2 heures avant de servir.");
        System.out.println();
    }

    public Tui() {}
}
