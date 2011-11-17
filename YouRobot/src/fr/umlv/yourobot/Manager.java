package fr.umlv.yourobot;

import fr.umlv.yourobot.field.*;
import fr.umlv.zen.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Objects;

/**
 * Manage the logic of the game.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Manager {

    private World maps;
    private final Player[] players;
    private final int width;
    private final int height;
    private Game renderGame;
    private final Menu renderMenu;
    //private final ApplicationRenderCode renderPauseMenu; TODO
    private CurrentRenderer currentRenderer; 

    /**
     * Tell which renderer is currently displayed.
     * Useful to manage the comportment of the application.
     */
    private enum CurrentRenderer {

        Game,
        Menu,
        Pause
    }

    public Manager(World maps, Player[] players, int width, int height) {
        this.maps = maps;
        this.players = players;
        this.width = width;
        this.height = height;

        renderMenu = new Menu(width, height, this);
        // TODO init of the pause menu.
    }

    /**
     * Run the application.
     * 
     * @param width Width of the window of the game.
     * @param height Height of the window of the game.
     */
    public void run() {
        Application.run("YouRobot", width, height, new MainManager());
    }

    /**
     * Launch a new game.
     * 
     * @param map The map to start the game on.
     * @param numberOfHumanPlayer Number of Human player.
     */
    public void newGame(World map, int numberOfHumanPlayer) {
        // Create a game.
    }

    private class MainManager implements ApplicationCode {

        @Override
        public void run(ApplicationContext context) {
            // Hello, setting the renderer the menu.
            currentRenderer = CurrentRenderer.Menu;
            
            // Drawing the menu.
            context.render(renderMenu);
            
            // Managing the menu.
            renderMenu.run(context);
        }
    }
}
