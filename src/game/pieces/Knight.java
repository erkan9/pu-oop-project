package game.pieces;

import game.board.Board;

public class Knight extends Piece {

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
    public Knight(int figureColumn, int figureRow, boolean isFigureRed, String figureFilePath, Board board,
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
     * Method that checks what type of Piece the Player is attacking
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow    Row that the players want to place it's figure
     * @param isTherePiece      The piece that will be attacked
     * @param isThereObstacle   The obstacle that will be attacked
     * @return If the player is attacking obstacle or a piece
     */
    private boolean checkAttackedPieceType(int destinationColumn, int destinationRow, Piece isTherePiece, Obstacle isThereObstacle) {

        this.sumOfDices = 0;

        if (isThereObstacle != null && isKnightCanBeMoved(destinationColumn, destinationRow)) {

            return true;
        }
        return canKnightAttackFigure(destinationColumn, destinationRow, isTherePiece);
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
        return isKnightCanBeMoved(destinationColumn, destinationRow);
    }

    /**
     * Method that checks if the Attack is possible
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow    Row that the players want to place it's figure
     * @param isTherePiece      The piece that will be attacked
     * @return return if Attack is possible
     */
    private boolean canKnightAttackFigure(int destinationColumn, int destinationRow, Piece isTherePiece) {

        if (isKnightCanBeMoved(destinationColumn, destinationRow)) {

            calculateDamageOfAttackedFigure(isTherePiece);

            return true;
        }
        return false;
    }

    /**
     * Method that checks if the Knight can be moved
     *
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow    Row that the players want to place it's figure
     * @return boolean to check figure can be moved
     */
    private boolean isKnightCanBeMoved(int destinationColumn, int destinationRow) {

        return isKnightMovingNorthOrSouth(destinationColumn, destinationRow) || isKnightMovingWestOrEast(destinationColumn, destinationRow);
    }

    /**
     * Method that checks if the Knight is moving South or North
     *
     * @param destinationColumn Column that the players want to place its figure
     * @param destinationRow    Row that the players want to place its figure
     * @return boolean to check if the figure can go North or South
     */
    private boolean isKnightMovingNorthOrSouth(int destinationColumn, int destinationRow) {

        boolean didKnightChangeRow = Math.abs(destinationRow - this.getFigureRow()) == 1;
        boolean didKnightChangeColumn = Math.abs(destinationColumn - this.getFigureColumn()) == 0;

        return didKnightChangeColumn && didKnightChangeRow;
    }

    /**
     * Method that checks if the Knight is moving West or East
     *
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow    Row that the players want to place it's figure
     * @return boolean to check if the figure can go West or East
     */
    private boolean isKnightMovingWestOrEast(int destinationColumn, int destinationRow) {

        boolean didKnightChangeRow = Math.abs(destinationRow - this.getFigureRow()) == 0;
        boolean didKnightChangeColumn = Math.abs(destinationColumn - this.getFigureColumn()) == 1;

        return didKnightChangeColumn && didKnightChangeRow;
    }
}