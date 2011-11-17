package fr.umlv.yourobot.field;

import fr.umlv.yourobot.YouRobotSetting;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
    public World() {
        elements = new LinkedList<Element>();
    }

    /**
     * 
     */
    public World(LinkedList<Element> elements) {
        Objects.requireNonNull(elements);

        this.elements = elements;
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
}
