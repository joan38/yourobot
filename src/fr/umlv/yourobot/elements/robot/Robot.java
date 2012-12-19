package fr.umlv.yourobot.elements.robot;

import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.TypeElementBase;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

/**
 * Represent a Robot.
 *
 * License: GNU Public license v3.
 *
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public abstract class Robot extends Element {

    private boolean isBoosting = false;
    private boolean isBraking = false;
    private BufferedImage textureBoost;
    private BufferedImage textureBrake;
    // JBox2D.
    private final CircleShape dynamicCircle;

    /**
     * Create a robot.
     *
     * @param texture Standard texture to use.
     * @param textureBoost Texture to use when the robot is boosting.
     * @param textureBrake Texture to use when the robot is braking.
     * @param x X position of the robot.
     * @param y Y position of the robot.
     */
    public Robot(BufferedImage texture, BufferedImage textureBoost, BufferedImage textureBrake, int x, int y) {
        super(TypeElementBase.Robot, texture, x, y);

        this.textureBoost = textureBoost;
        this.textureBrake = textureBrake;

        // Init of JBox2D.
        getBodyDef().type = BodyType.DYNAMIC; // A robot is dynamic.

        this.dynamicCircle = new CircleShape();
        this.dynamicCircle.m_radius = (float) Settings.getSize() / 2.0f;

        getFixtureDef().shape = dynamicCircle;
        getFixtureDef().density = 0.0001f;
        getFixtureDef().friction = 0.1f;
        getFixtureDef().restitution = 0.3f;

        getBodyDef().linearDamping = 1.0f;
        getBodyDef().linearVelocity = new Vec2(0.1f, 0.1f);

        getBodyDef().angularDamping = 8.0f;
        getBodyDef().angularVelocity = 2.0f;
    }

    @Override
    public void render(Graphics2D gd) {
        if (getBody() == null) { // No JBox2D body, no drawing so.
            return;
        }
        if (isBoosting == true) {
            super.render(gd, textureBoost);
        } else if (isBraking == true) {
            super.render(gd, textureBrake);
        } else {
            super.render(gd);
        }
    }

    /**
     * Does the robot is boosting ?
     *
     * @return True or false.
     */
    public boolean isIsBoosting() {
        return isBoosting;
    }

    /**
     * Returns the boost force.
     * 
     * @return The boost force. (5)
     */
    public float getBoostLevel() {
        return 5;
    }

    /**
     * Set the boost of the robot.
     *
     * @param isBoosting true to boost, false to stop.
     */
    public void setIsBoosting(boolean isBoosting) {
        this.isBoosting = isBoosting;
        if (isBoosting == true && getBody() != null) {

            float forceX = (float) (getBoostLevel() * Math.cos(getBody().getAngle()));
            float forceY = (float) (getBoostLevel() * Math.sin(getBody().getAngle()));

            getBody().applyForce(new Vec2(forceX, forceY), getBody().getWorldCenter());
        }
    }

    /**
     * Is the robot braking ?
     *
     * @return true if braking, false otherwise.
     */
    public boolean isIsBraking() {
        return isBraking;
    }

    /**
     * Set the robot braking.
     *
     * @param isBraking true or false.
     *
     * Note: Unused in version 0.1.
     */
    public void setIsBraking(boolean isBraking) {
        this.isBraking = isBraking;
    }

    /**
     * Turns the robot left.
     */
    public void turnLeft() {
        getBody().setAngularVelocity(-6.0f);
    }

    /**
     * Turns the robot right.
     */
    public void turnRight() {
        getBody().setAngularVelocity(6.0f);
    }
}
