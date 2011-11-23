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
    private static int stride;

    /**
     * 
     * @param width
     * @param height
     * @param size
     * @param stride 
     */
    public static void setYouRobotSetting(int width, int height, int size, int stride) {
        YouRobotSetting.height = height;
        YouRobotSetting.size = size;
        YouRobotSetting.stride = stride;
        YouRobotSetting.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static int getSize() {
        return size;
    }

    /**
     * Size of a bonus.
     * @return 
     */
    public static int getStride() {
        return stride;
    }

    public static int getWidth() {
        return width;
    }
}
