package fr.umlv.yourobot;

import fr.umlv.yourobot.field.World;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

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
        World dummyWorld = new World();
        try {
            dummyWorld.setBackgroundPattern(ImageIO.read(new File("src/textures/metal_floor.jpg")));
        } catch (IOException ex) {
            System.err.println("Texture not found. "+ ex.getMessage());
            return;
        }
        worldList[0] = dummyWorld;
        
        // Launching the application manager.
        Manager manager = new Manager(worldList, new Player[2]);
        manager.run();
    }
}
