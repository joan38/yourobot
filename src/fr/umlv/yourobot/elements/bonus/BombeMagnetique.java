package fr.umlv.yourobot.elements.bonus;

import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robot.Robot;
import fr.umlv.yourobot.elements.TypeElementBase;
import fr.umlv.yourobot.elements.World;
import java.awt.image.BufferedImage;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

/**
 * Represent a BombeMagnetique bonus.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class BombeMagnetique extends Bonus {

    // JBox2D.
    private final CircleShape dynamicCircle;

    public BombeMagnetique(TypeElementBase element, BufferedImage texture, int x, int y) {
        super(element, texture, x, y);

        this.dynamicCircle = new CircleShape();
        this.dynamicCircle.m_radius = (float) YouRobotSetting.getSize() / 2.0f;

        getFixtureDef().shape = dynamicCircle;
    }

    @Override
    public void activateBonus(final Robot robot, final World world) {
        // BombeMagnetiqueEffect
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
                    force = 1000000.0f;
                } else {
                    force = 10000.0f;
                }

                fixture.getBody().applyLinearImpulse(new Vec2((float) Math.cos(angle) * force, (float) Math.sin(angle) * force), fixture.getBody().getWorldCenter());
                return true;
            }
        }, area);

    }
}
