package trifleGraphic.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import trifleGraphic.boardifierGraphic.model.GameElement;
import trifleGraphic.boardifierGraphic.view.ElementLook;
import trifleGraphic.model.BackgroundCell;
import trifleGraphic.model.Pawn;

import static trifleGraphic.view.TrifleBoardLook.PAWN_SIZE;


public class BackgroundCellLook extends ElementLook {
    private Rectangle rectangle;

    public BackgroundCellLook(GameElement element) {
        super(element);
        render();
    }

    protected void render(){
        BackgroundCell bc = (BackgroundCell) element;

        this.rectangle = new Rectangle(PAWN_SIZE, PAWN_SIZE);
        this.rectangle.setX(this.rectangle.getX() - ((double) PAWN_SIZE / 2));
        this.rectangle.setY(this.rectangle.getY() - ((double) PAWN_SIZE / 2));

        java.awt.Color bcColor = Pawn.COLORS[bc.getColorIndex()];

        Color convertedColor = javafx.scene.paint.Color.color(
                bcColor.getRed() / 255.0,
                bcColor.getGreen() / 255.0,
                bcColor.getBlue() / 255.0
        );

        rectangle.setFill(convertedColor);

        addShape(rectangle);

//        int colorIndex = TrifleBoard.BOARD[(int) bc.getY()][(int) bc.getX()];
//        Color color = Pawn.BG_COLORS[colorIndex];
//
//        rectangle.setFill(
//                javafx.scene.paint.Color.color(
//                        color.getRed() / 255.0,
//                        color.getGreen() / 255.0,
//                        color.getBlue()  / 255.0
//                )
//        );
    }
}
