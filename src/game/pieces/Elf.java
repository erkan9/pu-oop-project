package game.pieces;

import game.Board;

public class Elf extends Piece {

    private boolean hasFigureMoved;

    public Elf(int figureColumn, int figureRow, boolean isFigureRed, String figureFilePath, Board board) {

        super(figureColumn, figureRow, isFigureRed, figureFilePath, board);
        hasFigureMoved = false;
    }

    @Override
    public boolean canMove(int destinationColumn, int destinationRow) {

        return true;
    }
}
