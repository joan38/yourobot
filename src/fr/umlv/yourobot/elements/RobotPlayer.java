package fr.umlv.yourobot.elements;

import fr.umlv.yourobot.bonus.*;
import java.awt.image.BufferedImage;

public class RobotPlayer extends Robot {

    protected int health;
    protected Bonus listBonus;

    public RobotPlayer(int health, Bonus listBonus, BufferedImage texture, BufferedImage textureBoost, BufferedImage textureBrake, int x, int y) {
        super(texture, textureBoost, textureBrake, x, y);
        this.health = health;
        this.listBonus = listBonus;
    } 
}
