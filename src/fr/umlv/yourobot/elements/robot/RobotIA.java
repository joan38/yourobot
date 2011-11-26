package fr.umlv.yourobot.elements.robot;

import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.bonus.Bonus;
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

    public RobotIA(BufferedImage texture, int x, int y) {
        super(texture, texture, texture, x, y);
    }

    /**
     * Do an IA movement.
     */
    public void stepIA() {
        // Querying a target.

        // Internal utility class. QueryCallback
        //
        // Used to query a valid target for the RobotIA.
        //
        class AreaCallback implements QueryCallback {

            private double maxDistance = Double.MAX_VALUE;
            private Object target = null;
            private final RobotIA robot;
            private final org.jbox2d.dynamics.World world;

            public AreaCallback(RobotIA robot, org.jbox2d.dynamics.World world) {
                this.robot = robot;
                this.world = world;
            }

            @Override
            public boolean reportFixture(Fixture fixture) {
                Object o = fixture.getBody().getUserData();
                if (o == null || !(o instanceof RobotPlayer || o instanceof Leurre)) {
                    return true;
                }
                // Checking if the target is dead.
                if (o instanceof RobotPlayer) {
                    if (((RobotPlayer) o).isDead()) {
                        return true;
                    }
                } else if (o instanceof Leurre) {
                    if (!((Bonus) o).isActivated())
                        return true;
                }

                // Ok, we are now only targetting RobotPlayer and Leurre.

                // Getting the distance with this object.
                double distance = Math.sqrt(Math.pow(fixture.getBody().getWorldCenter().x - getBody().getWorldCenter().x, 2)
                        + Math.pow(fixture.getBody().getWorldCenter().y - getBody().getWorldCenter().y, 2));

                if (distance < maxDistance) {
                    // Ok, this element is interesting.
                    // Checking that we can raycast it without an element between.   
                    RayCallback rayCallback = new RayCallback(o);

                    world.raycast(rayCallback, robot.getBody().getWorldCenter(),
                            fixture.getBody().getWorldCenter());

                    if (rayCallback.isValidRayCast == true) {
                        // Marking this object as the most interesting one.
                        maxDistance = distance;
                        target = fixture.getBody().getUserData();
                    }
                }

                return true;
            }

            // Internal utility class (of an internal utility class).
            class RayCallback implements RayCastCallback {

                private final Object target;
                private boolean isValidRayCast = true;

                public RayCallback(Object target) {
                    this.target = target;
                }

                @Override
                public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
                    Object o = fixture.getBody().getUserData();
                    // Checking if somebody is between the RobotIA and the detected body.
                    // If there is something, I ignore the element.
                    // Otherwise I mark it as possible element to track.
                    if (o != target && !(o instanceof Bonus)) {
                        isValidRayCast = false;
                        return -1; // An object is between the robotIA and the targetted element.
                    }

                    // Not found, continue.
                    return 1;
                }
            }
        }

        // Core code is here !
        //
        AABB area = new AABB(new Vec2(getX() - YouRobotSetting.getStride(), getY() - YouRobotSetting.getStride()),
                new Vec2(getX() + YouRobotSetting.getStride(), getY() + YouRobotSetting.getStride()));

        // Querying a target.
        AreaCallback areaCallback = new AreaCallback(this, getBody().getWorld());
        getBody().getWorld().queryAABB(areaCallback, area);
        // Checking if there is a target.
        if (areaCallback.target != null) {
            // A target has been found !
            // Tracking it.
            Vec2 A = getBody().getWorldCenter();
            Vec2 B = ((Element) areaCallback.target).getBody().getWorldCenter();

            float angle = org.jbox2d.common.MathUtils.atan2(B.y - A.y, B.x - A.x);
            float force = 0.05f;

            getBody().applyLinearImpulse(new Vec2((float) Math.cos(angle) * force, (float) Math.sin(angle) * force), B);

        } else {
            // No target, random movement.
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
}
