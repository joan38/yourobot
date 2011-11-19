package fr.umlv.yourobot.field;

import fr.umlv.yourobot.YouRobotSetting;
import java.awt.image.BufferedImage;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;

/**
 * Wall class.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Wall extends Element {

    // JBox2D.
    private final PolygonShape dynamicBox;

    public Wall(TypeElementBase typeElement, BufferedImage texture, int x, int y) {
        super(typeElement, texture, x, y);
        
        // Setting JBox2D.
        getBodyDef().type = BodyType.DYNAMIC; // A wall is dynamic.

        this.dynamicBox = new PolygonShape();
        this.dynamicBox.setAsBox(YouRobotSetting.getSize() / 2.0f, YouRobotSetting.getSize() / 2.0f);
        
        getFixtureDef().shape = dynamicBox;
        getFixtureDef().density = 500.0f;
        getFixtureDef().friction = 1.0f;
    }
}
