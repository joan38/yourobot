package fr.umlv.yourobot.elements.bonus;

import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.elements.robot.Robot;
import fr.umlv.yourobot.elements.TypeElementBase;
import fr.umlv.yourobot.elements.World;
import java.awt.image.BufferedImage;
import org.jbox2d.collision.shapes.CircleShape;

public class BombeMagnetique extends Bonus {

    // JBox2D.
    private final CircleShape dynamicCircle;

    public BombeMagnetique(TypeElementBase element, BufferedImage texture, int x, int y) {
        super(element, texture, x, y);
        
        this.dynamicCircle = new CircleShape();
        this.dynamicCircle.m_radius = (float) YouRobotSetting.getSize() / 2.0f;
        
        getFixtureDef().shape = dynamicCircle;
    }

    @Override
    public void activateBonus(Robot robot, World world) {
        // BombeMagnetiqueEffect
        
        // TODO!
        //world.getjbox2DWorld().queryAABB(null, null);
        
    }
}
