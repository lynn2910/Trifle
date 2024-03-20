package trifle.rules;

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

    public String toString(GameMode mode) {
        return switch (mode) {
            case Fast -> "Rapide";
            case Standard -> "Standard";
            case Marathon -> "Marathon";
            default -> "Mode inconnu";
        };
    }
}
