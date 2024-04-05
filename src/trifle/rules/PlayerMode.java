package trifle.rules;

/**
 * The different player modes available.
 */
public enum PlayerMode {
    /**
     * Two human players.
     */
    HumanVsHuman,
    /**
     * A human player against a computer.
     */
    HumanVsComputer,
    /**
     * Two computers, or should I say, ChatGPT vs. Terminators.
     */
    ComputerVsComputer;

    public String toString() {
        return switch (this) {
            case HumanVsHuman -> "Joueur contre Joueur";
            case HumanVsComputer -> "Joueur contre Ordinateur";
            case ComputerVsComputer -> "Ordinateur contre Ordinateur";
            default -> "Mode inconnu";
        };
    }
}
