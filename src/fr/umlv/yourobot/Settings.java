package fr.umlv.yourobot;

/**
 * General settings of the application.
 * Static because those general settings are accessible anywhere.
 * 
 * License: GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Settings {

    private static int width;
    private static int height;
    private static int size;
    private static int effectArea;
    private static int detectionArea;
    private static int leurreDurationBeforeActivation = 2;

    /**
     * Init the settings of YouRobot.
     * 
     * @param width Width resolution of YouRobot.
     * @param height Height resolution of YouRobot.
     * @param size   Size of an element.
     * @param effectArea Effect area for bonusses.
     * @param detectionArea Detection area for IA Robots.
     */
    public static void setYouRobotSetting(int width, int height, int size, int effectArea, int detectionArea) {
        Settings.height = height;
        Settings.size = size;
        Settings.effectArea = effectArea;
        Settings.width = width;
        Settings.detectionArea = detectionArea;
    }

    /**
     * Returns the height of the window.
     * @return The height of the window.
     */
    public static int getHeight() {
        return height;
    }

    /**
     * Returns the size of an element in the game.
     * @return The size of an element.
     * 
     * Note: Elements are square in the game.
     */
    public static int getSize() {
        return size;
    }

    /**
     * Returns the effect area of a bonus.
     * @return The size of the area.
     */
    public static int getEffectArea() {
        return effectArea;
    }

    /**
     * Returns the width of the window.
     * @return The width of the window.
     */
    public static int getWidth() {
        return width;
    }

    /**
     * Returns the detection area for RobotIA.
     * @return The detection area size.
     */
    public static int getDetectionArea() {
        return detectionArea;
    }

    /**
     * Returns the duration of a leurre bonus before it get activated.
     * @return The duration.
     */
    public static int getLeurreDurationBeforeActivation() {
        return leurreDurationBeforeActivation;
    }

    /**
     * Set the duration before a leurre bonus is activated.
     * @param leurreDurationBeforeActivation In seconds.
     */
    public static void setLeurreDurationBeforeActivation(int leurreDurationBeforeActivation) {
        Settings.leurreDurationBeforeActivation = leurreDurationBeforeActivation;
    }
}
