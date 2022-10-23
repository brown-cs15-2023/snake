package snake.studentB;

/**
 * An enum to represent the direction in which something moves.
 */
public enum Direction {

    UP, DOWN, LEFT, RIGHT;

    /**
     * Gets the opposite direction of the current value.
     * @return opposite direction
     */
    public Direction opposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            default:
                return LEFT;
        }
    }
}
