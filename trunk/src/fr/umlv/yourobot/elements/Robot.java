package fr.umlv.yourobot.elements;

import fr.umlv.yourobot.YouRobotSetting;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

public abstract class Robot extends Element {

    private boolean isBoosting = false;
    private boolean isBraking = false;
    private BufferedImage textureBoost;
    private BufferedImage textureBrake;
    // JBox2D.
    private final CircleShape dynamicCircle;

    public Robot(BufferedImage texture, BufferedImage textureBoost, BufferedImage textureBrake, int x, int y) {
        super(TypeElementBase.Robot, texture, x, y);

        this.textureBoost = textureBoost;
        this.textureBrake = textureBrake;

        // Init of JBox2D.
        bodyDef.type = BodyType.DYNAMIC; // A robot is dynamic.

        this.dynamicCircle = new CircleShape();
        this.dynamicCircle.m_radius = (float) YouRobotSetting.getSize() / 2.0f;

        fixtureDef.shape = dynamicCircle;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.3f;

        bodyDef.linearDamping = 1.0f;
        bodyDef.linearVelocity = new Vec2(0.1f, 0.1f);
        
        bodyDef.angularDamping = 8.0f;
        bodyDef.angularVelocity = 2.0f;
    }
    /**
     * Render the element on the graphic gd.
     * This method simply draw the texture.
     * 
     * @param gd Graphic to draw on.
     */
    private AffineTransform textTureTransform = new AffineTransform();

    @Override
    public void render(Graphics2D gd) {
        textTureTransform.setToIdentity();
        this.textTureTransform.rotate(body.getAngle(), texture.getWidth() / 2.0, texture.getHeight() / 2.0);

        if (isBoosting == true) {
            gd.drawImage(textureBoost, new AffineTransformOp(textTureTransform, AffineTransformOp.TYPE_BILINEAR), getX(), getY());
        } else if (isBraking == true) {
            gd.drawImage(textureBrake, new AffineTransformOp(textTureTransform, AffineTransformOp.TYPE_BILINEAR), getX(), getY());
        } else {
            gd.drawImage(texture, new AffineTransformOp(textTureTransform, AffineTransformOp.TYPE_BILINEAR), getX(), getY());
        }
    }

    public boolean isIsBoosting() {
        return isBoosting;
    }

    public void setIsBoosting(boolean isBoosting) {
        this.isBoosting = isBoosting;
        if (isBoosting == true && body != null) {
            float forceX = (float)(1000000 * Math.sin(body.getAngle()));
            float forceY = (float)(-1000000 * Math.cos(body.getAngle()));
            
            body.applyForce(new Vec2(forceX, forceY), body.getWorldCenter());
        }
    }

    public boolean isIsBraking() {
        return isBraking;
    }

    public void setIsBraking(boolean isBraking) {
        this.isBraking = isBraking;
    }

    public void turnLeft() {
        body.setAngularVelocity(-10.0f);
    }

    public void turnRight() {
        body.setAngularVelocity(10.0f);
    }
}
