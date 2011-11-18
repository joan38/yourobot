package fr.umlv.yourobot.field;

import fr.umlv.yourobot.YouRobotSetting;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Wall class.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Wall extends Element {

    // JBox2D.
    private final BodyDef bodyDef;
    private final PolygonShape dynamicBox;
    private final FixtureDef fixtureDef;
    private Body body;

    public Wall(TypeElementBase typeElement, BufferedImage texture) {
        this(typeElement, texture, 0, 0);
    }

    public Wall(TypeElementBase typeElement, BufferedImage texture, int x, int y) {
        super(typeElement, texture, x, y);

        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyType.DYNAMIC; // A wall is dynamic.
        this.bodyDef.position.set(x, y); // Setting the position.

        this.dynamicBox = new PolygonShape();
        this.dynamicBox.setAsBox(YouRobotSetting.getSize(), YouRobotSetting.getSize());

        this.fixtureDef = new FixtureDef();
        this.fixtureDef.shape = dynamicBox;
        this.fixtureDef.density = 1.0f;
        this.fixtureDef.friction = 1.0f;
    }

    // Positions
    //
     @Override
    public int getX() {
        if (body == null) {
            return super.getX();
        }
        return (int) body.getPosition().x;
    }

    /**
     * Force the position.
     * @note Break all the physics.
     * @param x New x.
     */
    @Override
    public void setX(int x) {
        if (body == null) {
            super.setX(x);
        } else {
            body.setTransform(new Vec2(x, getY()), body.getAngle());
        }
    }

    @Override
    public int getY() {
        if (body == null) {
            return super.getY();
        }
        return (int) body.getPosition().y;
    }

    /**
     * Force the position.
     * @note Break all the physics.
     * @param y New y.
     */
    @Override
    public void setY(int y) {
        if (body == null) {
            super.setY(y);
        } else {
            body.setTransform(new Vec2(getX(), y), body.getAngle());
        }
    }

    // JBox2D Register and Unregister
    //
    @Override
    public void jboxBodyInit(World w) {
        body = w.createBody(bodyDef);
        body.createFixture(fixtureDef);
    }

    @Override
    public void jboxBodyDestroy(World w) {
        w.destroyBody(body);
    }

    @Override
    public void setOrientation(int degree) {
        super.setOrientation(degree);

        this.dynamicBox.setAsBox(1.0f, 1.0f, new Vec2(0.5f, 0.5f), (float) Math.toRadians(degree));
    }
}
