package fr.umlv.yourobot.field;

import java.awt.Graphics2D;

public interface Element {

    /**
     * Render the element on the graphic gd.
     */
    public void render(Graphics2D gd);
    
    /**
     * Define the orientation of the element.
     */
    public void setOrientation(int degree);
}
