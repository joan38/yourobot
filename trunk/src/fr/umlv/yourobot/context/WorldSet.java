package fr.umlv.yourobot.context;

import fr.umlv.yourobot.elements.World;

/**
 * Represent a set of world playable.
 * 
 * License: GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public interface WorldSet {
    
    /**
     * Gets the next world of the set of world.
     * @return A World or null if the end of the game is reached.
     */
    public World getNextWorld();
    
    /**
     * Get the current world in order to be replayed.
     * @return The world to be played.
     */
    public World getReplayWorld();
    
    /**
     * Returns true if there is one or more worlds.
     * @return True if more worlds, false otherwise.
     */
    public boolean hasMoreWorld();
    
    /**
     * Reset the worldSet in order to restart a new game.
     */
    public void newGame();
}
