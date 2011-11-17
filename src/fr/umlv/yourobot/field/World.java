package fr.umlv.yourobot.field;

import fr.umlv.yourobot.YouRobotSetting;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Manage the logic of the application.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class World {

    private Area startArea;
    private Area endArea;
    private final LinkedList<Element> elements; // Elements of the game.
    private BufferedImage backgroundPattern;

    /**
     * Empty world.
     */
    public World(BufferedImage backgroundPattern) {
        Objects.requireNonNull(backgroundPattern);
        
        elements = new LinkedList<Element>();
        this.backgroundPattern = backgroundPattern;
    }

    /**
     * Render a world.
     * 
     * @param gd World to render.
     */
    public void render(Graphics2D gd) {
        // Drawing the background.
        if (backgroundPattern != null) {
            // An image is set.
            for (int i = 0; i < YouRobotSetting.getWidth(); i += backgroundPattern.getWidth()) {
                for (int j = 0; j < YouRobotSetting.getHeight(); j += backgroundPattern.getHeight()) {
                    gd.drawImage(backgroundPattern, i, j, null);
                }
            }
        } else {
            // Draw static background.
        }


        // Drawing elements.
        for (Element e : elements) {
            e.render(gd);
        }

        // Ok.
    }

    /**
     * Set the background pattern element.
     * @param image Image to set.
     */
    public void setBackgroundPattern(BufferedImage image) {
        this.backgroundPattern = image;
    }

    /**
     * Add an element into the world.
     * 
     * @param e Element to add.
     */
    public void addElement(Element e) {
        elements.add(e);
    }

    /**
     * Remove an element from the world.
     * 
     * @param e Element to remove.
     */
    public void removeElement(Element e) {
        elements.remove(e);
    }

    /**
     * Fill the given world with an unbreakable tube around.
     * 
     * @param w World to fill.
     * @param texturePath Path of the texture to use.
     */
    public static void fillBorder(World w, String texturePath) throws IOException {
        BufferedImage tubeTexture = TextureLoader.loadTexture(texturePath);

        // Vertical.
        int rightPos = YouRobotSetting.getWidth() - YouRobotSetting.getSize();
        Element e;
        for (int i = 0; i < YouRobotSetting.getHeight(); i += YouRobotSetting.getSize()) {
            e = new Barres(TypeElementBase.Unasigned, tubeTexture, 0, i);
            e.setOrientation(90);
            w.addElement(e);

            e = new Barres(TypeElementBase.Unasigned, tubeTexture, rightPos, i);
            e.setOrientation(90);
            w.addElement(e);
        }

        // Horizontal.
        int lowerPos = YouRobotSetting.getHeight() - YouRobotSetting.getSize();
        for (int i = 0; i < YouRobotSetting.getWidth(); i += YouRobotSetting.getSize()) {
            w.addElement(new Barres(TypeElementBase.Unasigned, tubeTexture, i, 0));
            w.addElement(new Barres(TypeElementBase.Unasigned, tubeTexture, i, lowerPos));
        }
    }
}
