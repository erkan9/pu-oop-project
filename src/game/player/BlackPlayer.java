package game.player;

public class BlackPlayer extends Player{

    /**
     * Constructor
     * @param dwarfCounter Counter for placed Dwarfs
     * @param knightCounter Counter for placed Knights
     * @param elfCounter Counter for placed Elves
     */
    public BlackPlayer(int dwarfCounter, int knightCounter, int elfCounter, int points) {

        super(dwarfCounter, knightCounter, elfCounter, points);
    }
}
