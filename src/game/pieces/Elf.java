package game.pieces;

import game.board.Board;

public class Elf extends Piece {

    private boolean hasFigureMoved;

    public Elf(int figureColumn, int figureRow, boolean isFigureRed, String figureFilePath, Board board) {
        
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
                return isElfCanBeMoved(destinationColumn, destinationRow);
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
     * Method that checks if the Elf can be moved
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow Row that the players want to place it's figure
     * @return boolean to check figure can be moved
     */
    private boolean isElfCanBeMoved(int destinationColumn, int destinationRow) {

        return isElfMovingNorthOrSouth(destinationColumn, destinationRow) ||
                isElfMovingWestOrEast(destinationColumn, destinationRow)  ||
                isElfMovingDiagonalPartOne(destinationColumn, destinationRow) ||
                isElfMovingDiagonalPartTwo(destinationColumn, destinationRow);
    }

    /**
     * Method that checks if the Elf is doing 2 moves by going Diagonal
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow Row that the players want to place it's figure
     * @return boolean to check if the figure can go North or South
     */
    private boolean isElfMovingDiagonalPartOne(int destinationColumn, int destinationRow) {

        boolean didElfWentSideWayRow = Math.abs(destinationRow - this.getFigureRow()) <= 2;
        boolean didElfWentSideWayColumn = Math.abs(destinationColumn - this.getFigureColumn()) == 1;

        return didElfWentSideWayRow && didElfWentSideWayColumn;
    }

    /**
     * Method that checks if the Elf is doing 2 moves by going Diagonal
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow Row that the players want to place it's figure
     * @return boolean to check if the figure can go North or South
     */
    private boolean isElfMovingDiagonalPartTwo(int destinationColumn, int destinationRow) {

        boolean didElfWentSideWayRow = Math.abs(destinationRow - this.getFigureRow()) == 1;
        boolean didElfWentSideWayColumn = Math.abs(destinationColumn - this.getFigureColumn()) == 2;

        return didElfWentSideWayRow && didElfWentSideWayColumn;
    }

    /**
     * Method that checks if the Elf is moving South or North
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow Row that the players want to place it's figure
     * @return boolean to check if the figure can go North or South
     */
    private boolean isElfMovingNorthOrSouth(int destinationColumn, int destinationRow) {

        boolean didElfChangeRow = Math.abs(destinationRow - this.getFigureRow()) <= 3;
        boolean didElfChangeColumn = Math.abs(destinationColumn - this.getFigureColumn()) == 0;

        return didElfChangeColumn && didElfChangeRow;
    }

    /**
     * Method that checks if the Elf is moving West or East
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow Row that the players want to place it's figure
     * @return if the figure can go West or East
     */
    private boolean isElfMovingWestOrEast(int destinationColumn, int destinationRow) {

        boolean didElfChangeRow = Math.abs(destinationRow - this.getFigureRow()) == 0;
        boolean didElfChangeColumn = Math.abs(destinationColumn - this.getFigureColumn()) <= 3;

        return didElfChangeColumn && didElfChangeRow;
    }
}