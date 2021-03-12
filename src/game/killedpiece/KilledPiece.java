package game.killedpiece;

public class KilledPiece<T> {

    private byte killedPieceIndex = 0;
    private final String[] killedPieceCollector;

    public KilledPiece() {

        this.killedPieceCollector = new String[6];
    }

    /**
     * Method that adds a new Killed Piece to the Custom Array List
     * @param element The Killed Piece that we want to add
     */
    public void add(String element) {

        this.killedPieceCollector[this.killedPieceIndex++] = element;
    }

    /**
     * Method that returns the Killed Piece
     * @param index The pointer to the Killed Piece
     * @return The Killed Piece
     */
    public T get(int index) {
        return (T)this.killedPieceCollector[index];
    }
}