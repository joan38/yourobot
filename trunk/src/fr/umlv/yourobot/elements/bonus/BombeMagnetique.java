package fr.umlv.yourobot.elements.bonus;

import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.TypeElementBase;
import java.awt.image.BufferedImage;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

/**
 * Represent a BombeMagnetique bonus.
 * 
 * License: GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class BombeMagnetique extends Bonus {

    // JBox2D.
    private final CircleShape dynamicCircle;

    /**
     * Create a BombeMagnetique.
     * @param typeElement Type of the bomb.
     * @param texture Texture to use.
     * @param x X position of the bomb.
     * @param y Y position of the bomb.
     */
    public BombeMagnetique(TypeElementBase typeElement, BufferedImage texture, int x, int y) {
        super(typeElement, texture, 0, x, y);

        this.dynamicCircle = new CircleShape();
        this.dynamicCircle.m_radius = (float) Settings.getSize() / 2.0f;

        getFixtureDef().shape = dynamicCircle;
    }

    @Override
    public boolean stepBonus() {
        // BombeMagnetiqueEffect
        AABB area = new AABB(new Vec2(getRobot().getX() - Settings.getEffectArea(), getRobot().getY() - Settings.getEffectArea()),
                new Vec2(getRobot().getX() + Settings.getEffectArea(), getRobot().getY() + Settings.getEffectArea()));

        getRobot().getBody().getWorld().queryAABB(new QueryCallback() {

            @Override
            public boolean reportFixture(Fixture fixture) {
                Element e = (Element) fixture.getBody().getUserData();
                if (e == null || e == getRobot()) {
                    // Unknown element or the robot the throw the bomb, I do not do anything.
                    return true;
                }

                float angle = org.jbox2d.common.MathUtils.atan2(fixture.getBody().getPosition().y - getRobot().getBody().getPosition().y, fixture.getBody().getPosition().x - getRobot().getBody().getPosition().x);
                float force;
                if (((Element) fixture.getBody().getUserData()).getTypeElement() == getTypeElement()) {
                    force = 1000000.0f;
                } else {
                    force = 10000.0f;
                }

                fixture.getBody().applyLinearImpulse(new Vec2((float) Math.cos(angle) * force, (float) Math.sin(angle) * force), fixture.getBody().getWorldCenter());
                return true;
            }
        }, area);
        
        return false;
    }
    
}
