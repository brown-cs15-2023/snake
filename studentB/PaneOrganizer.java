package snake.studentB;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * The graphical top-level class of the program: sets up the main BorderPane
 * and its sub-Panes.
 */
public class PaneOrganizer {

    private BorderPane root;

    /**
     * Sets up the graphical user interface.
     */
    public PaneOrganizer() {
        this.root = new BorderPane();

        Pane gamePane = new Pane();
        this.root.setCenter(gamePane);

        HBox lowerPane = new HBox();
        lowerPane.setAlignment(Pos.CENTER);
        lowerPane.setSpacing(Constants.SCORE_PANE_SPACING);
        lowerPane.setPrefHeight(Constants.SCORE_PANE_HEIGHT);
        this.root.setBottom(lowerPane);

        new Game(gamePane, new ScoreController(lowerPane));
    }


    /**
     * Gets the root Pane of the application.
     * @return root pane
     */
    public BorderPane getRoot() {
        return this.root;
    }
}
