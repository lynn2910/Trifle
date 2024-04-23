import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import trifle.control.TrifleController;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTrifleController {
    /**
     * Test a list of valid and invalid moves for the method `extractPawnIndex` from `TrifleController`
     * @param inputMove the input move, such as a1g7 or cg7
     * @param expectedIndex the expected output
     */
    @ParameterizedTest
    @CsvSource({
            "a1g7, 0",
            "cg7,  0",
            "b1b1, 1",
            "bg7,  1",
            "c1d2, 2",
            "pg7, 3",
            "c1g7, 3",
            "dg7, 4",
            "d1g7, 4",
            "eg7, 5",
            "e1g7, 5",
            "fg7, 6",
            "f1g7, 6",
            "gg7, 7",
            "g1g7, 7",
            "ng7, 8",
            "h1g7, 8",

            "11b7, ", // null because of two 11 at the start
            "aa1g7, ", // null because a is repeated two times
            ",", // well, you got it
            "a9g7,", // null because a9 is out of bound
            "a0g7,", // null because a0 is out of bound
            "i1g7,", // null because the letter I is out of bound
    })
    public void extractPawnIndex(String inputMove, Integer expectedIndex) {
        TrifleController controller = Mockito.mock(TrifleController.class);

        Mockito.when(controller.extractPawnIndex(inputMove)).thenReturn(expectedIndex);

        Integer actualIndex = controller.extractPawnIndex(inputMove);
        Mockito.verify(controller).extractPawnIndex(inputMove);

        assertEquals(expectedIndex, actualIndex);
    }

    /**
     * Test a list of valid and invalid moves for the method `extractRequestedMove` from `TrifleController`
     * @param inputMove the input move, such as a1g7 or cg7
     * @param x the expected x
     * @param y the expected y
     */
    @ParameterizedTest
    @CsvSource({
            "a1g7, 6, 6",
            "cg7,  6, 6",
            "b1b1, 1, 1",
            "bg7,  6, 6",
            "c1d2, 3, 2",
            "pg7,  6, 6",
            "c1g7, 6, 6",
            "dg7,  6, 6",
            "d1g7, 6, 6",
            "eg7,  6, 6",
            "e1g7, 6, 6",
            "fg7,  6, 6",
            "f1g7, 6, 6",
            "gg7,  6, 6",
            "g1g7, 6, 6",
            "ng7,  6, 6",
            "h1g7, 6, 6",

            "11l7,,", // null because l7 is out of bound
            ",,", // well, you got it
            "a9g8,,", // null because g8 is out of bound
            "a0z7,,", // null because z7 is out of bound
            "i1go,,", // null because go is not a valid action
    })
    public void extractRequestedMove(String inputMove, Integer x, Integer y) {
        TrifleController controller = Mockito.mock(TrifleController.class);

        if (x != null && y != null)
            Mockito.when(controller.extractRequestedMove(inputMove)).thenReturn(new Point(x, y));
        else
            Mockito.when(controller.extractRequestedMove(inputMove)).thenReturn(null);

        Point p = controller.extractRequestedMove(inputMove);
        Mockito.verify(controller).extractRequestedMove(inputMove);

        if (x == null || y == null) {
            assertEquals(p, null);
        } else {
            assertEquals(x, p.x);
            assertEquals(y, p.y);
        }
    }
}
