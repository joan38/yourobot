package fr.umlv.yourobot.elements.robot;

import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.area.Area;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.bonus.Leurre;
import java.awt.image.BufferedImage;
import java.util.Random;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

/**
 * Represent a Robot IA.
 * 
 * License: GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class RobotIA extends Robot {

    private boolean isTracking;
    private float speed = 0.05f;
    
    /**
     * Create a robot IA.
     * @param texture Texture of the robot.
     * @param x X position of the robot.
     * @param y Y position of the robot.
     */
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
                if (o == null) {
                    return true;
                }
                if (!(o instanceof RobotPlayer) && !(o instanceof Leurre)) {
                    return true;
                }

                // Checking if the target is dead.
                if (o instanceof RobotPlayer) {
                    if (((RobotPlayer) o).isDead()) {
                        return true;
                    }
                } else if (o instanceof Leurre) {
                    if (((Bonus) o).getState() != Bonus.BonusState.Activated) {
                        return true;
                    }
                }
                // Ok, we are now only targetting RobotPlayer and Leurre.

                // Getting the distance with this object.
                double distance = Math.sqrt(Math.pow(fixture.getBody().getWorldCenter().x - getBody().getWorldCenter().x, 2)
                        + Math.pow(fixture.getBody().getWorldCenter().y - getBody().getWorldCenter().y, 2));

                if (distance < maxDistance || (o instanceof Leurre && !(target instanceof Leurre))) {
                    // Ok, this element is interesting.
                    // Checking that we can raycast it without an element between.   
                    RayCallback rayCallback = new RayCallback(o);

                    world.raycast(rayCallback, robot.getBody().getWorldCenter(),
                            fixture.getBody().getWorldCenter());

                    if (rayCallback.isValidRayCast == true) {
                        if (!(target instanceof Leurre && fixture.getBody().getUserData() instanceof Robot)) {
                            // Marking this object as the most interesting one.
                            maxDistance = distance;
                            target = fixture.getBody().getUserData();
                        }
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
                    if (o != target && !(o instanceof Bonus || o instanceof Area || o instanceof RobotIA)) {
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
        AABB area = new AABB(new Vec2(getX() - Settings.getDetectionArea(), getY() - Settings.getDetectionArea()),
                new Vec2(getX() + Settings.getDetectionArea(), getY() + Settings.getDetectionArea()));

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

            getBody().applyLinearImpulse(new Vec2((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed), B);

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

    /**
     * Get the speed of the IA Robot.
     * @return The speed.
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Set the speed of the IA Robot.
     * @param speed The new speed.
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
