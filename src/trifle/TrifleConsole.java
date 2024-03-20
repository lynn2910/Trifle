package trifle;

import trifle.tui.Tui;

public class TrifleConsole {
    public static void main(String[] args) {
        Tui tui;
        if (args.length < 1) {
            tui = new Tui();
            tui.run();
        }
    }
}
