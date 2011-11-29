package fr.umlv.yourobot.elements.wall;

import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.TypeElementBase;
import java.awt.image.BufferedImage;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;

/**
 * Represent a Barre.
 * 
 * Static element of the game.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Barres extends Element {
   
    // JBox2D.
    private final PolygonShape dynamicBox;

    /**
     * Creates a barre.
     * 
     * @param texture Texture of the element.
     * @param x X position of the element.
     * @param y Y position of the element.
     */
    public Barres(BufferedImage texture, int x, int y) {
        super(TypeElementBase.Unasigned, texture, x, y);
        
        // Setting JBox2D.
        getBodyDef().type = BodyType.STATIC; // A wall is dynamic.

        this.dynamicBox = new PolygonShape();
        this.dynamicBox.setAsBox(Settings.getSize() / 2.0f, Settings.getSize() / 2.0f);
        
        getFixtureDef().shape = dynamicBox;
        getFixtureDef().density = 1.0f;
        getFixtureDef().friction = 1.0f;
    }

}
