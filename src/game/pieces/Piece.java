package game.pieces;

import game.Board;

public class Piece {

    private int figureColumn;
    private int figureRow;
    final private boolean isFigureRed;
    private String figureFilePath;
    public Board board;
    
    public Piece(int figureColumn, int figureRow, boolean isFigureRed, String figureFilePath, Board board)
    {
        this.isFigureRed = isFigureRed;
        this.figureColumn = figureColumn;
        this.figureRow = figureRow;
        this.figureFilePath = figureFilePath;
        this.board = board;
    }
    
    public String getFilePath()
    {
        return figureFilePath;
    }
    
    public void setFilePath(String path)
    {
        this.figureFilePath = path;
    }
    
    public boolean isRed()
    {
        return isFigureRed;
    }
    
    public boolean isBlack()
    {
        return !isFigureRed;
    }
    
    public void setFigureColumn(int figureColumn)
    {
        this.figureColumn = figureColumn;
    }
    
    public void setFigureRow(int figureRow)
    {
        this.figureRow = figureRow;
    }
    
    public int getFigureColumn()
    {
        return figureColumn;
    }
    
    public int getFigureRow()
    {
        return figureRow;
    }
    
    public boolean canMove(int destinationColumn, int destinationRow)  {

        return false;
    }
}
