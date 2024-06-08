package trifleGraphic.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import trifleGraphic.boardifierGraphic.view.RootPane;

public class TrifleRootPane extends RootPane {
    public TrifleRootPane() {
        super();
    }

    @Override
    public void createDefaultGroup(){
        // TODO on mettra ici la configuration de la partie :)

        Rectangle frame = new Rectangle(600, 500, Color.TRANSPARENT);
        Text text = new Text("Playing to the Kamisado");
        text.setFont(new Font(15));
        text.setFill(Color.BLACK);
        text.setX(10);
        text.setY(50);
        // put shapes in the group
        group.getChildren().clear();
        group.getChildren().addAll(frame, text);
    }
}
