package game.board;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JFrame;

public class BoardFrame extends JFrame {

    Component component;

    public BoardFrame() {

        component = new Board();
        this.add(component, BorderLayout.CENTER);

        this.setTitle("Knights/Elves/Dwarfs");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocation(300, 80);
    }
}