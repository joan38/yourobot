package fr.umlv.yourobot;

import fr.umlv.yourobot.field.*;
import fr.umlv.zen.*;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
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
    }

    /**
     * Game logic here.
     * 
     * @param context Context.
     */
    @Override
    public void run(ApplicationContext context) {
        // Drawing the menu.
        context.render(this);

        // Managing the menu.
        for (;;) {
            final KeyboardEvent event = context.pollKeyboard();
            if (event == null) {
                context.render(this);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                }
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
                            break;
                        case Brake:
                            p.getRobot().setIsBraking(true);
                            break;
                        case Take:
                            // TODO
                            break;
                        case Turn_Left:
                            // TODO
                            break;
                        case Turn_Right:
                            // TODO
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
        for (Player p : players) {
            if (p == null) {
                continue;
            }
            p.getRobot().render(g2bi);
        }

        gd.drawImage(bi, 0, 0, null);
    }
}
