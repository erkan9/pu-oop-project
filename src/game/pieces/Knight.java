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
                return isKnightCanBeMoved(destinationColumn, destinationRow);
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
     * Method that checks if the Knight can be moved
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow Row that the players want to place it's figure
     * @return boolean to check figure can be moved
     */
    private boolean isKnightCanBeMoved(int destinationColumn, int destinationRow) {

        return isKnightMovingNorthOrSouth(destinationColumn, destinationRow) || isKnightMovingWestOrEast(destinationColumn, destinationRow);
    }

    /**
     * Method that checks if the Knight is moving South or North
     * @param destinationColumn Column that the players want to place its figure
     * @param destinationRow Row that the players want to place its figure
     * @return boolean to check if the figure can go North or South
     */
    private boolean isKnightMovingNorthOrSouth(int destinationColumn, int destinationRow) {

        boolean didKnightChangeRow = Math.abs(destinationRow - this.getFigureRow()) == 1;
        boolean didKnightChangeColumn =   Math.abs(destinationColumn - this.getFigureColumn()) == 0;

        return didKnightChangeColumn && didKnightChangeRow;
    }

    /**
     * Method that checks if the Knight is moving West or East
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow Row that the players want to place it's figure
     * @return boolean to check if the figure can go West or East
     */
    private boolean isKnightMovingWestOrEast(int destinationColumn, int destinationRow) {

        boolean didKnightChangeRow = Math.abs(destinationRow - this.getFigureRow()) == 0;
        boolean didKnightChangeColumn =   Math.abs(destinationColumn - this.getFigureColumn()) == 1;

        return didKnightChangeColumn && didKnightChangeRow;
    }
}