package game.menu;

import game.board.Board;

public class ChooseMovementMenu extends Menu{

    /**
     * Constructor of the Menus
     * @param imageColumnPosition The Column position of the images
     * @param imageRowPosition The Row position of the images
     * @param imageFilePath The File Path of the images
     * @param board The Board
     */
    public ChooseMovementMenu(int imageColumnPosition, int imageRowPosition, String imageFilePath, Board board) {

        super(imageColumnPosition, imageRowPosition, imageFilePath, board);
    }
}
