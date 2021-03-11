package game.player;

import game.board.Board;

public class Player {

    protected int dwarfCounter;
    protected int knightCounter;
    protected int elfCounter;

    protected Board board;

    /**
     * Constructor
     * @param dwarfCounter Counter for placed Dwarfs
     * @param knightCounter Counter for placed Knights
     * @param elfCounter Counter for placed Elves
     */
    public Player(int dwarfCounter, int knightCounter, int elfCounter) {

        this.elfCounter = elfCounter;
        this.dwarfCounter = dwarfCounter;
        this.knightCounter = knightCounter;
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
}
