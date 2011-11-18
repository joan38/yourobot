package fr.umlv.yourobot.players;

import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.field.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public abstract class Robot extends Element {

    private boolean isBoosting = false;
    private boolean isBraking = false;
    private BufferedImage textureBoost;
    private BufferedImage textureBrake;
    // JBox2D.
    private final BodyDef bodyDef;
    private final CircleShape dynamicCircle;
    private final FixtureDef fixtureDef;
    private Body body;

    public Robot(BufferedImage texture, BufferedImage textureBoost, BufferedImage textureBrake) {
        super(TypeElementBase.Robot, texture);

        this.textureBoost = textureBoost;
        this.textureBrake = textureBrake;

        this.bodyDef = new BodyDef();
        this.bodyDef.type = BodyType.DYNAMIC; // A robot is dynamic.
        this.bodyDef.position.set(0.0f, 0.0f); // Setting the position.

        this.dynamicCircle = new CircleShape();
        this.dynamicCircle.m_radius = (float) YouRobotSetting.getSize() / 2.0f;

        this.fixtureDef = new FixtureDef();
        this.fixtureDef.shape = dynamicCircle;
        this.fixtureDef.density = 1.0f;
        this.fixtureDef.friction = 1.0f;
    }

    /**
     * Render the element on the graphic gd.
     * This method simply draw the texture.
     * 
     * @param gd Graphic to draw on.
     */
    @Override
    public void render(Graphics2D gd) {
        if (isBoosting == true) {
            gd.drawImage(textureBoost, super.getBufferedTextureTransformerOp(), getX(), getY());
        } else if (isBraking == true) {
            gd.drawImage(textureBrake, super.getBufferedTextureTransformerOp(), getX(), getY());
        } else {
            super.render(gd);
        }
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

    @Override
    public void setOrientation(int degree) {
        super.setOrientation(degree);
        
        this.body.setTransform(body.getPosition(), (float)Math.toRadians(degree));
    }

    public boolean isIsBoosting() {
        return isBoosting;
    }

    public void setIsBoosting(boolean isBoosting) {
        this.isBoosting = isBoosting;
        if (body != null) {
            body.applyForce(new Vec2(10.0f, 10.0f), body.getLocalCenter());
        }
    }

    public boolean isIsBraking() {
        return isBraking;
    }

    public void setIsBraking(boolean isBraking) {
        this.isBraking = isBraking;
    }

    // JBox2D Register and Unregister
    //
    @Override
    public void jboxBodyInit(org.jbox2d.dynamics.World w) {
        body = w.createBody(bodyDef);
        body.createFixture(fixtureDef);
    }

    @Override
    public void jboxBodyDestroy(org.jbox2d.dynamics.World w) {
        w.destroyBody(body);
    }
}
