package game.player;

import game.board.Board;

public class Player {

    protected int dwarfCounter;
    protected int knightCounter;
    protected int elfCounter;
    protected int points;
    protected int killedPieces;

    protected Board board;

    /**
     * Constructor
     * @param dwarfCounter Counter for placed Dwarfs
     * @param knightCounter Counter for placed Knights
     * @param elfCounter Counter for placed Elves
     */
    public Player(int dwarfCounter, int knightCounter, int elfCounter, int points, int killedPieces) {

        this.elfCounter = elfCounter;
        this.dwarfCounter = dwarfCounter;
        this.knightCounter = knightCounter;
        this.points = points;
        this.killedPieces = killedPieces;
    }

    // Getters and Setters

    public int getDwarfCounter() {
        return dwarfCounter;
    }

    public void setDwarfCounter(int dwarfCounter) {
        this.dwarfCounter = dwarfCounter;
    }

    public int getKnightCounter() {
        return knightCounter;
    }

    public void setKnightCounter(int knightCounter) {
        this.knightCounter = knightCounter;
    }

    public int getElfCounter() {
        return elfCounter;
    }

    public void setElfCounter(int elfCounter) {
        this.elfCounter = elfCounter;
    }

    public int getPoints() { return points; }

    public void setPoints(int points) { this.points = points; }

    public int getKilledPieces() { return killedPieces; }

    public void setKilledPieces(int killedPieces) { this.killedPieces = killedPieces; }
}
