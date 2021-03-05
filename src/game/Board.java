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

    private static final Image nullImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    private final String boardFilePath = "images" + File.separator + "board.png";

    private final byte SQUARE_WIDTH = 65;
    private final byte IMAGE_OFFSET = 10;

    private final int BOARD_ROWS = 7;
    private final int BOARD_COLUMNS = 9;
    public int turnCounter = 0;

    private Integer[][] BoardGrid;

    public ArrayList<Piece> redPieces;
    public ArrayList<Piece> blackPieces;
    public ArrayList<Obstacle> obstacles;

    public ArrayList<DrawingShape> staticShapes;
    public ArrayList<DrawingShape> pieceGraphics;

    public Piece activePieces;

    public Board() {

        setLists();

        setFiguresAndBoard();

        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(900, 455));
        this.setMinimumSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(1000, 1000));

        this.addMouseListener(mouseAdapter);

        this.setVisible(true);
        this.requestFocus();
        drawBoard();
    }

    private void setLists() {

        BoardGrid = new Integer[BOARD_ROWS][BOARD_COLUMNS];
        staticShapes = new ArrayList();
        pieceGraphics = new ArrayList();
        redPieces = new ArrayList();
        blackPieces = new ArrayList();
        obstacles = new ArrayList<>();
    }

    private void setBoard() {

        for (int column = 0; column < BOARD_ROWS; column++) {

            for (int row = 0; row < BOARD_COLUMNS; row++) {

                BoardGrid[column][row] = 0;
            }
        }

    }

    public void setFiguresAndBoard() {

        setBoard();

        redPieces.add(new Knight(3, 0, true, "Knight.png", this));
        redPieces.add(new Knight(4, 0, true, "Knight.png", this));
        redPieces.add(new Elf(7, 0, true, "Elf.png", this));
        redPieces.add(new Elf(6, 0, true, "Elf.png", this));
        redPieces.add(new Dwarf(1, 0, true, "Dwarf.png", this));
        redPieces.add(new Dwarf(0, 0, true, "Dwarf.png", this));

        blackPieces.add(new Knight(3, 6, false, "Knight.png", this));
        blackPieces.add(new Knight(4, 6, false, "Knight.png", this));
        blackPieces.add(new Elf(7, 6, false, "Elf.png", this));
        blackPieces.add(new Elf(6, 6, false, "Elf.png", this));
        blackPieces.add(new Dwarf(0, 6, false, "Dwarf.png", this));
        blackPieces.add(new Dwarf(1, 6, false, "Dwarf.png", this));

        setObstaclesOnBoard();

    }

    private void setObstaclesOnBoard() {

        Random random = new Random();

        int numOfObstacles = random.nextInt(5)+1;

        int randomColumn;
        int randomRow;

        for( int i = 0; i < numOfObstacles; i++) {

            randomColumn = random.nextInt(9);
            randomRow = random.nextInt(3) + 2;

            obstacles.add(new Obstacle(randomColumn, randomRow,"obstacle.png", this));
        }
    }

    private void drawBoard() {

        pieceGraphics.clear();
        staticShapes.clear();

        boardImageLoad();

        activePieceBackGround();

        visualiseRedPieces();

        visualiseBlackPieces();

        visualiseObstacles();

        this.repaint();
    }

    private void boardImageLoad() {

        Image board = loadImage(boardFilePath);
        staticShapes.add(new DrawingImage(board, new Rectangle2D.Double(0, 0, board.getWidth(null), board.getHeight(null))));
    }

    private void activePieceBackGround() {

        if (activePieces != null) {

            Image active_square = loadImage("images" + File.separator + "active_square.png");

            staticShapes.add(new DrawingImage(active_square, new Rectangle2D.Double(
                    SQUARE_WIDTH * activePieces.getFigureColumn(),
                    SQUARE_WIDTH * activePieces.getFigureRow(),
                    active_square.getWidth(null),
                    active_square.getHeight(null))));
        }
    }

    private void visualiseObstacles() {

        for (Obstacle obstacle : obstacles) {

            int pieceColumn = obstacle.getFigureColumn();
            int pieceRow = obstacle.getFigureRow();

            Image piece = loadImage("images" + File.separator + obstacle.getFigureFilePath());
            pieceGraphics.add(new DrawingImage(piece, new Rectangle2D.Double(SQUARE_WIDTH * pieceColumn + 3, SQUARE_WIDTH * pieceRow + 3,
                    piece.getWidth(null), piece.getHeight(null))));
        }
    }

    private void visualiseRedPieces() {

        for (Piece redPiece : redPieces) {

            int pieceColumn = redPiece.getFigureColumn();
            int pieceRow = redPiece.getFigureRow();

            Image piece = loadImage("images" + File.separator + "red_pieces" + File.separator + redPiece.getFilePath());
            pieceGraphics.add(new DrawingImage(piece, new Rectangle2D.Double(SQUARE_WIDTH * pieceColumn + IMAGE_OFFSET, SQUARE_WIDTH * pieceRow + IMAGE_OFFSET,
                    piece.getWidth(null), piece.getHeight(null))));
        }
    }

    private void visualiseBlackPieces() {

        for (Piece blackPiece : blackPieces) {

            int pieceColumn = blackPiece.getFigureColumn();
            int pieceRow = blackPiece.getFigureRow();

            Image piece = loadImage("images" + File.separator + "black_pieces" + File.separator + blackPiece.getFilePath());
            pieceGraphics.add(new DrawingImage(piece, new Rectangle2D.Double(SQUARE_WIDTH * pieceColumn + IMAGE_OFFSET, SQUARE_WIDTH * pieceRow + IMAGE_OFFSET,
                    piece.getWidth(null), piece.getHeight(null))));
        }
    }

    public Piece getPiece(int x, int y) {

        for (Piece p : redPieces) {

            if (p.getFigureColumn() == x && p.getFigureRow() == y) {

                return p;
            }
        }

        for (Piece p : blackPieces) {

            if (p.getFigureColumn() == x && p.getFigureRow() == y) {

                return p;
            }
        }
        return null;
    }

    private Image loadImage(String imageFile) {

        try {
            return ImageIO.read(new File(imageFile));
        } catch (IOException e) {
            return nullImage;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);
        drawShapes(g2);
    }

    private void drawBackground(Graphics2D g2) {
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawShapes(Graphics2D g2) {

        for (DrawingShape shape : staticShapes) {
            shape.draw(g2);
        }
        for (DrawingShape shape : pieceGraphics) {
            shape.draw(g2);
        }
    }

    private boolean checkPlayerTurn() {

        return turnCounter % 2 == 1;
    }

    private void figureLogic(int clickedRow, int clickedColumn, boolean isRedTurn, Piece clickedPiece) {

        if (activePieces == null && clickedPiece != null &&
                ((isRedTurn && clickedPiece.isRed()) || (!isRedTurn && clickedPiece.isBlack()))) {

            activePieces = clickedPiece;

        } else if (activePieces != null && activePieces.getFigureColumn() == clickedColumn && activePieces.getFigureRow() == clickedRow) {

            activePieces = null;

        } else if (activePieces != null && activePieces.canMove(clickedColumn, clickedRow)
                && ((isRedTurn && activePieces.isRed()) || (!isRedTurn && activePieces.isBlack()))) {

            if (clickedPiece != null) {

                if (clickedPiece.isRed()) {

                    redPieces.remove(clickedPiece);

                } else {

                    blackPieces.remove(clickedPiece);
                }
            }

            // Do the figure move
            activePieces.setFigureColumn(clickedColumn);
            activePieces.setFigureRow(clickedRow);

            // if piece is a pawn set hasMoved to true
            if (activePieces.getClass().equals(Dwarf.class)) {

                Dwarf castedDwarf = (Dwarf) (activePieces);
                castedDwarf.setHasMoved(true);
            }
            activePieces = null;
            turnCounter++;
        }
    }

    interface DrawingShape {

        void draw(Graphics2D g2);
    }

    static class DrawingImage implements DrawingShape {

        public Image image;
        public Rectangle2D rect;

        public DrawingImage(Image image, Rectangle2D rect) {

            this.image = image;
            this.rect = rect;
        }

        @Override
        public void draw(Graphics2D g2) {

            Rectangle2D bounds = rect.getBounds2D();

            g2.drawImage(image, (int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getMaxX(), (int) bounds.getMaxY(),
                    0, 0, image.getWidth(null), image.getHeight(null), null);
        }
    }

    MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {

            int mouseXCoordinate = e.getX();
            int mouseYCoordinate = e.getY();

            if (mouseXCoordinate <= 585) {

                int clickedRow = mouseYCoordinate / SQUARE_WIDTH;
                int clickedColumn = mouseXCoordinate / SQUARE_WIDTH;

                boolean isRedTurn = checkPlayerTurn();

                Piece clickedPiece = getPiece(clickedColumn, clickedRow);

                figureLogic(clickedRow, clickedColumn, isRedTurn, clickedPiece);
            }

            drawBoard();
        }
    };
}