package fr.umlv.yourobot;

import fr.umlv.yourobot.context.Game;
import fr.umlv.yourobot.context.Menu;
import fr.umlv.yourobot.context.WorldSet;
import fr.umlv.yourobot.elements.*;
import fr.umlv.yourobot.elements.robot.RobotIA;
import java.util.Objects;

/**
 * Manage the logic of the game.
 *
 * License: GNU Public license v3.
 *
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Manager {

    private WorldSet worlds;
    private final Player[] players;
    private Game renderGame;
    private final Menu renderMenu;
    private int lastHealth[] = new int[2];

    public void newGame() {
        worlds.newGame();
        lastHealth[0] = 100;
        lastHealth[1] = 100;
        players[0].getRobot().resetRobot(100);
        if (players[1] != null) {
            players[1].getRobot().resetRobot(100);
        }

    }

    /**
     * Tell which renderer is currently displayed. Useful to manage the
     * comportment of the application.
     */
    private enum CurrentRenderer {

        Game,
        Menu,
        Pause
    }

    /**
     * Defines the difficulty of the game.
     */
    public enum Difficuly {

        /**
         * Easy.
         */
        ICanWin("I can win", 10, 2, 0.05f, 0.05f),
        /**
         * Easy+.
         */
        BringItOn("Bring it on", 10, 4, 0.1f, 0.08f),
        /**
         * Normal.
         */
        HurtMePlenty("Hurt Me Plenty", 5, 4, 0.3f, 0.1f),
        /**
         * Hard.
         */
        HardCore("Hardcore", 3, 4, 0.8f, 0.15f),
        /**
         * Nightmare.
         */
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

    /**
     * Game manager.
     *
     * @param worlds Worlds to play.
     * @param players Players of the game. (include settings etc...)
     */
    public Manager(WorldSet worlds, Player[] players) {
        this.worlds = worlds;
        this.players = players;

        renderMenu = new Menu(this);

        lastHealth[0] = 100;
        lastHealth[1] = 100;
    }

    /**
     * Run the application.
     */
    public void run() {
        // Launching the application core.
        final ApplicationCore core = new ApplicationCore("DR. SLIDE VS MR. MAD", Settings.getWidth(), Settings.getHeight());

        // Launching the application code.
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                renderMenu.run(core);
            }
        });
        
        t.start();
    }

    /**
     * Launch a new game.
     *
     * @param map The map to start the game on.
     * @param isReplay Set to true if the map is a replay or a new level. (Used
     * to manage health between levels)
     * @param numberOfHumanPlayer Number of Human player.
     * @param d Difficulty of the game. number. (Set to 0 by default)
     *
     * @return The launched game.
     */
    public Game newGame(World map, boolean isReplay, int numberOfHumanPlayer, Difficuly d, int increaseHealthOfPlayer) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(d);

        // Create a game.
        Player[] array = new Player[2];
        array[0] = players[0];

        // Health management.
        if (isReplay == true) {
            // Reseting the health because we are replaying.
            players[0].getRobot().resetRobot(lastHealth[0]);
            if (numberOfHumanPlayer > 1) {
                players[1].getRobot().resetRobot(lastHealth[1]);
            }
        } else {
            // Storing the health because we are playing a new level.
            lastHealth[0] = players[0].getRobot().getHealth();
            if (numberOfHumanPlayer > 1) {
                lastHealth[1] = players[1].getRobot().getHealth();
            }
        }
        players[0].getRobot().applyDamage(-increaseHealthOfPlayer); // Giving a life boost for newbies.
        if (numberOfHumanPlayer > 1) {
            array[1] = players[1];
            players[1].getRobot().applyDamage(-increaseHealthOfPlayer); // Giving a life boost for newbies.
        }

        // Setting the speed of RobotIA.
        for (RobotIA iA : map.getRobotIAs()) {
            iA.setSpeed(d.robotIASpeed);
        }

        // Starting the music.
        MusicPlayer.getMusiquePlayer().playMusic(map.getMusique());

        renderGame = new Game(map, array, d.numberOfBonus, d.delayBeforeCreateBonus, d.robotIAPower);
        return renderGame;
    }

    /**
     * Get maps that the manager is holding.
     *
     * @return Maps to play.
     */
    public WorldSet getMaps() {
        return worlds;
    }

    public Player[] getPlayers() {
        return players;
    }
    
    
}
