package fr.umlv.yourobot.context;

import fr.umlv.yourobot.Player;
import fr.umlv.yourobot.RobotKeyAction;
import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.elements.robot.Robot;
import fr.umlv.yourobot.elements.area.Area;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.*;
import fr.umlv.zen.*;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Objects;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

/**
 * Manage the logic of the application.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Game implements ApplicationCode, ApplicationRenderCode {

    private final Player[] players;
    private final World world; // World of the game.
    private BufferedImage bi; // Double buffer.

    public Game(World world, Player[] players) {
        //Objects.requireNonNull(players[0]);
        Objects.requireNonNull(world);

        this.world = world;
        this.players = players;

        this.bi = new BufferedImage(YouRobotSetting.getWidth(), YouRobotSetting.getHeight(), BufferedImage.TYPE_INT_RGB);

        this.world.addElement(players[0].getRobot());
        if (players[1] != null) {
            this.world.addElement(players[1].getRobot());
        }

        // Contact management.
        this.world.getjbox2DWorld().setContactListener(new RobotContactListener());
    }

    /**
     * Game logic here.
     * 
     * @param context Context.
     */
    @Override
    public void run(ApplicationContext context) {
        // JBox2D.
        final float timeStep = 1.0f / 60.0f;
        final int velocityIteration = 6;
        final int positioniteration = 2;
        int counter = 0;

        // Drawing the menu.
        context.render(this);

        // Managing the menu.
        for (;;) {
            // JBox2D Iteration.
            world.getjbox2DWorld().step(timeStep, velocityIteration, positioniteration);
            context.render(this);
            // A little sleep.
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
            }

            // Managing keyboard.
            final KeyboardEvent event = context.pollKeyboard();
            if (event == null) {
                for (Player p : players) {
                    if (p == null) {
                        continue;
                    }
                    // Applying the boost.
                    p.getRobot().setIsBoosting(p.getRobot().isIsBoosting());
                }
                /*counter = (counter + 1) % 2;
                if (counter == 0) {
                for (Player p : players) {
                if (p == null) {
                continue;
                }
                p.getRobot().setIsBoosting(false);
                p.getRobot().setIsBraking(false);
                }
                }*/
                continue;
            } else {
                counter = 0;
            }

            // Managing player keybindings.
            for (Player p : players) {
                if (p == null) {
                    continue;
                }

                RobotKeyAction action = p.getKeyBinding(event.getKey());
                if (action != null) { // if null, the key is not associated to an action.
                    switch (action) {
                        case Boost:
                            p.getRobot().setIsBoosting(!p.getRobot().isIsBoosting());
                            //System.out.println("Boost");
                            break;
                        case Brake:
                            // Currently unsuported.
                            //p.getRobot().setIsBraking(true);
                            //System.out.println("Brake");
                            break;
                        case Take:
                            // TODO
                            break;
                        case Turn_Left:
                            p.getRobot().turnLeft();
                            //System.out.println("Left");
                            break;
                        case Turn_Right:
                            p.getRobot().turnRight();
                            //System.out.println("Right");
                            break;
                    }
                }
            }

            switch (event.getKey()) {
                case W:
                    System.out.println("Fin du jeu.");
                    return;
            }
        }
    }

    /**
     * Game rendering here.
     * @param gd Graphic context.
     */
    @Override
    public void render(Graphics2D gd) {
        // Double buffer.
        Graphics2D g2bi = (Graphics2D) bi.getGraphics();
        g2bi.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);

        world.render(g2bi);
        /*for (Player p : players) {
        if (p == null) {
        continue;
        }
        p.getRobot().render(g2bi);
        }*/

        gd.drawImage(bi, 0, 0, null);
    }

    /**
     * Manage contact with a robot.
     */
    private class RobotContactListener implements ContactListener {

        @Override
        public void beginContact(Contact contact) {
        }

        @Override
        public void endContact(Contact contact) {
        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {
            Body bodyA = contact.getFixtureA().getBody();
            Body bodyB = contact.getFixtureB().getBody();

            // Getting the velocity of the impact
            WorldManifold worldManifold = new WorldManifold();
            contact.getWorldManifold(worldManifold);

            Vec2 point = worldManifold.points[0];
            Vec2 vA = bodyA.getLinearVelocityFromWorldPoint(point);
            Vec2 vB = bodyB.getLinearVelocityFromWorldPoint(point);
            float approachVelocity = Vec2.dot(vB.sub(vA), worldManifold.normal);

            // Ok, I have got the velocity. Managing the collision.
            if (bodyA.getUserData() instanceof Robot) {
                // Something hitted the robot.
                System.out.println("Something hitted the robot; Velocity: " + approachVelocity);
            }
            if (bodyB.getUserData() instanceof Robot) {
                // The robot hitted something.
                System.out.println("The robot hitted something.  Velocity: " + approachVelocity);

                
                // The collision is only an information for an area.
                if (bodyA.getUserData() instanceof Area || bodyA.getUserData() instanceof Bonus) {
                    contact.setEnabled(false);
                } else if (!(bodyA.getUserData() instanceof Robot)) {
                    // If it is not another robot, I disable the booster.
                    ((Robot) bodyB.getUserData()).setIsBoosting(false);
                }
                // Victory ? area[0] is the end area.
                if (bodyA.getUserData() == world.getAreas()[0]) {
                    // Victory!
                    System.out.println("Victory of Player " + (bodyB.getUserData().equals(players[0].getRobot()) ? 0 : 1));
                }
            }
        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {
        }
    }
}
