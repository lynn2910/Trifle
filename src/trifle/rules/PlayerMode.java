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
            case HumanVsHuman       -> "Player vs Player";
            case HumanVsComputer    -> "Player vs Computer";
            case ComputerVsComputer -> "Computer vs Computer";
        };
    }

    public int getBotNumber(){
        return switch (this) {
            case HumanVsHuman       -> 0;
            case HumanVsComputer    -> 1;
            case ComputerVsComputer -> 2;
        };
    }
}
