package fr.umlv.yourobot;

import fr.umlv.yourobot.field.*;
import fr.umlv.zen.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Manage the logic of the application.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Game implements ApplicationCode, ApplicationRenderCode {
    private final int width;
    private final int height;
    private final Player[] players;
    
    private final World world; // World of the game.

    public Game(int width, int height, World world, Player[] players) {
        Objects.requireNonNull(players[0]);
        Objects.requireNonNull(world);
        if (width < 0 || height < 0)
            throw new IllegalArgumentException("Invalid width or height.");
        
        this.width = width;
        this.height = height;
        this.world = world;
        this.players = players;
    }
    
    /**
     * Game logic here.
     * 
     * @param context Context.
     */
    @Override
    public void run(ApplicationContext context) {
        
    }

    /**
     * Game rendering here.
     * @param gd Graphic context.
     */
    @Override
    public void render(Graphics2D gd) {
        
        
    }
    
    
}
