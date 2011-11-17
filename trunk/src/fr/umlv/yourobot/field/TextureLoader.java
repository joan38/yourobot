package fr.umlv.yourobot.field;

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
     * 
     * @note A texture must be a square image.
     * 
     * @return The image of the texture.
     */
    public static BufferedImage loadTexture(String path) throws IOException {
        if (textures.containsKey(path)) {
            return textures.get(path);
        }

        BufferedImage result = ImageIO.read(new File(path));
        if (result.getWidth() != result.getHeight())
            throw new IOException("Invalid texture format. The texture is not a square.");
        
        return result;
    }
}
