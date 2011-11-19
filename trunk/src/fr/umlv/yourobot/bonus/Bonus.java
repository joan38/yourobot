package fr.umlv.yourobot.bonus;

import fr.umlv.yourobot.field.*;
import java.awt.image.BufferedImage;

/**
 * Représente un bonus.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public abstract class Bonus extends Element {

    public Bonus(TypeElementBase typeElement, BufferedImage texture, int x, int y) {
        super(typeElement, texture, x, y);
    }
    
}