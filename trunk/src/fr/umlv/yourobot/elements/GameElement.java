package fr.umlv.yourobot.elements;

import java.awt.Graphics2D;

/**
 * Represent a drawable element.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public interface GameElement {

    /**
     * Render the element on the graphic gd.
     * This method simply draw the texture.
     * 
     * @param gd Graphic to draw on.
     */
    public void render(Graphics2D gd);
    
    
    
    
}

