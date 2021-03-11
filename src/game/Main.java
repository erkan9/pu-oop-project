package game;

import game.board.BoardFrame;

/**
 * @author Erkan Kamber
 */
public class Main {
    
    public BoardFrame boardFrame;

    public static void main(String[] args) {

        Main main = new Main();
        main.boardFrame = new BoardFrame();
        main.boardFrame.setVisible(true);
    }
}