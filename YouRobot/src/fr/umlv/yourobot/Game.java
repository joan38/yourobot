package fr.umlv.yourobot;

import fr.umlv.yourobot.field.*;
import fr.umlv.zen.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manage the logic of the application.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Game implements ApplicationCode, ApplicationRenderCode {

    private final Player[] players;
    private final World world; // World of the game.

    public Game(World world, Player[] players) {
        //Objects.requireNonNull(players[0]);
        Objects.requireNonNull(world);

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
        // Drawing the menu.
        context.render(this);

        // Managing the menu.
        for (;;) {
            final KeyboardEvent event = context.pollKeyboard();
            if (event == null) {
                // Nothing to do, redrawing.
                context.render(this);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                }
                continue;
            }

            switch (event.getKey()) {
                case W:
                    System.out.println("Fin du jeu.");
                    return;
            }

            context.render(this);
        }
    }

    /**
     * Game rendering here.
     * @param gd Graphic context.
     */
    @Override
    public void render(Graphics2D gd) {
        world.render(gd);
    }
}
