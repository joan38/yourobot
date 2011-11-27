package fr.umlv.yourobot.context;

import fr.umlv.yourobot.Player;
import fr.umlv.yourobot.RobotKeyAction;
import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.elements.robot.Robot;
import fr.umlv.yourobot.elements.area.Area;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.*;
import fr.umlv.yourobot.elements.robot.RobotIA;
import fr.umlv.yourobot.elements.robot.RobotPlayer;
import fr.umlv.zen.*;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    // Game Logic variables.
    //
    // Bonus
    private final int maxNumberOfBonus;
    private final int delayBeforeCreateABonus; // Milliseconds.
    private int numberOfBonus;
    private long lastBonnusPlacement = Calendar.getInstance().getTimeInMillis(); // Milliseconds
    private final ArrayList<Bonus> runningBonus = new ArrayList<Bonus>();
    // Player bonus management.
    // When a player take a bonus, its value is stored inside the variable playerBonus.
    // To take a bonus, the collision listener place the available bonus inside the array playersAvailableBonus.
    private final Bonus[] playersBonus = new Bonus[2]; // 2 = Max number of player.
    private final Bonus[] playersAvailableBonus = new Bonus[2];
    // IA Robot.
    //
    private final float robotIAPower;

    /**
     * Represent a game.
     * 
     * @param world World on the game will be played.
     * @param players Players of the games. (Set plauer[1] to null for single plauer)
     * @param numberOfBonus Number of simultaneous bonus on the map.
     * @param delayBeforeCreateABonus Delay between the creation of a bonus.
     */
    public Game(World world, Player[] players, int numberOfBonus, int delayBeforeCreateABonus, float robotIAPower) {
        //Objects.requireNonNull(players[0]);
        Objects.requireNonNull(world);

        this.world = world;
        this.players = players;

        this.bi = new BufferedImage(YouRobotSetting.getWidth(), YouRobotSetting.getHeight(), BufferedImage.TYPE_INT_RGB);

        this.world.addElement(players[0].getRobot());
        if (players[1] != null) {
            this.world.addElement(players[1].getRobot());
        }

        // Bonus management.
        this.maxNumberOfBonus = numberOfBonus;
        this.delayBeforeCreateABonus = delayBeforeCreateABonus * 1000;
        this.numberOfBonus = 0;

        // Contact management.
        this.world.getjbox2DWorld().setContactListener(new RobotContactListener());

        // Power of RobotIA.
        this.robotIAPower = robotIAPower;
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

        // Drawing the menu.
        context.render(this);

        // Managing the menu.
        for (;;) {
            // Bonus iteration
            Iterator<Bonus> bonusIt = runningBonus.iterator();
            while (bonusIt.hasNext()) {
                Bonus b = bonusIt.next();
                if (b.stepBonus() == false) { // If stepBonus return false, I must remove ended the bonus.
                    world.removeElement(b); // Removing the bonus from the world.
                    bonusIt.remove(); // Removing the bonus from the bonus list.
                }
            }
            // RobotIA iteration
            for (RobotIA r : world.getRobotIAs()) {
                r.stepIA();
            }
            // Adding/Removing bonus.
            if (numberOfBonus >= maxNumberOfBonus) {
                lastBonnusPlacement = Calendar.getInstance().getTimeInMillis();
            } else if (lastBonnusPlacement + delayBeforeCreateABonus < Calendar.getInstance().getTimeInMillis()) {
                // Adding a bonus.
                Bonus b;
                do {
                    b = Bonus.getRandomBonus();
                } while (world.isOverlap(b.getX(), b.getY(), YouRobotSetting.getSize(), YouRobotSetting.getSize()));
                lastBonnusPlacement = Calendar.getInstance().getTimeInMillis();
                numberOfBonus++;
                world.addElement(b);
            }

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
                continue;
            }

            // Managing player keybindings.
            for (int i = 0; i < 2; i++) {
                Player p = players[i];

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
                            // If the player hold a bonus, I activate it.
                            if (playersBonus[i] != null) {
                                // Activating the bonus.
                                playersBonus[i].activateBonus();
                                runningBonus.add(playersBonus[i]);
                                // The player do not hold anymore a bonus.
                                playersBonus[i] = null;
                            } else if (playersAvailableBonus[i] != null) {
                                // If the player is on a bonus, I grab it.
                                playersBonus[i] = playersAvailableBonus[i];
                                playersBonus[i].grabBonus(p.getRobot()); // I mark the bonus as grabbed.
                                numberOfBonus--;
                            }
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
        /*for (Bonus b : runningBonus) {
        b.render(g2bi);
        }*/

        gd.drawImage(bi, 0, 0, null);
    }

    /**
     * Manage contact with a robot.
     */
    private class RobotContactListener implements ContactListener {

        @Override
        public void beginContact(Contact contact) {
            // Managing contact with a bonus.
            Body bodyA = contact.getFixtureA().getBody();
            Body bodyB = contact.getFixtureB().getBody();

            if (bodyB.getUserData() instanceof Robot && bodyA.getUserData() instanceof Bonus) {
                // Woot, a bonus.
                // Marking it as available to be took by the player.
                playersAvailableBonus[(bodyB.getUserData().equals(players[0].getRobot()) ? 0 : 1)] = (Bonus) bodyA.getUserData();
            } else if (bodyA.getUserData() instanceof Robot && bodyB.getUserData() instanceof Bonus) {
                // Woot, a bonus.
                // Marking it as available to be took by the player.
                playersAvailableBonus[(bodyA.getUserData().equals(players[0].getRobot()) ? 0 : 1)] = (Bonus) bodyB.getUserData();
            }
        }

        @Override
        public void endContact(Contact contact) {
            // Managing the endcontact with a bonus.
            Body bodyA = contact.getFixtureA().getBody();
            Body bodyB = contact.getFixtureB().getBody();

            if (bodyB.getUserData() instanceof Robot && bodyA.getUserData() instanceof Bonus) {
                // Woot, a bonus.
                // Marking it as available to be took by the player.
                playersAvailableBonus[(bodyB.getUserData().equals(players[0].getRobot()) ? 0 : 1)] = null;
            } else if (bodyA.getUserData() instanceof Robot && bodyB.getUserData() instanceof Bonus) {
                // Woot, a bonus.
                // Marking it as available to be took by the player.
                playersAvailableBonus[(bodyA.getUserData().equals(players[0].getRobot()) ? 0 : 1)] = null;
            }
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

            // Collision between a robotPlayer and RobotIA.
            if (bodyA.getUserData() instanceof RobotPlayer && bodyB.getUserData() instanceof RobotIA
                    || bodyB.getUserData() instanceof RobotPlayer && bodyA.getUserData() instanceof RobotIA) {
                // Collision of a RobotPlayer and RobotIA.
                RobotPlayer robotPlayer = (RobotPlayer) ((bodyA.getUserData() instanceof RobotPlayer) ? bodyA.getUserData() : bodyB.getUserData());
                robotPlayer.applyDamage((int) (Math.abs(approachVelocity) * robotIAPower));
                return;
            }

            // Collision of a robot with a bonus.
            if ((bodyA.getUserData() instanceof Robot || bodyB.getUserData() instanceof RobotPlayer)
                    && (bodyA.getUserData() instanceof Bonus || bodyB.getUserData() instanceof Bonus)) {
                contact.setEnabled(false); // Ignoring the contact.
                return;
            }

            // Collision of a robot with an area.
            if ((bodyA.getUserData() instanceof Robot || bodyB.getUserData() instanceof Robot)
                    && (bodyA.getUserData() instanceof Area || bodyB.getUserData() instanceof Area)) {
                contact.setEnabled(false); // Ignoring the contact.

                // Checking if there is a victory.
                if (bodyA.getUserData() == world.getAreas()[0] || bodyB.getUserData() == world.getAreas()[0]) {
                    // Victory area. Is it an human robot that touched the element ?
                    
                    RobotPlayer robotPlayer = null;
                    if (bodyA.getUserData() instanceof RobotPlayer) {
                        robotPlayer = (RobotPlayer) bodyA.getUserData();
                    } else if (bodyB.getUserData() instanceof RobotPlayer) {
                        robotPlayer = (RobotPlayer) bodyB.getUserData();
                    }

                    if (robotPlayer != null) {
                        // It is an human robot.
                        // Setting the victory.
                        System.out.println("Victory of Player " + (bodyB.getUserData().equals(players[0].getRobot()) ? 0 : 1));
                    }
                }
                return;
            }
        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {
        }
    }
}
