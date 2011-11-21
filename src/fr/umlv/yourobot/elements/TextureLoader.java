package fr.umlv.yourobot.elements;

import fr.umlv.yourobot.YouRobotSetting;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * Flyweight pattern. Prevent the loading of 200 textures in memory.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class TextureLoader {

    private static HashMap<String, BufferedImage> textures = new HashMap<String, BufferedImage>();

    /**
     * Load the texture path. If the texture has been previously loaded, then the
     * image previously loaded is directly returned.
     * 
     * @param path Path of the texture to load.
     * @param scaleDown Scale down the texture to the size defined in settings of YouRobot.
     * 
     * @note A texture must be a square image.
     * 
     * @return The image of the texture.
     */
    public static BufferedImage loadTexture(String path, boolean scaleDown) throws IOException {
        if (textures.containsKey(path)) {
            return textures.get(path);
        }

        BufferedImage texture = ImageIO.read(new File(path));
        if (texture.getWidth() != texture.getHeight()) {
            throw new IOException("Invalid texture format. The texture is not a square.");
        }

        if (scaleDown == true) {
            // Scaling down the texture in order to have it to the right size.
            AffineTransform textureTransformer = new AffineTransform();
            textureTransformer.setToIdentity();
            double scale = (double) YouRobotSetting.getSize() / (double) texture.getHeight();
            textureTransformer.setToScale(scale, scale);

            BufferedImage scaledDownImage = new BufferedImage(YouRobotSetting.getSize(), YouRobotSetting.getSize(), texture.getType());
            Graphics2D gd = (Graphics2D) scaledDownImage.getGraphics();
            gd.drawImage(texture, new AffineTransformOp(textureTransformer, AffineTransformOp.TYPE_BILINEAR), 0, 0);

            return scaledDownImage;
        } else {
            return texture;
        }
    }
}
