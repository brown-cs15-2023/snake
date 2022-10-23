package snake.studentB;

/**
 * Sets up constant values that are used in the app.
 */
public class Constants {

    public static final int NUM_ROWS = 15;
    public static final int NUM_COLS = 15;
    public static final int SQ_WIDTH = 35;
    public static final int SCORE_PANE_HEIGHT = 60;
    public static final int GAME_PANE_HEIGHT = SQ_WIDTH * NUM_ROWS;
    public static final int SCENE_HEIGHT = GAME_PANE_HEIGHT + SCORE_PANE_HEIGHT;
    public static final int SCENE_WIDTH = SQ_WIDTH * NUM_COLS;
    public static final double SCORE_PANE_SPACING = 20;

    public static final int FRUIT_RADIUS = 12;
    public static final int FRUIT_OFFSET = SQ_WIDTH / 2;
    public static final int NUM_FRUIT = 3;

    public static final double TIMELINE_DURATION = 0.4;

    public static final int[][] SNAKE_INITIAL_COORDINATES = {{NUM_ROWS/2, 2}, {NUM_ROWS/2, 1}, {NUM_ROWS/2, 0}};

    public static final String SCORE_LABEL_TEXT = "Score: ";
}
