package fr.umlv.yourobot;

import fr.umlv.yourobot.elements.GameWorlds;
import fr.umlv.yourobot.elements.SampleWorldFactory;
import fr.umlv.yourobot.elements.TextureLoader;
import fr.umlv.yourobot.elements.robot.RobotPlayer;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Entry point of the application.
 *
 * License: GNU Public license v3.
 *
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class YouRobot {

    public final static int versionMajor = 0;
    public final static int versionMinor = 5;

    public static void main(String[] args) throws IOException, URISyntaxException {
        // Constant.
        final int WIDTH = 800;
        final int HEIGHT = 600;
        final int SIZE = 20;
        final int EFFECT_AREA = 100;
        final int DETECTION_AREA = 200;
        final int GRID_X = 40;
        final int GRID_Y = 30;

        // Banner
        System.out.println("Dr. SLIDE version " + versionMajor + "." + versionMinor + " - Licensed under GNU GPLv3.");
        System.out.println("Damien Girard and Joan Goyeau.");

        // Setting YouRobot settings.
        Settings.setYouRobotSetting(WIDTH, HEIGHT, SIZE, EFFECT_AREA, DETECTION_AREA, GRID_X, GRID_Y);

        // Registering musics.
        MusicPlayer.getMusiquePlayer().registerMusic("intro", YouRobot.class.getResourceAsStream("/sounds/music/00-quake-iii-arena-intro.mp3"));
        MusicPlayer.getMusiquePlayer().registerMusic("1", YouRobot.class.getResourceAsStream("/sounds/music/01-quake-iii-arena.mp3"));
        MusicPlayer.getMusiquePlayer().registerMusic("2", YouRobot.class.getResourceAsStream("/sounds/music/02-quake-iii-arena.mp3"));
        MusicPlayer.getMusiquePlayer().registerMusic("3", YouRobot.class.getResourceAsStream("/sounds/music/03-quake-iii-arena.mp3"));
        MusicPlayer.getMusiquePlayer().registerMusic("4", YouRobot.class.getResourceAsStream("/sounds/music/04-quake-iii-arena.mp3"));
        MusicPlayer.getMusiquePlayer().registerMusic("5", YouRobot.class.getResourceAsStream("/sounds/music/05-quake-iii-arena.mp3"));
        MusicPlayer.getMusiquePlayer().registerMusic("6", YouRobot.class.getResourceAsStream("/sounds/music/06-quake-iii-arena.mp3"));

        // Registering sounds.
        SoundPlayer.getPlayer().registerSound("menu", YouRobot.class.getResourceAsStream("/sounds/menu.wav"));
        SoundPlayer.getPlayer().registerSound("menuEnter", YouRobot.class.getResourceAsStream("/sounds/menuEnter.wav"));
        SoundPlayer.getPlayer().registerSound("menuExit", YouRobot.class.getResourceAsStream("/sounds/menuExit.wav"));
        SoundPlayer.getPlayer().registerSound("nobonus", YouRobot.class.getResourceAsStream("/sounds/nobonus.wav"));
        SoundPlayer.getPlayer().registerSound("bonuspickup", YouRobot.class.getResourceAsStream("/sounds/bonuspickup.wav"));
        SoundPlayer.getPlayer().registerSound("bonusbomb", YouRobot.class.getResourceAsStream("/sounds/bonusbomb.mp3"));
        SoundPlayer.getPlayer().registerSound("bonusLeurre", YouRobot.class.getResourceAsStream("/sounds/bonusLeurre.wav"));
        SoundPlayer.getPlayer().registerSound("bonusSnap", YouRobot.class.getResourceAsStream("/sounds/bonusSnap.wav"));
        SoundPlayer.getPlayer().registerSound("bonusmedipack", YouRobot.class.getResourceAsStream("/sounds/bonusmedipack.wav"));

        // Creation of a robot and player.
        RobotPlayer robot, robot2;
        try {
            robot = new RobotPlayer(50, TextureLoader.loadTexture(YouRobot.class.getResource("/textures/robot_human_normal.png"), true),
                    TextureLoader.loadTexture(YouRobot.class.getResource("/textures/robot_human_boost.png"), true),
                    TextureLoader.loadTexture(YouRobot.class.getResource("/textures/robot_human_brake.png"), true), 50, 50);
            robot2 = new RobotPlayer(50, TextureLoader.loadTexture(YouRobot.class.getResource("/textures/robot_human_normal_2.png"), true),
                    TextureLoader.loadTexture(YouRobot.class.getResource("/textures/robot_human_boost_2.png"), true),
                    TextureLoader.loadTexture(YouRobot.class.getResource("/textures/robot_human_brake_2.png"), true), 100, 100);
        } catch (IOException ex) {
            System.out.println("Can't load textures. " + ex);
            return;
        }

        Player[] players = new Player[2];
        players[0] = new Player(robot);
        players[1] = new Player(robot2);

        // Setting the default key of second player.
        players[1].setKeyBinding(RobotKeyAction.Boost, KeyEvent.VK_UP);
        players[1].setKeyBinding(RobotKeyAction.Take, KeyEvent.VK_SHIFT);
        players[1].setKeyBinding(RobotKeyAction.Turn_Left, KeyEvent.VK_LEFT);
        players[1].setKeyBinding(RobotKeyAction.Turn_Right, KeyEvent.VK_RIGHT);

        Manager manager = new Manager(new GameWorlds(), players);  // Launching the application manager.
        manager.run();
    }
}
