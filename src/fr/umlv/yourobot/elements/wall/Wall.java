package fr.umlv.yourobot.elements.wall;

import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.TypeElementBase;
import java.awt.image.BufferedImage;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
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
        getBodyDef().type = BodyType.DYNAMIC; // A wall is static. Becomes dynamic with bonus.
        getBodyDef().linearDamping = 2.0f;
        getBodyDef().linearVelocity = new Vec2(1.0f, 1.0f);
        getBodyDef().angularDamping = 2.0f;
        getBodyDef().angularVelocity = 0.0f;

        this.dynamicBox = new PolygonShape();
        this.dynamicBox.setAsBox(Settings.getSize() / 2.0f, Settings.getSize() / 2.0f);

        getFixtureDef().shape = dynamicBox;
        getFixtureDef().density = 1.0f;
        getFixtureDef().friction = 1.0f;
    }
}
