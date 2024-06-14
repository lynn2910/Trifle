import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trifleGraphic.boardifierGraphic.control.Controller;
import trifleGraphic.boardifierGraphic.model.Model;
import trifleGraphic.boardifierGraphic.model.action.ActionList;
import trifleGraphic.controllers.TrifleDecider;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestTrifleDecider {

    private TrifleDecider trifleDecider;
    private Model mockModel;
    private Controller mockController;

    @BeforeEach
    public void setUp() {
        mockModel = mock(Model.class);
        mockController = mock(Controller.class);

        trifleDecider = new TrifleDecider(mockModel, mockController);
    }

    @Test
    public void testDecide() {
        ActionList actions = trifleDecider.decide();

        assertNotNull(actions);
        System.out.println("Bot called");
    }
}
