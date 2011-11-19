package fr.umlv.yourobot.players;

import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.field.*;
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
        getBodyDef().type = BodyType.DYNAMIC; // A robot is dynamic.

        this.dynamicCircle = new CircleShape();
        this.dynamicCircle.m_radius = (float) YouRobotSetting.getSize() / 2.0f;

        getFixtureDef().shape = dynamicCircle;
        getFixtureDef().density = 0.5f;
        getFixtureDef().friction = 0.0f;

        getBodyDef().linearDamping = 1.0f;
        getBodyDef().linearVelocity = new Vec2(1.0f, 1.0f);
        
        getBodyDef().angularDamping = 8.0f;
        getBodyDef().angularVelocity = 2.0f;
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
        this.textTureTransform.setToScale(scale, scale);
        this.textTureTransform.rotate(getBody().getAngle(), getTexture().getWidth() / 2.0, getTexture().getHeight() / 2.0);

        if (isBoosting == true) {
            gd.drawImage(textureBoost, new AffineTransformOp(textTureTransform, AffineTransformOp.TYPE_BILINEAR), getX(), getY());
        } else if (isBraking == true) {
            gd.drawImage(textureBrake, new AffineTransformOp(textTureTransform, AffineTransformOp.TYPE_BILINEAR), getX(), getY());
        } else {
            gd.drawImage(super.getTexture(), new AffineTransformOp(textTureTransform, AffineTransformOp.TYPE_BILINEAR), getX(), getY());
        }
    }

    public boolean isIsBoosting() {
        return isBoosting;
    }

    public void setIsBoosting(boolean isBoosting) {
        this.isBoosting = isBoosting;
        if (isBoosting == true && getBody() != null) {
            float forceX = (float)(10000 * Math.sin(getBody().getAngle()));
            float forceY = (float)(-10000 * Math.cos(getBody().getAngle()));
            
            getBody().applyLinearImpulse(new Vec2(forceX, forceY), getBody().getWorldCenter());
        }
    }

    public boolean isIsBraking() {
        return isBraking;
    }

    public void setIsBraking(boolean isBraking) {
        this.isBraking = isBraking;
    }

    public void turnLeft() {
        getBody().setAngularVelocity(-10.0f);
    }

    public void turnRight() {
        getBody().setAngularVelocity(10.0f);
    }
}