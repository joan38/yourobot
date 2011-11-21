package fr.umlv.yourobot.elements;

import fr.umlv.yourobot.bonus.*;
import java.awt.image.BufferedImage;

public class RobotPlayer extends Robot {

    protected int health;
    protected Bonus listBonus;

    /**
     * Create a new robot for a player.
     * 
     * @param health Health of the robot.
     * @param texture Texture of robot.
     * @param textureBoost Texture of the robot while boosting.
     * @param textureBrake Texture of the robot while braking.
     * @param x X position of the robot.
     * @param y Y position of the robot.
     */
    public RobotPlayer(int healt, BufferedImage texture, BufferedImage textureBoost, BufferedImage textureBrake, int x, int y) {
        super(texture, textureBoost, textureBrake, x, y);
        this.health = health;
        this.listBonus = listBonus;
    } 
}
