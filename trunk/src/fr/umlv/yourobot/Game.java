package fr.umlv.yourobot;

import fr.umlv.yourobot.field.*;
import fr.umlv.zen.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

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
        final int velocityIteration = 8;
        final int positioniteration = 3;

        // Drawing the menu.
        context.render(this);

        // Managing the menu.
        for (;;) {
            // A little sleep.
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
            }
            // JBox2D Iteration.
            world.getjbox2DWorld().step(timeStep, velocityIteration, positioniteration);

            // Managing keyboard.
            final KeyboardEvent event = context.pollKeyboard();
            if (event == null) {
                context.render(this);
                continue;
            }

            // Managing player keybindings.
            for (Player p : players) {
                if (p == null) {
                    continue;
                }

                // TODO, là c'est bof.
                p.getRobot().setIsBoosting(false);
                p.getRobot().setIsBraking(false);

                RobotKeyAction action = p.getKeyBinding(event.getKey());
                if (action != null) { // if null, the key is not associated to an action.
                    switch (action) {
                        case Boost:
                            p.getRobot().setIsBoosting(true);
                            System.out.println("Boost");
                            break;
                        case Brake:
                            p.getRobot().setIsBraking(true);
                            System.out.println("Brake");
                            break;
                        case Take:
                            // TODO
                            break;
                        case Turn_Left:
                            p.getRobot().turnLeft();
                            System.out.println("Left");
                            break;
                        case Turn_Right:
                            p.getRobot().turnRight();
                            System.out.println("Right");
                            break;
                    }
                }
            }

            switch (event.getKey()) {
                case W:
                    System.out.println("Fin du jeu.");
                    return;
            }

            context.render(this);
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

        world.render(g2bi);
        /*for (Player p : players) {
        if (p == null) {
        continue;
        }
        p.getRobot().render(g2bi);
        }*/

        gd.drawImage(bi, 0, 0, null);
    }
}
