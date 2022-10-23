package snake.studentA;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the App class, where the program starts. It sets the scene with the
 * PaneOrganizer's root pane.
 */
public class App extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * @param stage the primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        PaneOrganizer po = new PaneOrganizer();
        Scene scene = new Scene(po.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Snake!");
        stage.show();
    }
}
