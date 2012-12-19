package fr.umlv.yourobot;

import fr.umlv.yourobot.elements.SampleWorldFactory;
import fr.umlv.yourobot.elements.TextureLoader;
import fr.umlv.yourobot.elements.robot.RobotPlayer;
import fr.umlv.zen.KeyboardKey;
import java.io.IOException;

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
    public final static int versionMinor = 1;

    public static void main(String[] args) throws IOException {
        // Constant.
        final int WIDTH = 800;
        final int HEIGHT = 600;
        final int SIZE = 20;
        final int EFFECT_AREA = 100;
        final int DETECTION_AREA = 200;

        // Banner
        System.out.println("YouRobot version " + versionMajor + "." + versionMinor + " - Licensed under GNU GPLv3.");
        System.out.println("Damien Girard and Joan Goyeau.");

        // Setting YouRobot settings.
        Settings.setYouRobotSetting(WIDTH, HEIGHT, SIZE, EFFECT_AREA, DETECTION_AREA);

        // Registering musics.
        MusicPlayer.getMusiquePlayer().registerMusic("intro", YouRobot.class.getResource("/sounds/music/00-quake-iii-arena-intro.mp3").getPath());
        MusicPlayer.getMusiquePlayer().registerMusic("1", YouRobot.class.getResource("/sounds/music/01-quake-iii-arena.mp3").getPath());
        MusicPlayer.getMusiquePlayer().registerMusic("2", YouRobot.class.getResource("/sounds/music/02-quake-iii-arena.mp3").getPath());
        MusicPlayer.getMusiquePlayer().registerMusic("3", YouRobot.class.getResource("/sounds/music/03-quake-iii-arena.mp3").getPath());
        MusicPlayer.getMusiquePlayer().registerMusic("4", YouRobot.class.getResource("/sounds/music/04-quake-iii-arena.mp3").getPath());
        MusicPlayer.getMusiquePlayer().registerMusic("5", YouRobot.class.getResource("/sounds/music/05-quake-iii-arena.mp3").getPath());
        MusicPlayer.getMusiquePlayer().registerMusic("6", YouRobot.class.getResource("/sounds/music/06-quake-iii-arena.mp3").getPath());

        // Registering sounds.
        SoundPlayer.getPlayer().registerSound("menu", YouRobot.class.getResource("/sounds/menu.wav").getPath());
        SoundPlayer.getPlayer().registerSound("menuEnter", YouRobot.class.getResource("/sounds/menuEnter.wav").getPath());
        SoundPlayer.getPlayer().registerSound("nobonus", YouRobot.class.getResource("/sounds/nobonus.wav").getPath());
        SoundPlayer.getPlayer().registerSound("bonuspickup", YouRobot.class.getResource("/sounds/bonuspickup.wav").getPath());
        SoundPlayer.getPlayer().registerSound("bonusbomb", YouRobot.class.getResource("/sounds/bonusbomb.mp3").getPath());
        SoundPlayer.getPlayer().registerSound("bonusLeurre", YouRobot.class.getResource("/sounds/bonusLeurre.wav").getPath());
        SoundPlayer.getPlayer().registerSound("bonusSnap", YouRobot.class.getResource("/sounds/bonusSnap.wav").getPath());


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
        players[1].setKeyBinding(RobotKeyAction.Boost, KeyboardKey.UP);
        players[1].setKeyBinding(RobotKeyAction.Take, KeyboardKey.SHIFT);
        players[1].setKeyBinding(RobotKeyAction.Turn_Left, KeyboardKey.LEFT);
        players[1].setKeyBinding(RobotKeyAction.Turn_Right, KeyboardKey.RIGHT);

        Manager manager = new Manager(SampleWorldFactory.getWorldSet1(), players);  // Launching the application manager.
        manager.run();
    }
}
