package fr.umlv.yourobot.field;

import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 * Manage the logic of the application.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class World {
    
    protected Area startArea;
    protected Area endArea;
    protected final LinkedList<Element> elements = new LinkedList<Element>(); // Elements of the game.

    public void render(Graphics2D gd) {
        // Drawing the background.
        // TODO

        // Drawing elements.
        for (Element e : elements) {
            e.render(gd);
        }
        
        // Ok.
    }
}
