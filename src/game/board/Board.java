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


//TODO Check if figure skipped OBSTACLE on MOVE
//TODO Refactor movement logic [too big]

//TODO Put killed pieces in somewhere for end
@SuppressWarnings("serial")
public class Board extends JComponent {

    private static final Image nullImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    private final Font playerTurnFont = new Font("Tacoma", Font.PLAIN, 15);
    private final String boardFilePath = "images" + File.separator + "board.png";
    private final byte SQUARE_WIDTH = 65;
    private final byte IMAGE_OFFSET = 10;
    private final int BOARD_ROWS = 7;
    private final int BOARD_COLUMNS = 9;
    public int turnCounter = 0;
    public int roundCounter = 0;
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
    public boolean isMovementChosen = false;
    public boolean chosenAttack = false;
    public boolean chosenMove = false;
    public boolean chosenHeal = false;
    Random random = new Random();
    boolean isFigureChosen = false;
    boolean chosenDwarf = false;
    boolean chosenElf = false;
    boolean chosenKnight = false;
    private int numberOfPlacedFigures = 0;
    private int clickedColumn;
    private int clickedRow;

    MouseAdapter mouseAdapter = new MouseAdapter() {

        /**
         * Method that checks where the mouse is Clicked
         * @param e Object of MouseEvent
         */
        @Override
        public void mousePressed(MouseEvent e) {

            int mouseXCoordinate = e.getX();
            int mouseYCoordinate = e.getY();

            clickedColumn = mouseXCoordinate / SQUARE_WIDTH;
            clickedRow = mouseYCoordinate / SQUARE_WIDTH;

            boolean isRedTurn = checkPlayerTurn();

            if (numberOfPlacedFigures >= 4) {

                Piece clickedPiece = getPiece(clickedColumn, clickedRow);
                Obstacle clickedObstacle = getObstacle(clickedColumn, clickedRow);

                figureLogic(clickedRow, clickedColumn, isRedTurn, clickedPiece, clickedObstacle);

                if (isMovementChosen) {

                    isMovementChosen = false;
                } else {
                    checkIfFigureActionChosen();
                }
            } else {
                logicForPlacingThePiecesOnBoard(isRedTurn);
            }

            drawBoard();
        }
    };
    private Integer[][] BoardGrid;

    public Board() {

        blackPlayer = new BlackPlayer(0, 0, 0, 0);
        redPlayer = new RedPlayer(0, 0, 0, 0);

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

    /**
     * Method that sets the Menu for Choosing Figures
     */
    private void setChooseFigureMenu() {

        choosePieceMenu.add(new ChoosePieceMenu(11, 3, "dwarf_menu.png", this));
        choosePieceMenu.add(new ChoosePieceMenu(11, 2, "knight_menu.png", this));
        choosePieceMenu.add(new ChoosePieceMenu(11, 4, "elf_menu.png", this));
    }

    /**
     * Method that sets the Menu for Attack, Heal and Move
     */
    private void setChooseFigureMovementMenu() {

        chooseMovement.add(new ChooseMovementMenu(11, 2, "heal_menu.png", this));
        chooseMovement.add(new ChooseMovementMenu(11, 3, "move_menu.png", this));
        chooseMovement.add(new ChooseMovementMenu(11, 4, "attack_menu.png", this));
    }

    /**
     * Method that sets new Lists
     */
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

    /**
     * Method that sets the Board
     */
    private void setBoard() {

        for (int column = 0; column < BOARD_ROWS; column++) {

            for (int row = 0; row < BOARD_COLUMNS; row++) {

                BoardGrid[column][row] = 0;
            }
        }
    }

    /**
     * Method that sets the obtacles on the BOard
     */
    private void setObstaclesOnBoard() {

        int numOfObstacles = random.nextInt(5) + 1;

        int randomColumn;
        int randomRow;

        for (int i = 0; i < numOfObstacles; i++) {

            randomColumn = random.nextInt(9);
            randomRow = random.nextInt(3) + 2;

            obstacles.add(new Obstacle(randomColumn, randomRow, "obstacle.png", this));
        }
    }

    /**
     * Method that draws the Board
     */
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

    /**
     * Method that Loads the image of the Board
     */
    private void boardImageLoad() {

        Image board = loadImage(boardFilePath);
        staticShapes.add(new DrawingImage(board, new Rectangle2D.Double(0, 0, board.getWidth(null), board.getHeight(null))));
    }

    /**
     * Method that Loads the background when Piece is chosen
     */
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

    /**
     * Method that visualises the Movement menu
     */
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

    /**
     * Method that visualises the Figure choosing menu
     */
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

    /**
     * Method that visualises the obstacles
     */
    private void visualiseObstacles() {

        for (Obstacle obstacle : obstacles) {

            int pieceColumn = obstacle.getFigureColumn();
            int pieceRow = obstacle.getFigureRow();

            Image piece = loadImage("images" + File.separator + obstacle.getFigureFilePath());
            pieceGraphics.add(new DrawingImage(piece, new Rectangle2D.Double(SQUARE_WIDTH * pieceColumn + 3, SQUARE_WIDTH * pieceRow + 3,
                    piece.getWidth(null), piece.getHeight(null))));
        }
    }

    /**
     * Method that visualises the Red pieces
     */
    private void visualiseRedPieces() {

        for (Piece redPiece : redPieces) {

            int pieceColumn = redPiece.getFigureColumn();
            int pieceRow = redPiece.getFigureRow();

            Image piece = loadImage("images" + File.separator + "red_pieces" + File.separator + redPiece.getFilePath());
            pieceGraphics.add(new DrawingImage(piece, new Rectangle2D.Double(SQUARE_WIDTH * pieceColumn + IMAGE_OFFSET, SQUARE_WIDTH * pieceRow + IMAGE_OFFSET,
                    piece.getWidth(null), piece.getHeight(null))));
        }
    }

    /**
     * Method that visualises the Black pieces
     */
    private void visualiseBlackPieces() {

        for (Piece blackPiece : blackPieces) {

            int pieceColumn = blackPiece.getFigureColumn();
            int pieceRow = blackPiece.getFigureRow();

            Image piece = loadImage("images" + File.separator + "black_pieces" + File.separator + blackPiece.getFilePath());
            pieceGraphics.add(new DrawingImage(piece, new Rectangle2D.Double(SQUARE_WIDTH * pieceColumn + IMAGE_OFFSET, SQUARE_WIDTH * pieceRow + IMAGE_OFFSET,
                    piece.getWidth(null), piece.getHeight(null))));
        }
    }

    /**
     * Method that gets the Obstacles
     *
     * @param x clicked column
     * @param y clicked row
     * @return The obstacle
     */
    public Obstacle getObstacle(int x, int y) {

        for (Obstacle obstacle : obstacles) {

            if (obstacle.getFigureColumn() == x && obstacle.getFigureRow() == y) {

                return obstacle;
            }
        }
        return null;
    }

    /**
     * Method that gets the Pieces
     *
     * @param x clicked column
     * @param y clicked row
     * @return The Piece
     */
    public Piece getPiece(int x, int y) {

        for (Piece piece : redPieces) {

            if (piece.getFigureColumn() == x && piece.getFigureRow() == y) {

                return piece;
            }
        }
        for (Piece piece : blackPieces) {

            if (piece.getFigureColumn() == x && piece.getFigureRow() == y) {

                return piece;
            }
        }
        return null;
    }

    /**
     * Method that loads the image
     *
     * @param imageFile image file
     * @return the image
     */
    private Image loadImage(String imageFile) {

        try {

            return ImageIO.read(new File(imageFile));

        } catch (IOException e) {

            return nullImage;
        }
    }

    /**
     * Method that paints the components
     *
     * @param g Object of Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);
        drawShapes(g2);

        drawPlayerTurn(g);
    }

    /**
     * Method that shows where Black player can place its figures
     *
     * @param g Object of Graphics
     */
    private void drawBlackPlayerAvailablePlacements(Graphics g) {

        for (int i = 0; i < 9; i++) {

            for (int c = 0; c < 5; c++) {

                g.setColor(Color.BLACK);
                g.fillRect(i * 65, c * 65, 65, 65);
            }
        }
    }

    /**
     * Method that shows where Red player can place its figures
     *
     * @param g Object of Graphics
     */
    private void drawRedPlayerAvailablePlacements(Graphics g) {

        for (int i = 0; i < 9; i++) {

            for (int c = 7; c >= 2; c--) {

                g.setColor(Color.RED);
                g.fillRect(i * 65, c * 65, 65, 65);
            }
        }
    }

    /**
     * Method that shows which player's turn is
     *
     * @param g Object of Graphics
     */
    private void drawPlayerTurn(Graphics g) {

        drawPlayersPointsAndRounds(g);

        if (checkPlayerTurn()) {

            g.setFont(playerTurnFont);
            g.setColor(Color.RED);
            g.drawString("Red player's turn", 700, 100);

            if (numberOfPlacedFigures < 4) {

                g.drawString("Place figure", 715, 120);
                drawRedPlayerAvailablePlacements(g);

            }
        }
        else if (!checkPlayerTurn()) {

            g.setFont(playerTurnFont);
            g.setColor(Color.BLACK);
            g.drawString("Black player's turn", 700, 400);

            if (numberOfPlacedFigures < 4) {

                g.drawString("Place a figure", 715, 420);
                drawBlackPlayerAvailablePlacements(g);

            }
        }
    }

    /**
     * Method that draws Player's Points and Rounds on the Board
     * @param g Object of Graphics
     */
    private void drawPlayersPointsAndRounds(Graphics g) {

        g.setFont(playerTurnFont);
        g.setColor(Color.BLACK);
        drawRoundCounterOnBoard(g);
        g.drawString("Black's Points: " + blackPlayer.getPoints(), 590, 60);
        g.drawString("Red's Points: " + redPlayer.getPoints(), 590, 40);
    }
    /**
     * Method that draws a Rounds on the Board
     * @param g Object of Graphics
     */
    private void drawRoundCounterOnBoard(Graphics g) {

        g.setFont(playerTurnFont);
        g.drawString("Rounds: " + roundCounter / 2, 590, 20);
    }

    /**
     * Method that draws the background
     *
     * @param g2 Object of Graphics2D
     */
    private void drawBackground(Graphics2D g2) {

        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Method that draws the shapes
     *
     * @param g2 Object of Graphics2D
     */
    private void drawShapes(Graphics2D g2) {

        for (DrawingShape shape : staticShapes) {
            shape.draw(g2);
        }
        for (DrawingShape shape : pieceGraphics) {
            shape.draw(g2);
        }
    }

    /**
     * Method that checks which player's turn it is
     */
    public boolean checkPlayerTurn() {

        return turnCounter % 2 == 1;
    }

    /**
     * Method that does the entire logic of the pieces
     *
     * @param clickedRow      The clicked Row
     * @param clickedColumn   The clicked Row
     * @param isRedTurn       If the figure is Red or Black
     * @param clickedPiece    The clicked Piece
     * @param clickedObstacle The clicked Obstacle
     */
    private void figureLogic(int clickedRow, int clickedColumn, boolean isRedTurn, Piece clickedPiece, Obstacle clickedObstacle) {

        if (activePieces == null && clickedPiece != null && ((isRedTurn && clickedPiece.isRed()) || (!isRedTurn && clickedPiece.isBlack()))) {

            activePieces = clickedPiece;

        } else if (activePieces != null && activePieces.canMove(clickedColumn, clickedRow) && ((isRedTurn && activePieces.isRed()) || (!isRedTurn && activePieces.isBlack()))) {

            if (chosenAttack || chosenMove) {

                if (chosenAttack) {

                    if (clickedObstacle != null) {

                        obstacles.remove(clickedObstacle);
                    } else if (clickedPiece.getPieceHealth() <= 0) {

                        if (clickedPiece.isRed()) {

                            redPieces.remove(clickedPiece);
                            System.out.println("Red's Figure is killed");
                        } else {

                            blackPieces.remove(clickedPiece);
                            System.out.println("Black's Figure is killed");
                        }
                    }
                } else {

                    // Do the figure move
                    activePieces.setFigureColumn(clickedColumn);
                    activePieces.setFigureRow(clickedRow);

                    // if piece is a pawn set hasMoved to true
                    if (activePieces.getClass().equals(Dwarf.class)) {

                        Dwarf castedDwarf = (Dwarf) (activePieces);
                        castedDwarf.setHasMoved(true);
                    }
                }
                activePieces = null;
                turnCounter++;
                roundCounter++;
            } else if (chosenHeal) {

                int randomNum = random.nextInt(5);

                System.out.printf("The rolled number to check for Second turn is: [%d]\n", randomNum);

                if (randomNum % 2 != 0) {

                    activePieces = null;
                    turnCounter++;
                }
            }
        } else if (activePieces != null && activePieces.getFigureColumn() == clickedColumn && activePieces.getFigureRow() == clickedRow) {

            activePieces = null;

        }
    }

    /**
     * Method that sets a Black Dwarf
     */
    private void setBlackDwarf() {

        blackPieces.add(new Dwarf(clickedColumn, clickedRow, false, "Dwarf.png", this,
                6, 2, 12));

        visualiseBlackPieces();

        System.out.printf("Placed 'Black' Dwarf [%d][%d]  || ", clickedColumn, clickedRow);
    }

    /**
     * Method that sets a Black Knight
     */
    private void setBlackKnight() {

        blackPieces.add(new Knight(clickedColumn, clickedRow, false, "Knight.png", this,
                8, 3, 15));

        visualiseBlackPieces();

        System.out.printf("Placed 'Black' Knight [%d][%d] || ", clickedColumn, clickedRow);
    }

    /**
     * Method that sets a Black Elf
     */
    private void setBlackElf() {

        blackPieces.add(new Elf(clickedColumn, clickedRow, false, "Elf.png", this,
                5, 1, 10));

        visualiseBlackPieces();

        System.out.printf("Placed 'Black' Elf [%d][%d]    || ", clickedColumn, clickedRow);
    }

    /**
     * Method that sets a Red Dwarf
     */
    private void setRedDwarf() {

        redPieces.add(new Dwarf(clickedColumn, clickedRow, true, "Dwarf.png", this,
                6, 2, 12));

        visualiseRedPieces();

        System.out.printf("Placed 'Red' Dwarf [%d][%d]\n", clickedColumn, clickedRow);
    }

    /**
     * Method that sets a Red Knight
     */
    private void setRedKnight() {

        redPieces.add(new Knight(clickedColumn, clickedRow, true, "Knight.png", this,
                8, 3, 15));

        visualiseRedPieces();

        System.out.printf("Placed 'Red' Knight [%d][%d]\n", clickedColumn, clickedRow);
    }

    /**
     * Method that sets a Red Elf
     */
    private void setRedElf() {

        redPieces.add(new Elf(clickedColumn, clickedRow, true, "Elf.png", this,
                5, 1, 10));

        visualiseRedPieces();

        System.out.printf("Placed 'Red' Elf [%d][%d]\n", clickedColumn, clickedRow);
    }

    /**
     * Method that checks if the figure action is chosen
     */
    private void checkIfFigureActionChosen() {

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

    /**
     * Method that checks the number of Placing Figures
     *
     * @param isRedTurn If the figure is Red or Black
     */
    private void logicForPlacingThePiecesOnBoard(boolean isRedTurn) {

        if (numberOfPlacedFigures < 4) {

            figurePlacementLogic(isRedTurn);

            checkIfFigureIsChosen();
        }
    }

    /**
     * Method that is does the logic for the Placement of the Figures
     *
     * @param isRedTurn If the Figure is Red or Black
     */
    private void figurePlacementLogic(boolean isRedTurn) {

        if (!isRedTurn) {

            checkBlackPlayerFigurePlacement();

        } else {

            checkRedPlayerFigurePlacement();
        }
    }

    /**
     * Method that checks what type of Piece is chosen to be placed
     */
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

    /**
     * Method that sets the Figure placing booleans to false
     */
    private void setChosenFigureBooleans() {

        chosenDwarf = false;
        chosenKnight = false;
        chosenElf = false;
    }

    /**
     * Method that checks Black player's placement
     */
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

    /**
     * Method that checks Red player's placement
     */
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

    /**
     * Method that checks if There is a Black piece
     *
     * @param clickedColumn The clicked Column
     * @param clickedRow    The clicked Row
     * @return The boolean if there is a figure
     */
    private boolean checkIfThereIsBlackPiece(int clickedColumn, int clickedRow) {

        for (Piece p : blackPieces) {

            if (p.getFigureColumn() == clickedColumn && p.getFigureRow() == clickedRow) {

                System.out.println("There is a Figure");
                return true;
            }
        }
        return false;
    }

    /**
     * Method that checks if There is a Red piece
     *
     * @param clickedColumn The clicked Column
     * @param clickedRow    The clicked Row
     * @return The boolean if there is a figure
     */
    private boolean checkIfThereIsRedPiece(int clickedColumn, int clickedRow) {

        for (Piece p : redPieces) {

            if (p.getFigureColumn() == clickedColumn && p.getFigureRow() == clickedRow) {

                System.out.println("There is a Figure");
                return true;
            }
        }
        return false;
    }

    /**
     * Method that set values when figure is chosen and placed
     */
    private void whenFigureChosenSetValues() {

        setChosenFigureBooleans();
        turnCounter++;
        isFigureChosen = false;
        numberOfPlacedFigures++;
    }

    /**
     * Interface for Drawing the Shapes
     */
    interface DrawingShape {

        void draw(Graphics2D g2);
    }

    static class DrawingImage implements DrawingShape {

        public Image image;
        public Rectangle2D rect;

        /**
         * Constructor
         *
         * @param image The Image
         * @param rect  The rectangle
         */
        public DrawingImage(Image image, Rectangle2D rect) {

            this.image = image;
            this.rect = rect;
        }

        /**
         * Method that draws the bounds
         *
         * @param g2 Object of Graphics2D
         */
        @Override
        public void draw(Graphics2D g2) {

            Rectangle2D bounds = rect.getBounds2D();

            g2.drawImage(image, (int) bounds.getMinX(), (int) bounds.getMinY(), (int) bounds.getMaxX(), (int) bounds.getMaxY(),
                    0, 0, image.getWidth(null), image.getHeight(null), null);
        }
    }
}