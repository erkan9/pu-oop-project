package game.player;

import game.board.Board;

public class Player {

    private int dwarfCounter;
    private int knightCounter;
    private int elfCounter;

    //public Board board;

    public Player(int dwarfCounter, int knightCounter, int elfCounter) {

        this.elfCounter = elfCounter;
        this.dwarfCounter = dwarfCounter;
        this.knightCounter = knightCounter;
    }

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
