package fr.umlv.yourobot.elements.robot;

import AreaCallback.RayCallback;
import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Leurre;
import java.awt.image.BufferedImage;
import java.util.Random;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

public class RobotIA extends Robot {

    private boolean isTracking;

    public RobotIA(BufferedImage texture, BufferedImage textureBoost, BufferedImage textureBrake, int x, int y) {
        super(texture, textureBoost, textureBrake, x, y);
    }

    /**
     * Do an IA movement.
     */
    public void stepIA() {
        // Querying a target.
        final RobotIA mySelf = this;
        final org.jbox2d.dynamics.World myWorld = getBody().getWorld();
        isTracking = false;

        AABB area = new AABB(new Vec2(getX() - YouRobotSetting.getStride(), getY() - YouRobotSetting.getStride()),
                new Vec2(getX() + YouRobotSetting.getStride(), getY() + YouRobotSetting.getStride()));

        // Internal utility class. QueryCallback
        class AreaCallback implements QueryCallback {

            private double maxDistance = Double.MAX_VALUE;

            @Override
            public boolean reportFixture(Fixture fixture) {
                Element e = (Element) fixture.getBody().getUserData();
                if (e == null || !(e instanceof RobotPlayer || e instanceof Leurre)) {
                    return true;
                }
                // TODO Manage the dead robot.

                // Getting the distance with this object.
                double distance = Math.sqrt(Math.pow(fixture.getBody().getWorldCenter().x - getBody().getWorldCenter().x, 2)
                        - Math.pow(fixture.getBody().getWorldCenter().y - getBody().getWorldCenter().y, 2));

                if (distance < maxDistance) {
                    // Ok, this element is interesting.
                    // Checking that we can raycast it without an element between.   
                    myWorld.raycast(new RayCallback(), mySelf.getBody().getWorldCenter(),
                            fixture.getBody().getWorldCenter());
                }

                return true;

            }

            // Internal utility class (of an internal utility class).
            class RayCallback implements RayCastCallback {

                @Override
                public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
                    Element e = (Element) fixture.getBody().getUserData();
                    // Checking if somebody is between the RobotIA and the detected body.
                    // If there is something, I ignore the element.
                    // Otherwise I mark it as possible element to track.
                    
                    return 1;
                }
            }
        }

        // No movement, simple iteration.

        // Random movement.
        if (isTracking == false) {
            Random rand = new Random();
            switch (rand.nextInt(3)) {
                case 0:
                    super.setIsBoosting(true);
                    break;
                case 1:
                    super.turnLeft();
                    break;
                case 2:
                    super.turnRight();
                    break;
            }
        }
    }
}
