package fr.umlv.yourobot;

import fr.umlv.yourobot.elements.SampleWorldFactory;
import fr.umlv.yourobot.elements.TextureLoader;
import fr.umlv.yourobot.elements.World;
import fr.umlv.yourobot.elements.robot.Robot;
import fr.umlv.yourobot.elements.robot.RobotPlayer;
import fr.umlv.zen.KeyboardKey;
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
        final int SIZE = 20;
        final int EFFECT_AREA = 100;
        final int DETECTION_AREA = 200;

        // Banner
        System.out.println("YouRobot version " + versionMajor + "." + versionMinor + " - Licensed under GNU GPLv3.");
        System.out.println("Damien Girard and Joan Goyeau.");

        // Setting YouRobot settings.
        YouRobotSetting.setYouRobotSetting(WIDTH, HEIGHT, SIZE, EFFECT_AREA, DETECTION_AREA);

        // Creation of a robot and player.
        RobotPlayer robot, robot2;
        try {
            robot = new RobotPlayer(50, TextureLoader.loadTexture("src/textures/robot_human_normal.png", true),
                    TextureLoader.loadTexture("src/textures/robot_human_boost.png", true),
                    TextureLoader.loadTexture("src/textures/robot_human_brake.png", true), 50, 50);
            robot2 = new RobotPlayer(50, TextureLoader.loadTexture("src/textures/robot_human_normal_2.png", true),
                    TextureLoader.loadTexture("src/textures/robot_human_boost_2.png", true),
                    TextureLoader.loadTexture("src/textures/robot_human_brake_2.png", true), 100, 100);
        } catch (IOException ex) {
            System.out.println(ex);
            return;
        }
        
        Player[] players = new Player[2];
        players[0] = new Player(robot);
        players[1] = new Player(robot2);
        
        // Setting the default key of second player.
        players[1].setKeyBinding(RobotKeyAction.Boost, KeyboardKey.SHIFT);
        players[1].setKeyBinding(RobotKeyAction.Turn_Left, KeyboardKey.LEFT);
        players[1].setKeyBinding(RobotKeyAction.Turn_Right, KeyboardKey.RIGHT);
        players[1].setKeyBinding(RobotKeyAction.Brake, KeyboardKey.DOWN);
        
        Manager manager = new Manager(SampleWorldFactory.getWorldSet1(), players);  // Launching the application manager.
        manager.run();
    }
}
