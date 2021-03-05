package game;

import game.pieces.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JComponent {

    public Board() {

        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(500, 500));
        this.setMinimumSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(1000, 1000));
        this.setVisible(true);
        this.requestFocus();
    }
}