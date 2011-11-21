package fr.umlv.yourobot;

import fr.umlv.yourobot.elements.*;
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

    private World[] worlds;
    private final Player[] players;
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

    public Manager(World[] worlds, Player[] players) {
        this.worlds = worlds;
        this.players = players;

        renderMenu = new Menu(this);
        // TODO init of the pause menu.
    }

    /**
     * Run the application.
     * 
     * @param width Width of the window of the game.
     * @param height Height of the window of the game.
     */
    public void run() {
        Application.run("YouRobot", YouRobotSetting.getWidth(), YouRobotSetting.getHeight(), new MainManager());
    }

    /**
     * Launch a new game.
     * 
     * @param map The map to start the game on.
     * @param numberOfHumanPlayer Number of Human player.
     */
    public Game newGame(World map, int numberOfHumanPlayer) {
        // Create a game.
        renderGame = new Game(map, players);
        return renderGame;
    }

    public World[] getMaps() {
        return worlds;
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
