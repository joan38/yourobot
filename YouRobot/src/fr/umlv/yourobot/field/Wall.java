package fr.umlv.yourobot.field;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Wall class.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Wall extends Element {

    public Wall(TypeElementBase typeElement, BufferedImage texture) {
        super(typeElement, texture);
    }

    public Wall(TypeElementBase typeElement, BufferedImage texture, int x, int y) {
        super(typeElement, texture, x, y);
    }
}
