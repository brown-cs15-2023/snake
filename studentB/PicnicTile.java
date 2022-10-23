package snake.studentB;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Wraps a square to represent one picnic tile on the board.
 */
public class PicnicTile {

    private Rectangle rect;
    private TileContents contents;
    private Food food;
    private Color originalColor;
    private int row;
    private int col;

    /**
     * Creates a picnic tile.
     * @param gamePane pane where the square should be added
     * @param color color of the square
     * @param row row of the square
     * @param col column of the square
     */
    public PicnicTile(Pane gamePane, Color color, int row, int col) {
        this.row = row;
        this.col = col;
        this.rect = new Rectangle(col * Constants.SQ_WIDTH, row * Constants.SQ_WIDTH, Constants.SQ_WIDTH, Constants.SQ_WIDTH);
        this.originalColor = color;
        this.reset();
        gamePane.getChildren().add(this.rect);
    }

    /**
     * Resets the tile to its initial state, without food.
     */
    public void reset() {
        this.contents = TileContents.EMPTY;
        this.rect.setFill(this.originalColor);
        if (this.food != null) {
            this.food.eat();
        }
        this.food = null;
    }

    /**
     * Sets the content of the tile to be snake.
     */
    public void addSnake() {
        this.contents = TileContents.SNAKE;
    }

    /**
     * Sets the content of the tile to be food.
     * @param food the food to be on the tile
     */
    public void addFood(Food food) {
        this.food = food;
        this.contents = TileContents.FOOD;
    }

    /**
     * Eats the food on the tile and sets the content of the tile to be empty.
     * @return the score of the food eaten
     */
    public int eatFood() {
        int score = this.food.eat();
        this.food = null;
        this.contents = TileContents.EMPTY;
        return score;
    }

    /**
     * Gets the contents of the tile.
     * @return contents of the tile
     */
    public TileContents getContents() {
        return this.contents;
    }

    /**
     * Gets the row value of the tile.
     * @return row of the tile
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Gets the column value of the tile.
     * @return column of the tile
     */
    public int getCol() {
        return this.col;
    }
}
