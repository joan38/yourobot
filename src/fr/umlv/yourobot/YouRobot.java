package fr.umlv.yourobot;

import fr.umlv.yourobot.field.SampleWorldFactory;
import fr.umlv.yourobot.field.TextureLoader;
import fr.umlv.yourobot.field.World;
import fr.umlv.yourobot.players.Robot;
import fr.umlv.yourobot.players.RobotPlayer;
import java.io.IOException;

/**
 * Entry point of the application.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class YouRobot {

    public final static int versionMajor = 0;
    public final static int versionMinor = 1;

    public static void main(String[] args) {
        // Constant.
        final int WIDTH = 800;
        final int HEIGHT = 600;
        final int SIZE = 30;
        final int STRIDE = 100;

        // Banner
        System.out.println("YouRobot version " + versionMajor + "." + versionMinor + " - Licensed under GNU GPLv3.");
        System.out.println("Damien Girard and Joan Goyeau.");

        // Setting YouRobot settings.
        YouRobotSetting.setYouRobotSetting(WIDTH, HEIGHT, SIZE, STRIDE);

        // Creation of a dummy world.
        World[] worldList = new World[1];
        World dummyWorld = SampleWorldFactory.getDummyWorld1();
        worldList[0] = dummyWorld;

        // Creation of a robot and player.
        Robot robot;
        try {
            robot = new RobotPlayer(50, null, TextureLoader.loadTexture("src/textures/robot_human_normal.png"),
                    TextureLoader.loadTexture("src/textures/robot_human_boost.png"),
                    TextureLoader.loadTexture("src/textures/robot_human_brake.png"), 50, 50);
        } catch (IOException ex) {
            System.out.println(ex);
            return;
        }

        Player player = new Player(robot); // Launching the application manager.
        Player[] players = new Player[2];
        players[0] = player;
        Manager manager = new Manager(worldList, players);
        manager.run();
    }
}
