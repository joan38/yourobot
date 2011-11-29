package fr.umlv.yourobot.elements.area;

import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.TypeElementBase;
import java.awt.image.BufferedImage;

/**
 * Represent an area.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public abstract class Area extends Element {

    /**
     * Define a game area.
     * @param texture Texture of the area.
     * @param x X position of the area.
     * @param y Y position of the area.
     */
    public Area(BufferedImage texture, int x, int y) {
        super(TypeElementBase.Unasigned, texture, x, y);
    }
}
