package game.piece;

import game.board.Board;

public class Elf extends Piece {

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
    public Elf(int figureColumn, int figureRow, boolean isFigureRed, String figureFilePath, Board board,
                 int pieceAttack, int pieceShield, int pieceHealth) {

        super(figureColumn, figureRow, isFigureRed, figureFilePath, board, pieceAttack, pieceShield, pieceHealth);
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
        return isElfCanBeMoved(destinationColumn, destinationRow);
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

        if (isThereObstacle != null && checkIfElfCanAttack(destinationColumn, destinationRow)) {

            return true;
        }
        return canElfAttackFigure(destinationColumn, destinationRow, isTherePiece);
    }

    /**
     * Method that checks if the Attack is possible
     *
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow    Row that the players want to place it's figure
     * @param isTherePiece      The piece that will be attacked
     */
    private boolean canElfAttackFigure(int destinationColumn, int destinationRow, Piece isTherePiece) {

        if (checkIfElfCanAttack(destinationColumn, destinationRow)) {

           calculateDamageOfAttackedFigure(isTherePiece);

            return true;
        }
        return false;
    }

    /**
     * Method that checks if the Elf is attacking correctly
     * @param destinationColumn Column that the players want to place its figure
     * @param destinationRow Row that the players want to place its figure
     * @return If it is attacking correctly
     */
    private boolean checkIfElfCanAttack(int destinationColumn, int destinationRow) {

        return isElfMovingNorthOrSouth(destinationColumn, destinationRow) ||
                isElfMovingWestOrEast(destinationColumn, destinationRow);
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