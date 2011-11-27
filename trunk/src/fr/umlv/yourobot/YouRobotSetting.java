package fr.umlv.yourobot;

/**
 * General settings of the application.
 * Static because those general settings are accessible anywhere.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class YouRobotSetting {

    private static int width;
    private static int height;
    private static int size;
    private static int effectArea;
    private static int detectionArea;
    private static int leurreDurationBeforeActivation = 8;

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
        YouRobotSetting.height = height;
        YouRobotSetting.size = size;
        YouRobotSetting.effectArea = effectArea;
        YouRobotSetting.width = width;
        YouRobotSetting.detectionArea = detectionArea;
    }

    public static int getHeight() {
        return height;
    }

    public static int getSize() {
        return size;
    }

    public static int getEffectArea() {
        return effectArea;
    }

    public static int getWidth() {
        return width;
    }

    public static int getDetectionArea() {
        return detectionArea;
    }

    public static int getLeurreDurationBeforeActivation() {
        return leurreDurationBeforeActivation;
    }

    /**
     * Set the duration before a leurre bonus is activated.
     * @param leurreDurationBeforeActivation In seconds.
     */
    public static void setLeurreDurationBeforeActivation(int leurreDurationBeforeActivation) {
        YouRobotSetting.leurreDurationBeforeActivation = leurreDurationBeforeActivation;
    }
}
