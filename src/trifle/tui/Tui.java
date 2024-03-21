package trifle.tui;

import trifle.boardifier.view.ConsoleColor;
import trifle.rules.GameMode;
import trifle.rules.PlayerMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * TODO add the configuration of the bot.s strategy.ies
 */
public class Tui {
    private final List<String> playerNames = new ArrayList<>();
    private GameMode gameMode;
    private PlayerMode playerMode;

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Draw the navigation
     */
    private void drawNavMenu(){
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

    /**
     * Check whether the configuration is ready
     * @return true if the config is ready to be played, false elsewhere
     */
    private boolean isConfigReady(){
        if (this.gameMode == null || this.playerMode == null)
            return false;

        return switch (this.playerMode) {
            case HumanVsHuman -> this.playerNames.size() == 2;
            case HumanVsComputer -> this.playerNames.size() == 1;
            case ComputerVsComputer -> this.playerNames.isEmpty();
        };
    }

    /**
     * The main loop
     */
    public void run() {
        boolean run = true;

        while (run){
            this.drawNavMenu();
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
                    this.definePlayers();
                    break;
                }
                // On affiche la configuration actuelle
                case "4": {
                    this.showActualConfiguration();
                    break;
                }
                // Lancer la partie
                case "5": {
                    if (isConfigReady())
                        run = false;
                    else
                        this.notConfigured();

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

    /**
     * Define the players
     */
    private void definePlayers(){
        if (this.playerMode == null) {
            System.out.println(ConsoleColor.RED + "Vous devez sélectionner le mode de joueurs avant de configurer les joueurs." + ConsoleColor.RESET);
            return;
        }

        int playersNb = switch (this.playerMode) {
            case HumanVsHuman -> 2;
            case HumanVsComputer -> 1;
            case ComputerVsComputer -> 0;
        };

        if (playersNb < 1) {
            System.out.println(ConsoleColor.YELLOW + "Aucun joueur humain n'est requis pour ce mode de joueur." + ConsoleColor.RESET);
            return;
        }

        // On définit les joueurs
        for (int i = 0; i < playersNb; i++)
            this.addPlayer(i + 1);

        System.out.println(ConsoleColor.GREEN + "Tout les joueurs ont été enregistré." + ConsoleColor.RESET);
    }

    /**
     * Add a single player
     * @param nb The number of the player (to say "Player n°<nb>"), it's purely aesthetic
     */
    private void addPlayer(int nb){
        System.out.println("\nConfiguration du joueur n°" + nb);

        boolean r = true;
        String name = "";
        while (r) {
            System.out.print("Quel est son nom? ");

            name = scanner.nextLine().trim();
            r = !this.yesOrNo("Le nom du joueur n°" + nb + " sera '" + name + "', êtes-vous sûr? (y/n) ");
        }
        this.playerNames.add(name);
        System.out.println("Le nom du joueur n°" + nb + " sera '" + name + "'");
    }

    /**
     * A helper function to check if the user says yes or no
     * @param msg The message
     * @return true for yes, false for no
     */
    private boolean yesOrNo(String msg){
        while (true) {
            System.out.print(msg);
            String l = scanner.nextLine().trim().toLowerCase();

            if (l.isEmpty())
                return true;

            switch (l) {
                case "y": case "yes": case "o": case "oui":
                    return true;
                case "n": case "no": case "non":
                    return false;
                default:
                    System.out.println(ConsoleColor.RED + "Vous devez répondre Y ou N." + ConsoleColor.RESET);
            }
        }
    }

    /**
     * Set the game mode for the game
     */
    private void setGameMode(){
        while (true){
            System.out.println();
            System.out.println("Définition du mode de jeu");
            System.out.println("Modes de jeu disponibles:");
            System.out.println("(a) " + GameMode.Fast     + "\n      " + GameMode.Fast.getDescription());
            System.out.println("(b) " + GameMode.Standard + "\n      " + GameMode.Standard.getDescription());
            System.out.println("(c) " + GameMode.Marathon + "\n      " + GameMode.Marathon.getDescription());
            System.out.println("Tapez 'exit' pour quitter ce menu");
            System.out.print(">> ");

            String selection = scanner.nextLine().toLowerCase().trim();
            switch (selection){
                case "a": {
                    this.gameMode = GameMode.Fast;
                    System.out.println("\nMode de jeu sélectionné: " + ConsoleColor.WHITE_BOLD + GameMode.Fast + ConsoleColor.RESET);
                    return;
                }
                case "b": {
                    this.gameMode = GameMode.Standard;
                    System.out.println("\nMode de jeu sélectionné: " + ConsoleColor.WHITE_BOLD + GameMode.Standard + ConsoleColor.RESET);
                    return;
                }
                case "c": {
                    this.gameMode = GameMode.Marathon;
                    System.out.println("\nMode de jeu sélectionné: " + ConsoleColor.WHITE_BOLD + GameMode.Marathon + ConsoleColor.RESET);
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

    /**
     * Set the player mode for the game
     */
    private void setPlayerMode(){
        while (true) {
            System.out.println();
            System.out.println("Définition du mode de joueurs");
            System.out.println(ConsoleColor.RED + "Les joueurs enregistré seront supprimé." + ConsoleColor.RESET);
            System.out.println("Modes de joueurs disponibles:");
            System.out.println("(a) " + PlayerMode.HumanVsHuman);
            System.out.println("(b) " + PlayerMode.HumanVsComputer);
            System.out.println("(c) " + PlayerMode.ComputerVsComputer);
            System.out.println("Tapez 'exit' pour quitter ce menu");
            System.out.print(">> ");

            String selection = scanner.nextLine().toLowerCase().trim();
            switch (selection) {
                case "a": {
                    this.playerMode = PlayerMode.HumanVsHuman;
                    System.out.println("\nMode de joueur sélectionné: " + ConsoleColor.WHITE_BOLD + PlayerMode.HumanVsHuman + ConsoleColor.RESET);
                    this.resetPlayers();
                    return;
                }
                case "b": {
                    this.playerMode = PlayerMode.HumanVsComputer;
                    System.out.println("\nMode de joueur sélectionné: " + ConsoleColor.WHITE_BOLD + PlayerMode.HumanVsComputer + ConsoleColor.RESET);
                    this.resetPlayers();
                    return;
                }
                case "c": {
                    this.playerMode = PlayerMode.ComputerVsComputer;
                    System.out.println("\nMode de joueur sélectionné: " + ConsoleColor.WHITE_BOLD + PlayerMode.ComputerVsComputer + ConsoleColor.RESET);
                    this.resetPlayers();
                    return;
                }
                default: {
                    System.out.println(ConsoleColor.RED + "Votre sélection n'est pas valide." + ConsoleColor.RESET);
                }
            }
        }
    }

    /**
     * Reset the player list for this configuration
     */
    private void resetPlayers(){
        this.playerNames.clear();
    }

    /**
     * Print the required information to start the game
     */
    private void notConfigured(){
        System.out.println(ConsoleColor.RED + "Certains éléments doivent être configurez:");
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

    /**
     * Show the actual configuration in a formatted-way
     */
    private void showActualConfiguration(){
        System.out.println("\nConfiguration actuelle:");
        System.out.println("  Mode de jeu:        "
                + (this.gameMode == null ? ConsoleColor.WHITE_BOLD + "Aucun mode de jeu défini" + ConsoleColor.RESET : this.gameMode));
        System.out.println("  Mode de joueur:     "
                + (this.playerMode == null ? ConsoleColor.WHITE_BOLD + "Aucun mode de joueur défini" + ConsoleColor.RESET : this.playerMode));

        System.out.print  ("  Joueurs enregistré: ");
        if (this.playerNames.isEmpty()) {
            System.out.println(ConsoleColor.WHITE_BOLD + "Aucun joueur enregistré" + ConsoleColor.RESET);
        } else {
            String playerList = String.join(", ", this.playerNames);
            System.out.println(playerList);
        }

        System.out.println();
    }

    /**
     * Et paf ça fait des chocapics
     */
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

    public GameMode getGameMode() {
        return gameMode;
    }

    public PlayerMode getPlayerMode() {
        return playerMode;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public Tui() {}
}
