package fr.umlv.yourobot.elements;

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
        bodyDef.type = BodyType.STATIC; // A wall is static. Becomes dynamic with bonus.
        bodyDef.linearDamping = 0.90f;

        this.dynamicBox = new PolygonShape();
        this.dynamicBox.setAsBox(YouRobotSetting.getSize() / 2.0f, YouRobotSetting.getSize() / 2.0f);
        
        fixtureDef.shape = dynamicBox;
        fixtureDef.density = 500.0f;
        fixtureDef.friction = 1.0f;
    }
}
