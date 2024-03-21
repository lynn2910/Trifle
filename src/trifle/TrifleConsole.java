package trifle;

import trifle.boardifier.view.ConsoleColor;
import trifle.tui.Tui;

import java.io.File;
import java.io.IOException;

public class TrifleConsole {
    private static void hello(){
        try {
            String kamisadoAsset = Utils.readFile(new File("src/trifle/resources/kamisado.asset.text"));
            System.out.println("\n");
            System.out.println(kamisadoAsset);
        } catch (IOException e) {
            System.out.println(ConsoleColor.RED_BOLD + "Cannot load the asset `kamisado.asset.text`." + ConsoleColor.RESET);
        }
    }
    public static void main(String[] args) {
        hello();

        Tui tui;
        if (args.length < 1) {
            tui = new Tui();
            tui.run();
        }
    }
}
