package snake.studentC;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * This App class runs my entire Snake game. It manages the graphical setup of my app, creates
 * the timeline of the snake moving, keeps track of the score and updates the label, creates
 * the board of squares, spawns food on the board, turns the snake on key input, and checks for
 * game over when the snake runs into itself or off screen.
 */
public class App extends Application {

    private Rectangle[][] board;
    private ArrayList<Circle> food;
    private Pane gamePane;
    private int score;
    private Label scoreLabel;
    private boolean gameOver;
    private ArrayList<Rectangle> snake;
    private int currDirection;
    private int nextDirection;

    /**
     * The main entry point for all JavaFX applications.
     * This method sets up the Graphical User Interface, creates the 2D array
     * board graphically and logically, and sets up the event handlers for the
     * timeline and key handler.
     *
     * @param stage the primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        Pane gamePane = new Pane();
        root.setCenter(gamePane);

        HBox bottomPane = new HBox();
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setSpacing(20);
        bottomPane.setPrefHeight(60);
        Button quit = new Button("Quit");
        quit.setFocusTraversable(false);
        quit.setOnAction(ActionEvent -> {
            Platform.exit(); });

        bottomPane.getChildren().add(quit);
        this.scoreLabel = new Label("Score: " + 0);
        bottomPane.getChildren().add(this.scoreLabel);
        root.setBottom(bottomPane);

        this.gamePane = gamePane;
        this.score = 0;
        this.board = new Rectangle[15][15];
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                this.board[row][col] = new Rectangle(col * 35, row * 35, 35, 35);
                if ((row + col) % 2 == 0) {
                    this.board[row][col].setFill(Color.GREENYELLOW);
                } else {
                    this.board[row][col].setFill(Color.GREEN);
                }
                this.gamePane.getChildren().add(this.board[row][col]);
            }
        }

        this.snake = new ArrayList<>();

        for (int[] coord : new int[][]{{7,2}, {7,1}, {7,0}}) {
            Rectangle square = this.board[coord[0]][coord[1]];
            square.setFill(Color.BLACK);
            this.snake.add(square);
        }

        this.gameOver = false;

        gamePane.setOnKeyPressed(e -> this.handleKeyPress(e));
        gamePane.setFocusTraversable(true);

        this.food = new ArrayList<>();
        this.spawnFood();
        this.spawnFood();
        this.spawnFood();

        this.currDirection = 0;
        this.nextDirection = 0;

        KeyFrame kf = new KeyFrame(Duration.seconds(0.4), (e -> this.update()));
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(root, 525, 585);
        stage.setScene(scene);
        stage.setTitle("Snake");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * This method spawns one food on the board by finding a square on the board that's
     * available, then randomly generating one of the food types.
     */
    private void spawnFood() {
        int row;
        int col;
        boolean hasFood;
        do {
            row = (int) (Math.random() * 15);
            col = (int) (Math.random() * 15);
            hasFood = false;
            for (Circle myFood : this.food) {
                if ((int) myFood.getCenterY() / 35 == row &&
                        (int) myFood.getCenterX() / 35 == col) {
                    hasFood = true;
                    break;
                }
            }
        } while (this.board[row][col].getFill().equals(Color.BLACK) || hasFood);

        Color foodColor;
        switch ((int) (Math.random() * 10)) {
            case 0:
            case 1:
                foodColor = Color.GOLDENROD;
                break;
            case 2:
                foodColor = Color.MINTCREAM;
                break;
            case 3:
            case 4:
            case 5:
                foodColor = Color.BLACK;
                break;
            default:
                foodColor = Color.RED;
                break;
        }

        Circle newFood = new Circle(col * 35 + 17, row * 35 + 17, 12, foodColor);
        this.gamePane.getChildren().add(newFood);
        this.food.add(newFood);
    }

    /**
     * This method handles a key press by changing the direction of the snake movement,
     * as long as it isn't changing by 180º.
     *
     * @param event the key event representing that key press
     */
    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                if (this.currDirection != 1) {
                    this.nextDirection = 3;
                }
                break;
            case DOWN:
                if (this.currDirection != 3) {
                    this.nextDirection = 1;
                }
                break;
            case LEFT:
                if (this.currDirection != 0) {
                    this.nextDirection = 2;
                }
                break;
            case RIGHT:
                if (this.currDirection != 2) {
                    this.nextDirection = 0;
                }
                break;
            default:
                break;
        }
    }

    /**
     * This method handles how the game updates at each increment of the timeline. If the game is
     * over it creates and adds a Game Over label. Otherwise, it moves the snake, checks
     * for game over, and updates the score and snake length if it has eaten food.
     */
    private void update() {
        if (this.gameOver) {
            Label label = new Label("Game Over!");
            VBox labelBox = new VBox(label);
            labelBox.setAlignment(Pos.CENTER);
            labelBox.setPrefHeight(this.gamePane.getHeight());
            labelBox.setPrefWidth(this.gamePane.getWidth());
            label.setStyle("-fx-font: italic bold 75px arial, serif;-fx-text-alignment: center;-fx-text-fill: white;");

            Color[] colors = new Color[]{Color.web("#E00009"), Color.web("#E47C00"), Color.web("#ECEF02"), Color.web("#65F400"), Color.web("#51B5FF")};
            DropShadow shadow = new DropShadow(BlurType.GAUSSIAN, Color.web("#E02EF3"), 0, 10, 2, 2);
            for (Color color : colors) {
                DropShadow temp = new DropShadow(BlurType.GAUSSIAN, color, 0, 10, 2, 2);
                temp.setInput(shadow);
                shadow = temp;
            }
            label.setEffect(shadow);
            this.gamePane.getChildren().add(labelBox);
            return;
        }

        this.currDirection = this.nextDirection;
        int newRow = (int) this.snake.get(0).getY() / 35;
        int newCol = (int) this.snake.get(0).getX() / 35;

        switch (this.currDirection) {
            case 0:
                newCol++;
                break;
            case 1:
                newRow++;
                break;
            case 2:
                newCol--;
                break;
            case 3:
                newRow--;
                break;
            default:
                break;
        }

        if (newCol >= 15 || newCol < 0 || newRow >= 15 || newRow < 0
                || this.board[newRow][newCol].getFill().equals(Color.BLACK)) {
            this.gameOver = true;
            return;
        }

        boolean ateFood = false;
        for (Circle myFood : this.food) {
            if ((int) myFood.getCenterY() / 35 == newRow
                    && (int) myFood.getCenterX() / 35 == newCol) {
                if (myFood.getFill().equals(Color.GOLDENROD)) {
                    this.score += 50;
                } else if (myFood.getFill().equals(Color.MINTCREAM)) {
                    this.score += 100;
                } else if (myFood.getFill().equals(Color.BLACK)) {
                    this.score += 25;
                } else if (myFood.getFill().equals(Color.RED)) {
                    this.score += 10;
                }
                this.scoreLabel.setText("Score: " + this.score);

                this.food.remove(myFood);
                this.gamePane.getChildren().remove(myFood);
                this.spawnFood();
                ateFood = true;
                break;
            }
        }

        if (!ateFood) {
            Rectangle square = this.snake.remove(this.snake.size() - 1);
            int squareRow = (int) square.getY() / 35;
            int squareCol = (int) square.getX() / 35;
            if ((squareRow + squareCol) % 2 == 0) {
                square.setFill(Color.GREENYELLOW);
            } else {
                square.setFill(Color.GREEN);
            }
        }

        Rectangle newFrontSquare = this.board[newRow][newCol];
        this.snake.add(0, newFrontSquare);
        newFrontSquare.setFill(Color.BLACK);
    }
}
