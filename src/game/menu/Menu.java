package game.menu;

import game.board.Board;

public class Menu {

    protected int imageColumnPosition;
    protected int imageRowPosition;
    protected String imageFilePath;
    protected Board board;

    /**
     * Constructor of the Menus
     * @param imageColumnPosition The Column position of the images
     * @param imageRowPosition The Row position of the images
     * @param imageFilePath The File Path of the images
     * @param board The Board
     */
    public Menu(int imageColumnPosition, int imageRowPosition, String imageFilePath, Board board) {

        this.imageColumnPosition = imageColumnPosition;
        this.imageRowPosition = imageRowPosition;
        this.imageFilePath = imageFilePath;
        this.board = board;
    }

    // Getters and Setter

    public int getImageColumnPosition() {
        return imageColumnPosition;
    }

    public int getImageRowPosition() {
        return imageRowPosition;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }
}