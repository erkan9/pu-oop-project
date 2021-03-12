package game.piece;

import game.board.Board;

public class Dwarf extends Piece {

    private boolean hasFigureMoved;

    /**
     * Constructor
     * @param figureColumn The Column of the chosen figure
     * @param figureRow  The Row of the chosen figure
     * @param isFigureRed If the figure is Red or Black
     * @param figureFilePath The image path of the figure
     * @param board The board that it is placed
     * @param pieceAttack The attack power of the piece
     * @param pieceShield The shield power of the piece
     * @param pieceHealth The health of the piece
     */
    public Dwarf(int figureColumn, int figureRow, boolean isFigureRed, String figureFilePath, Board board,
                  int pieceAttack, int pieceShield, int pieceHealth) {

        super(figureColumn, figureRow, isFigureRed, figureFilePath, board, pieceAttack, pieceShield, pieceHealth);
        this.hasFigureMoved = false;
    }

    public void setHasMoved(boolean hasMoved) {

        this.hasFigureMoved = hasMoved;
    }

    /**
     * Method that checks if player can move, heal or attack
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow    Row that the players want to place it's figure
     * @return If it is possible to move, heal or attack
     */
    @Override
    public boolean canMove(int destinationColumn, int destinationRow) {

        Piece isTherePiece = this.board.getPiece(destinationColumn, destinationRow);
        Obstacle isThereObstacle = this.board.getObstacle(destinationColumn, destinationRow);

        if (this.board.isMovementChosen) {

            if (this.board.chosenMove) {

                return checkIfMovementIsAvailable(destinationColumn, destinationRow, isTherePiece);

            } else if (this.board.chosenHeal) {

                return healFigure(destinationColumn, destinationRow);

            } else if (this.board.chosenAttack) {

               return checkAttackedPieceType(destinationColumn, destinationRow, isTherePiece, isThereObstacle);
            }
        }
        return false;
    }

    /**
     * Method that checks if the players wants to place on it's figure
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow    Row that the players want to place it's figure
     * @param isTherePiece      The piece that will be attacked
     * @return If the knight can be moved
     */
    private boolean checkIfMovementIsAvailable(int destinationColumn, int destinationRow, Piece isTherePiece) {

        if (isTherePiece != null) {

            if (!isThereYourFigure(isTherePiece)) {

                System.out.println("You can't do that");

                return false;
            }
        }
        return canDwarfBeMoved(destinationColumn, destinationRow);
    }

    /**
     * Method that checks what type of Piece the Player is attacking
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow    Row that the players want to place it's figure
     * @param isTherePiece      The piece that will be attacked
     * @param isThereObstacle   The obstacle that will be attacked
     * @return If the player is attacking obstacle or a piece
     */
    private boolean checkAttackedPieceType(int destinationColumn, int destinationRow, Piece isTherePiece, Obstacle isThereObstacle) {

        this.sumOfDices = 0;

        if (isThereObstacle != null && checkIfDwarfCanAttack(destinationColumn, destinationRow)) {

            return true;
        }
        return canDwarfAttackFigure(destinationColumn, destinationRow, isTherePiece);
    }

    /**
     * Method that checks if the Attack is possible
     *
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow    Row that the players want to place it's figure
     * @param isTherePiece      The piece that will be attacked
     */
    private boolean canDwarfAttackFigure(int destinationColumn, int destinationRow, Piece isTherePiece) {

        if (checkIfDwarfCanAttack(destinationColumn, destinationRow)) {

            calculateDamageOfAttackedFigure(isTherePiece);

            return true;
        }

        return false;
    }

    private boolean checkIfDwarfCanAttack(int destinationColumn, int destinationRow) {

        return isDwarfMovingNorthOrSouth(destinationColumn, destinationRow) ||
                isDwarfMovingWestOrEast(destinationColumn, destinationRow);
    }

    /**
     * Method that checks if the Dwarf can be moved
     * @param destinationColumn Column that the players want to place its figure
     * @param destinationRow Row that the players want to place its figure
     * @return boolean to check figure can be moved
     */
    private boolean canDwarfBeMoved(int destinationColumn, int destinationRow) {

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