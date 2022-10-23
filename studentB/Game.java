package snake.studentB;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * The logical top-level class of the program that handles setting up the game,
 * the animation timeline, key input, and handling game over.
 */
public class Game {

    private Snake snake;
    private PicnicTile[][] board;
    private Pane gamePane;
    private ScoreController scoreController;
    private boolean gameOver;

    /**
     * Starts the snake game, by setting up the board of squares, adding initial food,
     * and starting the timeline.
     *
     * @param gamePane the pane for the game components to show up on
     * @param scoreController the controller that tracks game score
     */
    public Game(Pane gamePane, ScoreController scoreController) {
        this.gamePane = gamePane;
        this.board = new PicnicTile[Constants.NUM_ROWS][Constants.NUM_COLS];
        for (int row = 0; row < Constants.NUM_ROWS; row++) {
            for (int col = 0; col < Constants.NUM_COLS; col++) {
                if ((row + col) % 2 == 0) {
                    this.board[row][col] = new PicnicTile(gamePane, Color.GREENYELLOW, row, col);
                } else {
                    this.board[row][col] = new PicnicTile(gamePane, Color.GREEN, row, col);
                }
            }
        }

        this.snake = new Snake(this.board, gamePane);
        this.scoreController = scoreController;
        this.gameOver = false;

        gamePane.setOnKeyPressed(e -> this.handleKeyPress(e));
        gamePane.setFocusTraversable(true);
        for (int i = 0; i < Constants.NUM_FRUIT; i++) {
            this.spawnFood();
        }

        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.TIMELINE_DURATION), (e -> this.update()));
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Spawns one random type of food on a random empty tile.
     */
    private void spawnFood() {
        int row;
        int col;
        do {
            row = (int) (Math.random() * Constants.NUM_ROWS);
            col = (int) (Math.random() * Constants.NUM_COLS);
        } while (!(this.board[row][col].getContents() == TileContents.EMPTY));

        PicnicTile tile = this.board[row][col];
        Color foodColor;
        int foodScore;
        switch ((int) (Math.random() * 10)) {
            case 0:
            case 1:
                foodColor = Color.GOLDENROD;
                foodScore = 50;
                break;
            case 2:
                foodColor = Color.MINTCREAM;
                foodScore = 100;
                break;
            case 3:
            case 4:
            case 5:
                foodColor = Color.BLACK;
                foodScore = 25;
                break;
            default:
                foodColor = Color.RED;
                foodScore = 10;
                break;
        }

        tile.addFood(new Food(this.gamePane, foodColor, foodScore,
                tile.getRow() * Constants.SQ_WIDTH, tile.getCol() * Constants.SQ_WIDTH));
    }

    /**
     * Changes the direction of the snake based on key input
     *
     * @param event the key press event
     */
    private void handleKeyPress(KeyEvent event) {
        Direction newDirection = null;
        switch (event.getCode()) {
            case UP:
                newDirection = Direction.UP;
                break;
            case DOWN:
                newDirection = Direction.DOWN;
                break;
            case LEFT:
                newDirection = Direction.LEFT;
                break;
            case RIGHT:
                newDirection = Direction.RIGHT;
                break;
            default:
                break;
        }
        if (newDirection != null && !this.gameOver) {
            this.snake.changeDirection(newDirection);
        }
    }

    /**
     * Updates the game at each tick of the timeline. If the game is over it sets a game
     * over message. Otherwise, it moves the snake, then checks for game over and if the
     * snake ate food.
     */
    private void update() {
        PicnicTile tile = this.snake.move();
        if (tile == null) {
            this.gameOver = true;
            Label label = new Label("Game Over!");
            VBox labelBox = new VBox(label);
            labelBox.setAlignment(Pos.CENTER);
            labelBox.setPrefHeight(this.gamePane.getHeight());
            labelBox.setPrefWidth(this.gamePane.getWidth());
            label.setStyle("-fx-font: italic bold 75px arial, serif;-fx-text-alignment: center;-fx-text-fill: white;");

            Color[] colors = new Color[]{Color.web("#E00009"), Color.web("#E47C00"),
                    Color.web("#ECEF02"), Color.web("#65F400"), Color.web("#51B5FF")};
            DropShadow shadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#E02EF3"),
                    0, 10, 2, 2);
            for (Color color : colors) {
                DropShadow temp = new DropShadow(BlurType.GAUSSIAN, color, 0, 10, 2, 2);
                temp.setInput(shadow);
                shadow = temp;
            }
            label.setEffect(shadow);
            this.gamePane.getChildren().add(labelBox);
            return;
        }

        if (tile.getContents() == TileContents.FOOD) {
            this.scoreController.addToScore(tile.eatFood());
            this.spawnFood();
        }

        tile.addSnake();
    }
}
