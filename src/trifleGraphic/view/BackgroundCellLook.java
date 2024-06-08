package trifleGraphic.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import trifleGraphic.boardifierGraphic.model.GameElement;
import trifleGraphic.boardifierGraphic.view.ElementLook;
import trifleGraphic.model.BackgroundCell;
import trifleGraphic.model.Pawn;
import trifleGraphic.model.TrifleBoard;

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
        rectangle.setFill(Color.BLUE);

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
