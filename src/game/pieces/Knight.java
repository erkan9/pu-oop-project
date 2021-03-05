package game.pieces;

import game.Board;

public class Knight extends Piece {

    private boolean hasFigureMoved;

    public Knight(int figureColumn, int figureRow, boolean isFigureRed, String figureFilePath, Board board) {

        super(figureColumn, figureRow, isFigureRed, figureFilePath, board);
    }

    @Override
    public boolean canMove(int destinationColumn, int destinationRow) {

        return true;
    }
}
