package snake.studentA;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardSquareTest {
    @Test
    public void testTileIsEmpty(){

        Pane gamePane = new Pane();
        Board board = new Board(gamePane);
        Pellet pellet = new Pellet(gamePane, Color.RED, Constants.FOOD_1_SCORE, 1, 1);
        BoardSquare tile = board.tileAt(1,1);

        tile.addPellet(pellet);
        assertFalse(board.tileAt(1,1).isEmpty());

        tile.addSnake(); //eats pellet, but adds snake
        assertFalse(board.tileAt(1,1).isEmpty());

        tile.reset(); // removes snake
        assertTrue(board.tileAt(1,1).isEmpty());

    }


}
