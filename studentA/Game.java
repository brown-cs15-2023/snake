package snake.studentA;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Handles the high-level logic of the program, by running a timeline that updates
 * the snake and board state at each tick of the timeline and by updating the
 * snake direction of key input.
 */
public class Game {

    private Snake snake;
    private Board board;
    private int score;
    private Label scoreLabel;
    private Timeline timeline;
    private Pane gamePane;

    /**
     * Sets up the game by registering the KeyEvent handler on the gamePane and starting
     * the timeline that triggers the game update.
     *
     * @param gamePane the pane on which to add game elements
     * @param scoreLabel a label that tracks score
     */
    public Game(Pane gamePane, Label scoreLabel) {
        this.gamePane = gamePane;
        this.gamePane.setFocusTraversable(true);
        this.board = new Board(this.gamePane);
        this.snake = new Snake(this.board);
        this.scoreLabel = scoreLabel;
        this.score = 0;

        this.startGame();
    }

    /**
     * Sets up the key handler and timeline in order to start the game.
     */
    private void startGame() {
        this.gamePane.setOnKeyPressed((KeyEvent event) -> this.handleKeyInput(event.getCode()));

        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.TIMELINE_DURATION),
                (ActionEvent event) -> this.update());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }


    /**
     * Updates the state of the game, by moving the snake, updating the score,
     * and spawning new food when necessary.
     */
    private void update() {
        SnakeMoveResult result = this.snake.move();

        if (result == SnakeMoveResult.GAME_OVER) {
            this.timeline.stop();
            Label label = new Label("Game Over!");
            VBox labelBox = new VBox(label);
            labelBox.setAlignment(Pos.CENTER);
            labelBox.setPrefHeight(this.gamePane.getHeight());
            labelBox.setPrefWidth(this.gamePane.getWidth());
            label.setStyle("-fx-font: italic bold 75px arial, serif;-fx-text-alignment: center;-fx-text-fill: white;");

            Color[] colors = new Color[]{Color.web("#E00009"), Color.web("#E47C00"), Color.web("#ECEF02"),
                    Color.web("#65F400"), Color.web("#51B5FF")};
            DropShadow shadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#E02EF3"),
                    0, 10, 2, 2);
            for (Color color : colors) {
                DropShadow temp = new DropShadow(BlurType.GAUSSIAN, color, 0, 10, 2, 2);
                temp.setInput(shadow);
                shadow = temp;
            }
            label.setEffect(shadow);
            this.gamePane.getChildren().add(labelBox);
        }

        if (result.getScoreIncrease() > 0) {
            this.score += result.getScoreIncrease();
            this.scoreLabel.setText(Constants.SCORE_LABEL_TEXT + this.score);
            this.board.spawnFood();
        }
    }

    /**
     * Handles key input by changing direction of snake on up, down, left, and
     * right arrow keys.
     *
     * @param code code of the key pressed
     */
    private void handleKeyInput(KeyCode code) {
        switch (code) {
            case UP:
                this.snake.changeDirection(Direction.UP);
                break;
            case DOWN:
                this.snake.changeDirection(Direction.DOWN);
                break;
            case LEFT:
                this.snake.changeDirection(Direction.LEFT);
                break;
            case RIGHT:
                this.snake.changeDirection(Direction.RIGHT);
                break;
            default:
                break;
        }
    }
}
