package rules;

/**
 * The different game modes available
 */
public enum GameMode {
    /**
     * A single round
     */
    Fast,
    /**
     * A standard game with multiple rounds
     */
    Standard,
    /**
     * A game with a large number of rounds
     */
    Marathon;

    /**
     * @return The name
     */
    public String toString() {
        return switch (this) {
            case Fast     -> "Fast";
            case Standard -> "Standard";
            case Marathon -> "Marathon";
        };
    }

    public String getDescription(){
        // TODO ajouter les descriptions des différents modes de jeu
        return switch (this) {
            case Fast     -> "One set";
            case Standard -> "3 sets";
            default       -> "";
        };
    }

    public int numberOfRounds(){
        return switch (this) {
            case Fast -> 1;
            case Standard -> 3;
            case Marathon -> 5;
        };
    }

    public static GameMode defaultValue() {
        return Fast;
    }
}
