package fr.umlv.yourobot.elements.bonus;

import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.elements.*;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robot.Robot;
import fr.umlv.yourobot.elements.TypeElementBase;
import fr.umlv.yourobot.elements.World;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.Date;

/**
 * Represent a bonus.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public abstract class Bonus extends Element {

    Robot robot;
    World world;
    Date bonusActivationDate;
    private final Integer durationOfBonusInSeconds;

    /**
     * A Bonus.
     * 
     * @param typeElement Type of the element.
     * @param texture Texture of the bonus.
     * @param x Initial x position of the bonus.
     * @param y Initial y position of the bonus.
     */
    public Bonus(TypeElementBase typeElement, BufferedImage texture, int durationOfBonusInSeconds, int x, int y) {
        super(typeElement, texture, x, y);

        // A bonus do not rotate.
        getBodyDef().fixedRotation = true;
        this.durationOfBonusInSeconds = durationOfBonusInSeconds;
    }

    /**
     * Associate the bonus with a world and a robot.
     * Call automatically stepBonus to action the bonus.
     * 
     * @param robot Robot that activate the bonus.
     * @param world World of the bonus.
     */
    public void activateBonus(Robot robot, World world) {
        this.robot = robot;
        this.world = world;
        bonusActivationDate = Calendar.getInstance().getTime();

        stepBonus();
    }

    /**
     * Step the bonus, no effect if activateBonus was not activated.
     * 
     * @note The bonus effect will end after the duration of the bonus.
     * 
     * @return false is the bonus MUST BE DELETED. Because its effect has finished. true otherwise.
     */
    public abstract boolean stepBonus();

    @Override
    public void render(Graphics2D gd) {
        super.render(gd);

        // Render the duration.
        if (durationOfBonusInSeconds != 0) {
            gd.setPaint(Color.yellow.darker());
            if (robot == null) {
                int x = (int) (this.getX() + YouRobotSetting.getSize());
                int y = (int) (this.getY());

                // Drawing the text.
                gd.setFont(new Font("Arial", Font.BOLD, 12));
                gd.drawString(durationOfBonusInSeconds.toString(), x, y);
            } else {
                int x = (int) (robot.getX() + YouRobotSetting.getSize());
                int y = (int) (robot.getY());

                // Todo
            }
        }
    }
}
