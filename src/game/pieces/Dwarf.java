package game.pieces;

import game.board.Board;

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

        Piece isTherePiece = board.getPiece(destinationColumn, destinationRow);

        if(board.isMovementChosen) {

            if (board.chosenMove) {

                if (isTherePiece != null) {

                    if(!isThereYourFigure(isTherePiece)) {

                        System.out.println("You can't do that");
                        return false;
                    }
                }
                return isDwarfCanBeMoved(destinationColumn, destinationRow);
            }
            if (board.chosenHeal) {

                System.out.println("I checked for Heal, figure is not moved");
                return false;
            }
            if (board.chosenAttack) {

                System.out.println("I checked for Attack, figure is not moved");
                return false;
            }
        }
        // Remember: Dwarf can attack 6, shield 2, health 12, speed 2
        // cant attack own pieces

        // WRITE CODE HERE
        return false;
    }

    /**
     * Method that was created to check if can kill own figures, then changed so cant kill any figures
     * @param isTherePiece Object that contains information about the place where i want to but my figure
     * @return boolean to check if I can put it there
     */
    private boolean isThereYourFigure(Piece isTherePiece) {

        if (isTherePiece.isRed() && this.isRed()) {

            return false;
        }
        isTherePiece.isBlack();

        return false;
    }

    /**
     * Method that checks if the Dwarf can be moved
     * @param destinationColumn Column that the players want to place its figure
     * @param destinationRow Row that the players want to place its figure
     * @return boolean to check figure can be moved
     */
    private boolean isDwarfCanBeMoved(int destinationColumn, int destinationRow) {

        return isDwarfMovingNorthOrSouth(destinationColumn, destinationRow) ||
                isDwarfMovingWestOrEast(destinationColumn, destinationRow)  ||
                isDwarfMovingDiagonal(destinationColumn, destinationRow);
    }

    /**
     * Method that checks if the Dwarf is doing 2 moves by going Diagonal
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow Row that the players want to place it's figure
     * @return boolean to check if the figure can go North or South
     */
    private boolean isDwarfMovingDiagonal(int destinationColumn, int destinationRow) {

        boolean didDwarfWentSideWayRow = Math.abs(destinationRow - this.getFigureRow()) == 1;
        boolean didDwarfWentSideWayColumn = Math.abs(destinationColumn - this.getFigureColumn()) == 1;

        return didDwarfWentSideWayRow && didDwarfWentSideWayColumn;
    }

    /**
     * Method that checks if the Dwarf is moving South or North
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow Row that the players want to place it's figure
     * @return boolean to check if the figure can go North or South
     */
    private boolean isDwarfMovingNorthOrSouth(int destinationColumn, int destinationRow) {

        boolean didDwarfChangeRow = Math.abs(destinationRow - this.getFigureRow()) <= 2;
        boolean didDwarfChangeColumn = Math.abs(destinationColumn - this.getFigureColumn()) == 0;

        return didDwarfChangeColumn && didDwarfChangeRow;
    }

    /**
     * Method that checks if the Dwarf is moving West or East
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow Row that the players want to place it's figure
     * @return if the figure can go West or East
     */
    private boolean isDwarfMovingWestOrEast(int destinationColumn, int destinationRow) {

        boolean didDwarfChangeRow = Math.abs(destinationRow - this.getFigureRow()) == 0;
        boolean didDwarfChangeColumn = Math.abs(destinationColumn - this.getFigureColumn()) <= 2;

        return didDwarfChangeColumn && didDwarfChangeRow;
    }
}