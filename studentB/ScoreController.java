package snake.studentB;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Wraps a label to track the game's score with a quit button.
 */
public class ScoreController {
    private int score;
    private Label scoreLabel;

    /**
     * Sets up the pane with the score label and quit button.
     *
     * @param pane the pane for the score and button to reside in
     */
    public ScoreController(HBox pane) {
        Button quit = new Button("Quit");
        quit.setFocusTraversable(false);
        quit.setOnAction((ActionEvent event) -> Platform.exit());

        this.score = 0;
        this.scoreLabel = new Label(Constants.SCORE_LABEL_TEXT + 0);

        pane.getChildren().addAll(quit, this.scoreLabel);
    }

    /**
     * Adds to the current score of the game.
     * @param points amount to increase score by
     */
    public void addToScore(int points) {
        this.score += points;
        this.scoreLabel.setText(Constants.SCORE_LABEL_TEXT + this.score);
    }
}
