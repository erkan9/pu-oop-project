package game.player;

public class RedPlayer extends Player{

    /**
     * Constructor
     * @param dwarfCounter Counter for placed Dwarfs
     * @param knightCounter Counter for placed Knights
     * @param elfCounter Counter for placed Elves
     */
    public RedPlayer(int dwarfCounter, int knightCounter, int elfCounter, int points, int killedPieces) {

        super(dwarfCounter, knightCounter, elfCounter, points, killedPieces);
    }
}
