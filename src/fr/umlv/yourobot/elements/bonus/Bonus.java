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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    long bonusActivationDate;
    final Integer durationOfBonusInSeconds;

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
        bonusActivationDate = Calendar.getInstance().getTimeInMillis();

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
        String duration = "";
        if (durationOfBonusInSeconds != 0) {
            duration = " "+ durationOfBonusInSeconds.toString();
        }

        gd.setFont(new Font("Arial", Font.BOLD, 12));
        gd.setPaint(Color.yellow.darker());
        if (robot == null) {
            int x = (int) (this.getX() + YouRobotSetting.getSize());
            int y = (int) (this.getY());

            // Drawing the text.
            gd.drawString(this.getTypeElement() + duration, x, y);
        } else {
            int x = (int) (robot.getX() + YouRobotSetting.getSize());
            int y = (int) (robot.getY());
            Long tmp = durationOfBonusInSeconds - Math.round((double) (Calendar.getInstance().getTimeInMillis() - bonusActivationDate) / 1000.0);
            gd.drawString(tmp.toString(), x, y);
        }
    }

    /**
     * Get a new random bonus.
     * 
     * @return A bonus.
     */
    public static Bonus getRandomBonus() {
        Random rand = new Random();
        TypeElementBase typeElement;

        switch (rand.nextInt(3)) {
            case 0:
                typeElement = TypeElementBase.Ice;
                break;
            case 1:
                typeElement = TypeElementBase.Wood;
                break;
            default:
                typeElement = TypeElementBase.Stone;
                break;
        }

        switch (rand.nextInt(3)) {
            case 0:
                try {
                    return new BombeMagnetique(typeElement, TextureLoader.loadTexture("src/textures/bomb.png", true),
                            rand.nextInt(YouRobotSetting.getWidth() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize(),
                            rand.nextInt(YouRobotSetting.getHeight() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize());
                } catch (IOException ex) {
                    System.err.println("Error while loading texture : src/textures/bomb.png");
                }
                break;
            case 1:
                try {
                    return new Snap(typeElement, TextureLoader.loadTexture("src/textures/snap.png", true), rand.nextInt(4) + 1,
                            rand.nextInt(YouRobotSetting.getWidth() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize(),
                            rand.nextInt(YouRobotSetting.getHeight() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize());
                } catch (IOException ex) {
                    System.err.println("Error while loading texture : src/textures/bomb.png");
                }
                break;
            default:
                try {
                    return new Leurre(typeElement, TextureLoader.loadTexture("src/textures/leurre.png", true), rand.nextInt(10) + 2,
                            rand.nextInt(YouRobotSetting.getWidth() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize(),
                            rand.nextInt(YouRobotSetting.getHeight() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize());
                } catch (IOException ex) {
                    System.err.println("Error while loading texture : src/textures/bomb.png");
                }
                break;
        }

        // Should never happen.
        throw new IllegalStateException("This case must not happen. return null");
    }
}
