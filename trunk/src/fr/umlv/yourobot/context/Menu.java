package fr.umlv.yourobot.context;

import fr.umlv.yourobot.Manager;
import fr.umlv.yourobot.YouRobot;
import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.zen.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Manage the logic of the application.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Menu implements ApplicationCode, ApplicationRenderCode {

    private final Manager manager;

    public Menu(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void run(ApplicationContext context) {
        // Drawing the menu.
        context.render(this);

        // Managing the menu.
        for (;;) {
            final KeyboardEvent event = context.waitKeyboard();
            if (event == null) {
                return;
            }

            switch (event.getKey()) {
                case N:
                    System.out.println("New game.");
                    // TODO
                    // List all worlds and let the user select one.
                    // Currently I take the first world and launch it.
                    
                    manager.newGame(manager.getMaps()[0], 1).run(context);
                    break;

            }

            System.out.println("Key pressed in menu " + event.toString());

            context.render(this);
        }
    }
    private final Font fontTitle = new Font("arial", Font.BOLD, 30);
    private final Color colorTitle = new Color(0, 0, 0, 180);
    private final Color backColor = new Color(89, 125, 181);
    private final Font menuFont = new Font("arial", Font.BOLD, 20);
    private final Color menuColor = new Color(0, 0, 0);

    @Override
    public void render(Graphics2D gd) {
        // Drawing the background.
        gd.setColor(backColor);
        gd.fillRect(0, 0, YouRobotSetting.getWidth(), YouRobotSetting.getHeight());

        // Drawing the title.
        gd.setPaint(colorTitle);
        gd.setFont(fontTitle);

        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.drawString("YouRobot version " + YouRobot.versionMajor + "." + YouRobot.versionMinor, 10, 50);

        // Drawing the menu.
        gd.setFont(menuFont);
        gd.setPaint(menuColor);
        gd.drawString("[N] New Game", 50, 100);
        gd.drawString("[M] New Game - 2 Player", 50, 140);
    }
}
