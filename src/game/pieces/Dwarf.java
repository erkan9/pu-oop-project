package game.pieces;

import game.Board;

public class Dwarf extends Piece {

    private boolean hasFigureMoved;
    
    public Dwarf(int figureColumn, int figureRow, boolean isFigureRed, String figureFilePath, Board board) {

        super(figureColumn, figureRow, isFigureRed, figureFilePath, board);
        hasFigureMoved = false;
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
    public boolean canMove(int destinationColumn, int destinationRow){

        // Remember: Dwarf can attack 6, shield 2, health 12, speed 2
        // cant attack own pieces

        // WRITE CODE HERE

        return true;
    }
}
