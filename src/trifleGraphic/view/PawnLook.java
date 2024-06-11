package trifleGraphic.view;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import trifleGraphic.boardifierGraphic.model.GameElement;
import trifleGraphic.boardifierGraphic.view.ElementLook;
import trifleGraphic.model.Pawn;

import static trifleGraphic.view.TrifleBoardLook.PAWN_SIZE;

public class PawnLook extends ElementLook {
    private Circle circle;

    public static int RADIUS = (PAWN_SIZE / 2) - 2;

    public static int STROKE_WIDTH = 2;

    public PawnLook(GameElement element) {
        super(element);

        render();
    }

    @Override
    public void onSelectionChange(){
        // TODO Permet de changer l'affichage quand il est sélectionné
    }

    @Override
    public void onFaceChange() {}

    protected void render() {
        Pawn pawn = (Pawn) element;
        // TODO remplacer le cercle par une forme de tour
        circle = new Circle();
        circle.setRadius(RADIUS);

        // Define the color
        java.awt.Color pawnColor = Pawn.COLORS[pawn.getColorIndex()];
        Color convertedColor = javafx.scene.paint.Color.color(
                pawnColor.getRed() / 255.0,
                pawnColor.getGreen() / 255.0,
                pawnColor.getBlue() / 255.0
        );
        circle.setFill(convertedColor);

        circle.setStrokeWidth(STROKE_WIDTH);
        circle.setStrokeType(StrokeType.CENTERED);
        circle.setStroke(Color.valueOf(pawn.getPlayerID() == 1 ? "0xfefefe" : "0x000"));


        addShape(circle);
        Text text = new Text(pawn.getChinesePawnName());
        text.setFont(new Font(24));

        // Define the text color, white or black, depending on the color of the pawn

        if (whiteOrBlack(convertedColor) == 0) {
            text.setFill(Color.WHITE);
        } else {
            text.setFill(Color.BLACK);
        }

        Bounds bt = text.getBoundsInLocal();
        text.setX(-bt.getWidth()/2);
        text.setY(text.getBaselineOffset()/2-4);
        addShape(text);
    }

    /**
     *
     * @param color The color in question
     * @return Zero for white, one for black
     */
    public static int whiteOrBlack(Color color) {
        double seuil = 0.75;

        Color adjustedColor = color.brighter();
        double lum = adjustedColor.getBrightness();

        return lum > seuil ? 1 : 0;
    }
}
