package fr.umlv.yourobot.elements.bonus;

import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.elements.robot.Robot;
import fr.umlv.yourobot.elements.TypeElementBase;
import fr.umlv.yourobot.elements.World;
import java.awt.image.BufferedImage;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

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
                float angle = org.jbox2d.common.MathUtils.atan2(fixture.getBody().getPosition().y - robot.getBody().getPosition().y, fixture.getBody().getPosition().x - robot.getBody().getPosition().x);
                float force = 1000000.0f;
                
                fixture.getBody().applyLinearImpulse(new Vec2((float)Math.cos(angle) * force, (float)Math.sin(angle) * force), fixture.getBody().getWorldCenter());
                
                //fixture.getBody().applyAngularImpulse(10000000.0f);
                return true;
            }
        }, area);

    }
}
