package fr.umlv.yourobot;

import fr.umlv.yourobot.context.Game;
import fr.umlv.yourobot.context.Menu;
import fr.umlv.yourobot.context.WorldSet;
import fr.umlv.yourobot.elements.*;
import fr.umlv.yourobot.elements.robot.RobotIA;
import fr.umlv.zen.*;
import java.util.Objects;

/**
 * Manage the logic of the game.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Manager {

    private WorldSet worlds;
    private final Player[] players;
    private Game renderGame;
    private final Menu renderMenu;

    /**
     * Tell which renderer is currently displayed.
     * Useful to manage the comportment of the application.
     */
    private enum CurrentRenderer {

        Game,
        Menu,
        Pause
    }

    public enum Difficuly {

        ICanWin("I can win", 10, 2, 0.05f, 0.05f),
        BringItOn("Bring it on", 10, 4, 0.1f, 0.08f),
        HurtMePlenty("Hurt Me Plenty", 5, 4, 0.3f, 0.1f),
        HardCore("Hardcore", 3, 4, 0.8f, 0.15f),
        Nightmare("Nightmare", 3, 6, 1.5f, 0.2f);

        private Difficuly(String name, int numberOfBonus, int delayBeforeCreateBonus, float robotIAPower, float robotIASpeed) {
            this.name = name;
            this.numberOfBonus = numberOfBonus;
            this.delayBeforeCreateBonus = delayBeforeCreateBonus;
            this.robotIAPower = robotIAPower;
            this.robotIASpeed = robotIASpeed;
        }
        private final String name;
        private final int numberOfBonus;
        private final int delayBeforeCreateBonus;
        private final float robotIAPower;
        private final float robotIASpeed;

        @Override
        public String toString() {
            return name;
        }
    }

    public Manager(WorldSet worlds, Player[] players) {
        this.worlds = worlds;
        this.players = players;

        renderMenu = new Menu(this);
    }

    /**
     * Run the application.
     * 
     * @param width Width of the window of the game.
     * @param height Height of the window of the game.
     */
    public void run() {
        Application.run("YouRobot", Settings.getWidth(), Settings.getHeight(), new MainManager());
    }

    /**
     * Launch a new game.
     * 
     * @param map The map to start the game on.
     * @param numberOfHumanPlayer Number of Human player.
     */
    public Game newGame(World map, int numberOfHumanPlayer, Difficuly d) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(d);

        // Create a game.
        Player[] array = new Player[2];
        array[0] = players[0];
        if (numberOfHumanPlayer > 1) {
            array[1] = players[1];
        }

        // Setting the speed of RobotIA.
        for (RobotIA iA : map.getRobotIAs()) {
            iA.setSpeed(d.robotIASpeed);
        }

        renderGame = new Game(map, array, d.numberOfBonus, d.delayBeforeCreateBonus, d.robotIAPower);
        return renderGame;
    }

    public WorldSet getMaps() {
        return worlds;
    }

    private class MainManager implements ApplicationCode {

        @Override
        public void run(ApplicationContext context) {
            // Drawing the menu.
            context.render(renderMenu);

            // Managing the menu.
            renderMenu.run(context);
        }
    }
}
