package game.pieces;

import game.board.Board;

public class Knight extends Piece {

    private boolean hasFigureMoved;

    public Knight(int figureColumn, int figureRow, boolean isFigureRed, String figureFilePath, Board board) {

        super(figureColumn, figureRow, isFigureRed, figureFilePath, board);
    }

    public void setHasMoved(boolean hasMoved)
    {
        this.hasFigureMoved = hasMoved;
    }

    public boolean getHasMoved()
    {
        return hasFigureMoved;
    }
    
    @Override
    public boolean canMove(int destinationColumn, int destinationRow) {

        // Remember: Dwarf can attack 8, shield 3, health 15, speed 1
        // cant attack own pieces

        // WRITE CODE HERE

        return true;
    }
}
