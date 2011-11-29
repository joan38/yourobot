package fr.umlv.yourobot.elements.bonus;

import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.TypeElementBase;
import fr.umlv.yourobot.elements.robot.RobotIA;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

/**
 * Represent a Snap bonus.
 * 
 * A Snap bring all elements behinds the robot.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Snap extends Bonus {

    private final CircleShape dynamicCircle;

    /**
     * Create a Snap.
     * @param typeElement Type of the snap.
     * @param texture Texture to use.
     * @param x X position of the bonus.
     * @param y Y position of the bonus.
     */
    public Snap(TypeElementBase typeElement, BufferedImage texture, int durationOfBonusInSeconds, int x, int y) {
        super(typeElement, texture, durationOfBonusInSeconds, x, y);

        this.dynamicCircle = new CircleShape();
        this.dynamicCircle.m_radius = (float) Settings.getSize() / 2.0f;

        getFixtureDef().shape = dynamicCircle;
    }

    @Override
    public boolean stepBonus() {
        // SnapEffect
        if (((Calendar.getInstance().getTimeInMillis() - getBonusActivationDate())) > durationOfBonusInSeconds * 1000) {
            return false; // End of the effect.
        }

        AABB area = new AABB(new Vec2(getRobot().getX() - Settings.getEffectArea(), getRobot().getY() - Settings.getEffectArea()),
                new Vec2(getRobot().getX() + Settings.getEffectArea(), getRobot().getY() + Settings.getEffectArea()));

        getRobot().getBody().getWorld().queryAABB(new QueryCallback() {

            @Override
            public boolean reportFixture(Fixture fixture) {
                Element e = (Element) fixture.getBody().getUserData();
                if (e == null || e == getRobot() || e instanceof RobotIA) {
                    // Unknown element or the robot the throw the bomb, I do not do anything.
                    return true;
                }

                float angle = org.jbox2d.common.MathUtils.atan2(fixture.getBody().getPosition().y - getRobot().getBody().getPosition().y, fixture.getBody().getPosition().x - getRobot().getBody().getPosition().x);
                float force;
                if (((Element) fixture.getBody().getUserData()).getTypeElement() == getTypeElement()) {
                    force = -1000.0f;
                } else {
                    force = -500.0f;
                }

                // Elements are attracted at the rear of the robot. (prevent blocking)
                Vec2 rearOfRobot = fixture.getBody().getWorldCenter().sub(new Vec2((float) ((Settings.getSize() * 2) * Math.cos(angle)),
                        (float) ((Settings.getSize() * 2) * Math.sin(angle))));

                fixture.getBody().applyLinearImpulse(new Vec2((float) Math.cos(angle) * force, (float) Math.sin(angle) * force), rearOfRobot);
                return true;
            }
        }, area);

        return true;
    }
}
