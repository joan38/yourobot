package fr.umlv.yourobot.elements.robot;

import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.elements.bonus.Bonus;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Represent an human robot.
 * 
 * License: GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class RobotPlayer extends Robot {

    private Integer health = 100;

    /**
     * Create a new robot for a player.
     * 
     * @param health Health of the robot. (Usually 100)
     * @param texture Texture of robot.
     * @param textureBoost Texture of the robot while boosting.
     * @param textureBrake Texture of the robot while braking.
     * @param x X position of the robot.
     * @param y Y position of the robot.
     */
    public RobotPlayer(int health, BufferedImage texture, BufferedImage textureBoost, BufferedImage textureBrake, int x, int y) {
        super(texture, textureBoost, textureBrake, x, y);
        this.health = health;
    }

    /**
     * Is the robot dead ?
     * @return True if the robot is dead.
     */
    public boolean isDead() {
        return (health <= 0);
    }

    @Override
    public void render(Graphics2D gd) {
        super.render(gd);

        // Drawing the health.
        gd.setFont(new Font("Arial", Font.BOLD, 10));
        if (health > 75) {
            gd.setPaint(Color.green);
        } else if (health > 25 && health <= 75) {
            gd.setPaint(Color.orange);
        } else {
            gd.setPaint(Color.red);
        }

        int x = (int) (this.getX() - Settings.getSize());
        int y = (int) (this.getY());
        if (health < 0) {
            gd.drawString("0", x, y);
        } else {
            gd.drawString(health.toString(), x, y);
        }

    }

    /**
     * Apply damages to the robot.
     * 
     * @param damage Damage of the robot. (Set negative damage to apply a life boost!)
     */
    public void applyDamage(int damage) {
        if (health > 0) {
            health -= damage;

            if (health <= 0) {
                setIsBoosting(false);
            }
        }
    }

    /**
     * Reset the entire life of the robot.
     */
    public void resetRobot() {
        health = 100;
    }
}
