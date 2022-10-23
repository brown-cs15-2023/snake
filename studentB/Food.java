package snake.studentB;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Wraps a circle to represent a piece of food on a tile.
 */
public class Food {

    private Circle circle;
    private int score;
    private Pane gamePane;

    /**
     * Creates a piece of food.
     * @param gamePane pane where circle should be added
     * @param color color of the food
     * @param score score of the food when eaten
     * @param y y value of circle
     * @param x x value of circle
     */
    public Food(Pane gamePane, Color color, int score, int y, int x) {
        this.gamePane = gamePane;
        this.circle = new Circle(x + Constants.FRUIT_OFFSET, y + Constants.FRUIT_OFFSET,
                Constants.FRUIT_RADIUS, color);
        this.score = score;
        this.gamePane.getChildren().add(this.circle);
    }

    /**
     * Eats the piece of food, by graphically removing it.
     *
     * @return the score of the food
     */
    public int eat() {
        this.gamePane.getChildren().remove(this.circle);
        return this.score;
    }
}
