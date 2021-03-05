package game.pieces;

import game.Board;

public class Dwarf extends Piece {

    private boolean hasFigureMoved;

    public Dwarf(int figureColumn, int figureRow, boolean isFigureRed, String figureFilePath, Board board) {

        super(figureColumn, figureRow, isFigureRed, figureFilePath, board);
        hasFigureMoved = false;
    }

    @Override
    public boolean canMove(int destinationColumn, int destinationRow){

        return true;
    }
}
