package game.pieces;

import game.Board;

public class Obstacle {

    private int figureColumn;
    private int figureRow;
    private String figureFilePath;
    public Board board;

    public Obstacle(int figureColumn, int figureRow, String figureFilePath, Board board) {

        this.figureColumn = figureColumn;
        this.figureRow = figureRow;
        this.figureFilePath = figureFilePath;
        this.board = board;
    }

    public int getFigureColumn() {
        return figureColumn;
    }

    public int getFigureRow() {
        return figureRow;
    }

    public String getFigureFilePath() {
        return figureFilePath;
    }
}
