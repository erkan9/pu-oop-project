package game.pieces;

import game.board.Board;

import java.util.Random;

public class Piece {

    protected Board board;
    protected Random random = new Random();

    protected int randomNumber;
    protected int sumOfDices;

    protected int calculateDamage;
    protected int calculateHealthToAdd;
    protected int calculatePointsToAdd;

    protected int pieceAttack;
    protected int pieceShield;
    protected int pieceHealth;

    protected int figureColumn;
    protected int figureRow;

    protected String figureFilePath;

    protected boolean isFigureRed;

    /**
     * Constructor
     *
     * @param figureColumn   The Column of the chosen figure
     * @param figureRow      The Row of the chosen figure
     * @param isFigureRed    If the figure is Red or Black
     * @param figureFilePath The image path of the figure
     * @param board          The board that it is placed
     * @param pieceAttack    The attack power of the piece
     * @param pieceShield    The shield power of the piece
     * @param pieceHealth    The health of the piece
     */
    public Piece(int figureColumn, int figureRow, boolean isFigureRed, String figureFilePath, Board board,
                 int pieceAttack, int pieceShield, int pieceHealth) {

        this.isFigureRed = isFigureRed;
        this.figureColumn = figureColumn;
        this.figureRow = figureRow;
        this.figureFilePath = figureFilePath;
        this.board = board;
        this.pieceAttack = pieceAttack;
        this.pieceShield = pieceShield;
        this.pieceHealth = pieceHealth;
    }

    // Getters and Setters

    public int getPieceAttack() {
        return pieceAttack;
    }

    public int getPieceShield() {
        return pieceShield;
    }

    public int getPieceHealth() {
        return pieceHealth;
    }

    public void setPieceHealth(int pieceHealth) {
        this.pieceHealth = pieceHealth;
    }

    public String getFilePath() {
        return figureFilePath;
    }

    public boolean isRed() {
        return isFigureRed;
    }

    public boolean isBlack() {
        return !isFigureRed;
    }

    public int getFigureColumn() {
        return figureColumn;
    }

    public void setFigureColumn(int figureColumn) {
        this.figureColumn = figureColumn;
    }

    public int getFigureRow() {
        return figureRow;
    }

    public void setFigureRow(int figureRow) {
        this.figureRow = figureRow;
    }

    public boolean canMove(int destinationColumn, int destinationRow) {
        return false;
    }

    /**
     * Method that rolls 3 dices to check if the player attacked a figure
     */
    protected void rollDicesToAttacked() {

        for (int i = 0; i < 3; i++) {

            this.randomNumber = random.nextInt(6);

            this.sumOfDices += this.randomNumber;
        }
        System.out.printf("\nThe Sum of the rolled 3 dices is: [%d]\n", this.sumOfDices);
    }

    /**
     * Method that checks if the figure can be healed and heals it
     *
     * @param destinationColumn Column that the players want to place it's figure
     * @param destinationRow    Row that the players want to place it's figure
     * @return The result of the check if the figure can be healed
     */
    protected boolean healFigure(int destinationColumn, int destinationRow) {

        this.randomNumber = this.random.nextInt(6) + 1;

        if (this.getFigureColumn() == destinationColumn && this.getFigureRow() == destinationRow) {

            System.out.printf("\nHealth of the Figure before Heal is: [%d]\n", this.getPieceHealth());

            this.calculateHealthToAdd = this.getPieceHealth() + this.randomNumber;

            this.setPieceHealth(this.calculateHealthToAdd);

            System.out.printf("Health of the Figure after Heal is: [%d]\n", this.getPieceHealth());

            return true;
        }
        return false;
    }

    /**
     * Method that checks if the attack will be done depending on the sum of rolled dices
     *
     * @param isTherePiece The piece that will be attacked
     */
    protected void checkIfAttacked(Piece isTherePiece) {

        System.out.printf("\nChosen Figures Health is: [%d]\n", isTherePiece.getPieceHealth());

        if (this.sumOfDices == isTherePiece.getPieceHealth()) {

            System.out.println("Figure is not Attacked");

        } else {

            System.out.println("Figure is Attacked");

            isTherePiece.setPieceHealth(this.calculateDamage);

            System.out.printf("Chosen Figures Health now is: [%d]\n", isTherePiece.getPieceHealth());
        }
    }

    /**
     * Method that was created to check if can kill own figures, then changed so cant kill any figures
     *
     * @param isTherePiece Object that contains information about the place where i want to but my figure
     * @return boolean to check if I can put it there
     */
    protected boolean isThereYourFigure(Piece isTherePiece) {

        if (isTherePiece.isRed() && this.isRed()) {

            return false;
        }

        isTherePiece.isBlack();

        return false;
    }

    /**
     * Method that calculates the damage done and health of the attacked figure
     * @param isTherePiece The figure that is being attacked
     */
    protected void calculateDamageOfAttackedFigure(Piece isTherePiece) {

        this.calculateDamage = isTherePiece.getPieceHealth() - (this.getPieceAttack() - isTherePiece.getPieceShield());

        this.calculatePointsToAdd = (this.getPieceAttack() - isTherePiece.getPieceShield());

        rollDicesToAttacked();

        checkIfAttacked(isTherePiece);

        addDamageAsPoints();
    }

    /**
     * Method that adds the done damage as points to the player that attacked
     */
    public void addDamageAsPoints() {

        boolean isRedsTurn = board.checkPlayerTurn();

        int pointsToAdd;

        if (isRedsTurn) {

            pointsToAdd = board.redPlayer.getPoints() + calculatePointsToAdd;

            board.redPlayer.setPoints(pointsToAdd);

            System.out.printf("\n[%d] Points added to Red Player\n", calculatePointsToAdd);

        } else {

            pointsToAdd = board.blackPlayer.getPoints() + calculatePointsToAdd;

            board.blackPlayer.setPoints(pointsToAdd);

            System.out.printf("\n[%d] - Points are added to Black Player\n", calculatePointsToAdd);
        }
    }
}