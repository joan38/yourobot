package fr.umlv.yourobot;

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

        // Launching the application manager.
        Manager manager = new Manager(null, new Player[2], WIDTH, HEIGHT);
        manager.run();
    }
}
