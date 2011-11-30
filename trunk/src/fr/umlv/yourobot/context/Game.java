package fr.umlv.yourobot.context;

import fr.umlv.yourobot.Player;
import fr.umlv.yourobot.RobotKeyAction;
import fr.umlv.yourobot.Settings;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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
 * License: GNU Public license v3.
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
    // Victory.
    //
    private int victoriousPlayer = 0; // 0 -> no Victory.

    /**
     * Represent a game.
     * 
     * @param world World on the game will be played.
     * @param players Players of the games. (Set plauer[1] to null for single player. Must be an array of 2 elements.)
     * @param numberOfBonus Number of simultaneous bonus on the map.
     * @param delayBeforeCreateABonus Delay between the creation of a bonus.
     * @param robotIAPower The intensity of damages made by the RobotIA to a RobotPlayer. 
     */
    public Game(World world, Player[] players, int numberOfBonus, int delayBeforeCreateABonus, float robotIAPower) {
        Objects.requireNonNull(players);
        Objects.requireNonNull(world);

        if (players.length != 2) {
            throw new IllegalArgumentException("Invalid players array. Lenght != 2");
        }

        this.world = world;
        this.players = players;

        this.bi = new BufferedImage(Settings.getWidth(), Settings.getHeight(), BufferedImage.TYPE_INT_RGB);

        this.world.addElement(players[0].getRobot());
        if (players.length > 1 && players[1] != null) {
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

        // Placing robots inside areas.
        for (int i = 0; i < 2; i++) {
            Player p = players[i];
            if (p != null) {
                p.getRobot().resetRobot();
                p.getRobot().setX(world.getAreas()[i + 1].getX());
                p.getRobot().setY(world.getAreas()[i + 1].getY());
            }
        }
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

        // Drawing the game.
        context.render(this);

        // Managing the menu.
        for (;;) {
            // Bonus iteration
            Iterator<Bonus> bonusIt = runningBonus.iterator();
            while (bonusIt.hasNext()) {
                Bonus b = bonusIt.next();
                if (b.stepBonus() == false) { // If stepBonus return false, I must remove ended the bonus.
                    bonusIt.remove(); // Removing the bonus from the bonus list.
                    world.removeElement(b); // Removing the bonus from the world.
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
                } while (world.isOverlap(b.getX(), b.getY(), Settings.getSize(), Settings.getSize()));
                lastBonnusPlacement = Calendar.getInstance().getTimeInMillis();
                numberOfBonus++;
                world.addElement(b);
            }

            // JBox2D Iteration.
            world.getjbox2DWorld().step(timeStep, velocityIteration, positioniteration);
            context.render(this);

            // Victory !
            if (victoriousPlayer != 0) {
                for (Player p : players) {
                    if (p == null) {
                        continue;
                    }
                    p.getRobot().setIsBoosting(false);
                }
                return; // A player have won.
            }

            // A little sleep.
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                throw new IllegalStateException("Unexpected thread interruption", ex);
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
            boolean atLeastOnePlayerIsAlive = false;
            for (int i = 0; i < 2; i++) {
                Player p = players[i];
                if (p == null || p.getRobot().isDead()) {
                    continue;
                }
                atLeastOnePlayerIsAlive = true;

                RobotKeyAction action = p.getKeyBinding(event.getKey());
                if (action != null) { // if null, the key is not associated to an action.
                    switch (action) {
                        case Boost:
                            p.getRobot().setIsBoosting(!p.getRobot().isIsBoosting());
                            //System.out.println("Boost");
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
            // Managing victory or death of all players.
            if (atLeastOnePlayerIsAlive == false) {
                return; // Everybody is dead.
            }
        }
    }

    /**
     * Get the victorious player.
     * @return 0: No win, 1: player 1 win, 2: player 2 win.
     */
    public int getVictoriousPlayer() {
        return victoriousPlayer;
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
            if ((bodyA.getUserData() instanceof Robot || bodyB.getUserData() instanceof Robot)
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
                        victoriousPlayer = (bodyB.getUserData().equals(players[0].getRobot()) ? 1 : 2);
                        return;
                    }
                }
                return;
            }

            if ((bodyA.getUserData() instanceof RobotIA)) {
                ((RobotIA) bodyA.getUserData()).turnRight();
            }
            if ((bodyB.getUserData() instanceof RobotIA)) {
                ((RobotIA) bodyB.getUserData()).turnRight();
            }

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {
        }
    }
}
