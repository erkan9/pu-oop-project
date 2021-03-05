package game.pieces;

import game.Board;

public class Elf extends Piece {

    private boolean hasFigureMoved;

    public Elf(int figureColumn, int figureRow, boolean isFigureRed, String figureFilePath, Board board)
    {
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

        // Remember: Elf can attack 5, shield 1, health 10, speed 3
        // cant attack own pieces

        // WRITE CODE HERE
        
        return true;
    }
}
