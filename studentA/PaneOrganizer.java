package snake.studentA;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Organizes the JavaFX graphical structure of the program. It
 * creates the root BorderPane with the gamePane in the center and a bottomPane
 * with Quit button and score label.
 */
public class PaneOrganizer {

    private BorderPane root;

    /**
     * Constructs the PaneOrganizer and does the graphical setup.
     */
    public PaneOrganizer() {
        this.root = new BorderPane();

        Pane gamePane = new Pane();
        this.root.setCenter(gamePane);

        Pane bottomPane = this.createBottomPane();
        Label score = new Label(Constants.SCORE_LABEL_TEXT + 0);
        bottomPane.getChildren().add(score);
        this.root.setBottom(bottomPane);

        new Game(gamePane, score);
    }

    /**
     * Creates and styles the bottom pane as an HBox with a quit button.
     *
     * @return bottom pane
     */
    private HBox createBottomPane() {
        HBox lowerPane = new HBox();
        lowerPane.setAlignment(Pos.CENTER);
        lowerPane.setSpacing(Constants.SCORE_PANE_SPACING);
        lowerPane.setPrefHeight(Constants.SCORE_PANE_HEIGHT);
        Button quit = new Button("Quit");
        quit.setFocusTraversable(false);
        quit.setOnAction(ActionEvent -> { Platform.exit(); } );
        lowerPane.getChildren().add(quit);
        return lowerPane;
    }


    /**
     * Gets the root of the program.
     *
     * @return program's root BorderPane
     */
    public BorderPane getRoot() {
        return this.root;
    }
}
