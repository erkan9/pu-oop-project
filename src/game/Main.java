package game;

import game.board.BoardFrame;

public class Main {
    
    public BoardFrame boardFrame;

    public static void main(String[] args) {

        Main main = new Main();
        main.boardFrame = new BoardFrame();
        main.boardFrame.setVisible(true);
    }
}