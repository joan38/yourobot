package fr.umlv.yourobot.elements.bonus;

import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.TypeElementBase;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

public class Snap extends Bonus {

    private final CircleShape dynamicCircle;

    public Snap(TypeElementBase typeElement, BufferedImage texture, int durationOfBonusInSeconds, int x, int y) {
        super(typeElement, texture, durationOfBonusInSeconds, x, y);

        this.dynamicCircle = new CircleShape();
        this.dynamicCircle.m_radius = (float) YouRobotSetting.getSize() / 2.0f;

        getFixtureDef().shape = dynamicCircle;
    }

    @Override
    public boolean stepBonus() {
        // BombeMagnetiqueEffect
        if (((Calendar.getInstance().getTimeInMillis() - bonusActivationDate)) > durationOfBonusInSeconds * 1000) {
            return false; // End of the effect.
        }

        AABB area = new AABB(new Vec2(robot.getX() - YouRobotSetting.getStride(), robot.getY() - YouRobotSetting.getStride()),
                new Vec2(robot.getX() + YouRobotSetting.getStride(), robot.getY() + YouRobotSetting.getStride()));

        world.getjbox2DWorld().queryAABB(new QueryCallback() {

            @Override
            public boolean reportFixture(Fixture fixture) {
                Element e = (Element) fixture.getBody().getUserData();
                if (e == null || e == robot) {
                    // Unknown element or the robot the throw the bomb, I do not do anything.
                    return true;
                }

                float angle = org.jbox2d.common.MathUtils.atan2(fixture.getBody().getPosition().y - robot.getBody().getPosition().y, fixture.getBody().getPosition().x - robot.getBody().getPosition().x);
                float force;
                if (((Element) fixture.getBody().getUserData()).getTypeElement() == getTypeElement()) {
                    force = -1000.0f;
                } else {
                    force = -100.0f;
                }
                
                // Elements are attracted at the rear of the robot. (prevent blocking)
                Vec2 rearOfRobot = fixture.getBody().getWorldCenter().sub(new Vec2((float)((YouRobotSetting.getSize() * 2) *  Math.cos(angle)), 
                        (float)((YouRobotSetting.getSize() * 2) *  Math.sin(angle))));

                fixture.getBody().applyLinearImpulse(new Vec2((float) Math.cos(angle) * force, (float) Math.sin(angle) * force), rearOfRobot);
                return true;
            }
        }, area);

        return true;
    }
}
