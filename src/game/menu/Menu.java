package game.menu;

import game.board.Board;

public class Menu {

    private int imageColumnPosition;
    private int imageRowPosition;
    private String imageFilePath;
    public Board board;

    public Menu(int imageColumnPosition, int imageRowPosition, String imageFilePath, Board board) {

        this.imageColumnPosition = imageColumnPosition;
        this.imageRowPosition = imageRowPosition;
        this.imageFilePath = imageFilePath;
        this.board = board;
    }

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