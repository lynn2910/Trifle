package trifle.view;

import trifle.boardifier.model.GameElement;
import trifle.boardifier.view.ElementLook;

import java.util.Arrays;

public class CellBackgroundLook extends ElementLook {
    public CellBackgroundLook(GameElement element) {
        super(element);
    }

    protected void render(){
        System.out.println(Arrays.deepToString(shape));
    }
}
