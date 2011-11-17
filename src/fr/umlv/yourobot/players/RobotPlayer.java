package fr.umlv.yourobot.players;

import fr.umlv.yourobot.bonus.*;
import fr.umlv.yourobot.field.TypeElementBase;
import java.awt.image.BufferedImage;

public class RobotPlayer extends Robot {

    protected int health;
    protected Bonus listBonus;

    public RobotPlayer(int health, Bonus listBonus, BufferedImage texture, BufferedImage textureBoost, BufferedImage textureBrake) {
        super(texture, textureBoost, textureBrake);
        this.health = health;
        this.listBonus = listBonus;
    }
}
