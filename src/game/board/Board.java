package game.board;

import game.menu.ChoosePieceMenu;
import game.menu.ChooseMovementMenu;
import game.pieces.*;
import game.player.BlackPlayer;
import game.player.Player;
import game.player.RedPlayer;

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
    private final Font playerTurnFont = new Font("Tacoma", Font.PLAIN, 15);
    private final String boardFilePath = "images" + File.separator + "board.png";

    private final byte SQUARE_WIDTH = 65;
    private final byte IMAGE_OFFSET = 10;

    int numberOfPlacedFigures = 0;
    private final int BOARD_ROWS = 7;
    private final int BOARD_COLUMNS = 9;
    public int turnCounter = 0;

    public ArrayList<Piece> redPieces;
    public ArrayList<Piece> blackPieces;
    public ArrayList<Obstacle> obstacles;
    public ArrayList<ChoosePieceMenu> choosePieceMenu;
    public ArrayList<ChooseMovementMenu> chooseMovement;

    public ArrayList<DrawingShape> staticShapes;
    public ArrayList<DrawingShape> pieceGraphics;

    public Piece activePieces;

    public Player redPlayer;
    public Player blackPlayer;

    int mouseXCoordinate;
    int mouseYCoordinate;

    int clickedColumn;
    int clickedRow;

    boolean isFigureChosen = false;
    boolean chosenDwarf = false;
    boolean chosenElf = false;
    boolean chosenKnight = false;

    public boolean isMovementChosen = false;
    public boolean chosenAttack = false;
    public boolean chosenMove = false;
    public boolean chosenHeal = false;

    private Integer[][] BoardGrid;

    public Board() {

        blackPlayer = new BlackPlayer(0, 0, 0);
        redPlayer = new RedPlayer(0, 0, 0);

        setLists();

        setBoard();

        setObstaclesOnBoard();
        setChooseFigureMenu();
        setChooseFigureMovementMenu();

        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(900, 455));
        this.setMinimumSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(1000, 1000));

        this.addMouseListener(mouseAdapter);

        this.setVisible(true);
        this.requestFocus();

        drawBoard();
    }

    private void setChooseFigureMenu() {

            choosePieceMenu.add(new ChoosePieceMenu(11, 3, "dwarf_menu.png", this));
            choosePieceMenu.add(new ChoosePieceMenu(11, 2, "knight_menu.png", this));
            choosePieceMenu.add(new ChoosePieceMenu(11, 4, "elf_menu.png", this));
    }

    private void setChooseFigureMovementMenu() {

            chooseMovement.add(new ChooseMovementMenu(11, 2, "heal_menu.png", this));
            chooseMovement.add(new ChooseMovementMenu(11, 3, "move_menu.png", this));
            chooseMovement.add(new ChooseMovementMenu(11, 4, "attack_menu.png", this));
    }

    private void setLists() {

        BoardGrid = new Integer[BOARD_ROWS][BOARD_COLUMNS];
        staticShapes = new ArrayList();
        pieceGraphics = new ArrayList();
        redPieces = new ArrayList();
        blackPieces = new ArrayList();
        obstacles = new ArrayList<>();
        choosePieceMenu = new ArrayList<>();
        chooseMovement = new ArrayList<>();
    }

    private void setBoard() {

        for (int column = 0; column < BOARD_ROWS; column++) {

            for (int row = 0; row < BOARD_COLUMNS; row++) {

                BoardGrid[column][row] = 0;
            }
        }
    }

    private void setObstaclesOnBoard() {

        Random random = new Random();

        int numOfObstacles = random.nextInt(5) + 1;

        int randomColumn;
        int randomRow;

        for (int i = 0; i < numOfObstacles; i++) {

            randomColumn = random.nextInt(9);
            randomRow = random.nextInt(3) + 2;

            obstacles.add(new Obstacle(randomColumn, randomRow, "obstacle.png", this));
        }
    }

    private void drawBoard() {

        pieceGraphics.clear();
        staticShapes.clear();

        boardImageLoad();

        visualiseFigureMenu();

        visualiseFigureMovementMenu();

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

            Image activeSquare = loadImage("images" + File.separator + "active_square.png");

            staticShapes.add(new DrawingImage(activeSquare, new Rectangle2D.Double(
                    SQUARE_WIDTH * activePieces.getFigureColumn(),
                    SQUARE_WIDTH * activePieces.getFigureRow(),
                    activeSquare.getWidth(null),
                    activeSquare.getHeight(null))));
        }
    }

    private void visualiseFigureMovementMenu() {

        if (numberOfPlacedFigures >= 4) {

            for (ChooseMovementMenu chooseMovementMenu : chooseMovement) {

                int pieceColumn = chooseMovementMenu.getImageColumnPosition();
                int pieceRow = chooseMovementMenu.getImageRowPosition();

                Image piece = loadImage("images" + File.separator + "movement_menu" + File.separator + chooseMovementMenu.getImageFilePath());
                pieceGraphics.add(new DrawingImage(piece, new Rectangle2D.Double(SQUARE_WIDTH * pieceColumn + IMAGE_OFFSET, SQUARE_WIDTH * pieceRow + IMAGE_OFFSET,
                        piece.getWidth(null), piece.getHeight(null))));
            }
        }
    }

    private void visualiseFigureMenu() {

        if (numberOfPlacedFigures < 4) {

            for (ChoosePieceMenu choosePieceMenu : this.choosePieceMenu) {

                int pieceColumn = choosePieceMenu.getImageColumnPosition();
                int pieceRow = choosePieceMenu.getImageRowPosition();

                Image piece = loadImage("images" + File.separator + "figure_menu" + File.separator + choosePieceMenu.getImageFilePath());
                pieceGraphics.add(new DrawingImage(piece, new Rectangle2D.Double(SQUARE_WIDTH * pieceColumn + IMAGE_OFFSET, SQUARE_WIDTH * pieceRow + IMAGE_OFFSET,
                        piece.getWidth(null), piece.getHeight(null))));
            }
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

        drawPlayerTurn(g);
    }

    private void drawBlackPlayerAvailablePlacements(Graphics g) {

        for (int i = 0; i < 9; i++) {

            for (int c = 0; c < 5; c++) {

                g.setColor(Color.BLACK);
                g.fillRect(i * 65, c * 65, 65, 65);
            }
        }
    }

    private void drawRedPlayerAvailablePlacements(Graphics g) {

        for (int i = 0; i < 9; i++) {

            for (int c = 7; c >= 2; c--) {

                g.setColor(Color.RED);
                g.fillRect(i * 65, c * 65, 65, 65);
            }
        }
    }

    private void drawPlayerTurn(Graphics g) {

        if (checkPlayerTurn()) {

            g.setFont(playerTurnFont);
            g.setColor(Color.RED);
            g.drawString("Red player's turn", 700, 100);

            if(numberOfPlacedFigures < 4) {
                g.drawString("Place figure", 715, 120);
                drawRedPlayerAvailablePlacements(g);
            }

        } else {

            g.setFont(playerTurnFont);
            g.setColor(Color.BLACK);
            g.drawString("Black player's turn", 700, 400);

            if (numberOfPlacedFigures < 4) {
                g.drawString("Place a figure", 715, 420);
                drawBlackPlayerAvailablePlacements(g);
            }
        }
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

    private void setBlackDwarf() {

        blackPieces.add(new Dwarf(clickedColumn, clickedRow, false, "Dwarf.png", this));

        visualiseBlackPieces();

        System.out.printf("Placed 'Black' Dwarf [%d][%d]  || ", clickedColumn, clickedRow);
    }

    private void setBlackKnight() {

        blackPieces.add(new Knight(clickedColumn, clickedRow, false, "Knight.png", this));

        visualiseBlackPieces();

        System.out.printf("Placed 'Black' Knight [%d][%d] || ", clickedColumn, clickedRow);
    }

    private void setBlackElf() {

        blackPieces.add(new Elf(clickedColumn, clickedRow, false, "Elf.png", this));

        visualiseBlackPieces();

        System.out.printf("Placed 'Black' Elf [%d][%d]    || ", clickedColumn, clickedRow);
    }

    private void setRedDwarf() {

        redPieces.add(new Dwarf(clickedColumn, clickedRow, true, "Dwarf.png", this));

        visualiseRedPieces();

        System.out.printf("Placed 'Red' Dwarf [%d][%d]\n", clickedColumn, clickedRow);
    }

    private void setRedKnight() {

        redPieces.add(new Knight(clickedColumn, clickedRow, true, "Knight.png", this));

        visualiseRedPieces();

        System.out.printf("Placed 'Red' Knight [%d][%d]\n", clickedColumn, clickedRow);
    }

    private void setRedElf() {

        redPieces.add(new Elf(clickedColumn, clickedRow, true, "Elf.png", this));

        visualiseRedPieces();

        System.out.printf("Placed 'Red' Elf [%d][%d]\n", clickedColumn, clickedRow);
    }

    MouseAdapter mouseAdapter = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {

            mouseXCoordinate = e.getX();
            mouseYCoordinate = e.getY();

            clickedColumn = mouseXCoordinate / SQUARE_WIDTH;
            clickedRow = mouseYCoordinate / SQUARE_WIDTH;

            boolean isRedTurn = checkPlayerTurn();

            if(numberOfPlacedFigures >= 4) {

                Piece clickedPiece = getPiece(clickedColumn, clickedRow);

                figureLogic(clickedRow, clickedColumn, isRedTurn, clickedPiece);

                if(isMovementChosen) {

                    isMovementChosen = false;
                }
                else {
                    checkIfFigureMovementChosen();
                }
            }
            else {
                logicForPlacingThePiecesOnBoard(isRedTurn);
            }

            drawBoard();
        }
    };

    private void checkIfFigureMovementChosen() {

        if (clickedColumn == 11 && clickedRow == 3) {

            chosenMove = true;
            chosenHeal = false;
            chosenAttack = false;

            isMovementChosen = true;

        } else if (clickedColumn == 11 && clickedRow == 2) {

            chosenMove = false;
            chosenHeal = true;
            chosenAttack = false;

            isMovementChosen = true;

        } else if (clickedColumn == 11 && clickedRow == 4) {

            chosenMove = false;
            chosenHeal = false;
            chosenAttack = true;

            isMovementChosen = true;
        }
    }

    private void logicForPlacingThePiecesOnBoard(boolean isRedTurn) {

        if(numberOfPlacedFigures < 4) {

            figurePlacementLogic(isRedTurn);

            checkIfFigureIsChosen();
        }
    }

    private void figurePlacementLogic(boolean isRedTurn) {

        if (!isRedTurn) {

            checkBlackPlayerFigurePlacement();

        } else {

            checkRedPlayerFigurePlacement();
        }
    }

    private void checkIfFigureIsChosen() {

        if (clickedColumn == 11 && clickedRow == 3) {

            chosenDwarf = true;
            chosenKnight = false;
            chosenElf = false;

            isFigureChosen = true;

        } else if (clickedColumn == 11 && clickedRow == 2) {

            chosenKnight = true;
            chosenDwarf = false;
            chosenElf = false;

            isFigureChosen = true;

        } else if (clickedColumn == 11 && clickedRow == 4) {

            chosenElf = true;
            chosenKnight = false;
            chosenDwarf = false;

            isFigureChosen = true;
        }
    }

    private void setChosenFigureBooleans() {

        chosenDwarf = false;
        chosenKnight = false;
        chosenElf = false;
    }

    private void checkBlackPlayerFigurePlacement() {

        if (isFigureChosen) {

            if (clickedRow >= 5 && clickedColumn <= 8) {

                if (!checkIfThereIsBlackPiece(clickedColumn, clickedRow)) {

                    if (chosenDwarf) {

                        if (blackPlayer.getDwarfCounter() < 2) {
                            setBlackDwarf();
                            blackPlayer.setDwarfCounter(blackPlayer.getDwarfCounter() + 1);
                            whenFigureChosenSetValues();
                        }
                    } else if (chosenKnight) {

                        if (blackPlayer.getKnightCounter() < 2) {
                            setBlackKnight();
                            blackPlayer.setKnightCounter(blackPlayer.getKnightCounter() + 1);
                            whenFigureChosenSetValues();
                        }
                    } else if (chosenElf) {

                        if (blackPlayer.getElfCounter() < 2) {
                            setBlackElf();
                            blackPlayer.setElfCounter(blackPlayer.getElfCounter() + 1);
                            whenFigureChosenSetValues();
                        }
                    }
                }
            }
        }
    }

    private boolean checkIfThereIsBlackPiece(int clickedColumn, int clickedRow) {

        for (Piece p : blackPieces) {

            if (p.getFigureColumn() == clickedColumn && p.getFigureRow() == clickedRow) {

                System.out.println("There is a Figure");
                return true;
            }
        }
        return false;
    }

    private boolean checkIfThereIsRedPiece(int clickedColumn, int clickedRow) {

        for (Piece p : redPieces) {

            if (p.getFigureColumn() == clickedColumn && p.getFigureRow() == clickedRow) {

                System.out.println("There is a Figure");
                return true;
            }
        }
        return false;
    }

    private void whenFigureChosenSetValues() {

        setChosenFigureBooleans();
        turnCounter++;
        isFigureChosen = false;
        numberOfPlacedFigures++;
    }

    private void checkRedPlayerFigurePlacement() {

        if (isFigureChosen) {

            if (clickedRow <= 1 && clickedColumn <= 8) {

                if (!checkIfThereIsRedPiece(clickedColumn, clickedRow)) {

                    if (chosenDwarf) {
                        if (redPlayer.getDwarfCounter() < 2) {

                            setRedDwarf();
                            redPlayer.setDwarfCounter(redPlayer.getDwarfCounter() + 1);
                            whenFigureChosenSetValues();
                        }
                    } else if (chosenKnight) {
                        if (redPlayer.getKnightCounter() < 2) {

                            setRedKnight();
                            redPlayer.setKnightCounter(redPlayer.getKnightCounter() + 1);
                            whenFigureChosenSetValues();
                        }
                    } else if (chosenElf) {
                        if (redPlayer.getElfCounter() < 2) {

                            setRedElf();
                            redPlayer.setElfCounter(redPlayer.getElfCounter() + 1);
                            whenFigureChosenSetValues();
                        }
                    }
                }
            }
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
}