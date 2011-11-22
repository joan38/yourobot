package fr.umlv.yourobot.elements;

import fr.umlv.yourobot.elements.*;
import java.awt.image.BufferedImage;

/**
 * Represent a bonus.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public abstract class Bonus extends Element {

    /**
     * A Bonus.
     * 
     * @param typeElement Type of the element.
     * @param texture Texture of the bonus.
     * @param x Initial x position of the bonus.
     * @param y Initial y position of the bonus.
     */
    public Bonus(TypeElementBase typeElement, BufferedImage texture, int x, int y) {
        super(typeElement, texture, x, y);
    }

    /**
     * Activate the bonus.
     * 
     * @param robot Robot that activate the bonus.
     * @param world World of the bonus.
     */
    public abstract void activateBonus(Robot robot, World world);
}
