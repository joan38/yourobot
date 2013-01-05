/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.umlv.yourobot.elements.bonus;

import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.SoundPlayer;
import fr.umlv.yourobot.elements.TypeElementBase;
import fr.umlv.yourobot.elements.robot.RobotPlayer;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.jbox2d.collision.shapes.CircleShape;

/**
 * Represent a Medipack bonus.
 *
 * License: GNU Public license v3.
 *
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Medipack extends Bonus {

    private final CircleShape dynamicCircle;
    private final int healthBoost;

    public Medipack(BufferedImage texture, int x, int y, int healthBoost) {
        super(TypeElementBase.Unasigned, texture, 0, x, y, true);
        this.healthBoost = healthBoost;
        
        this.dynamicCircle = new CircleShape();
        this.dynamicCircle.m_radius = (float) Settings.getSize() / 2.0f;

        getFixtureDef().shape = dynamicCircle;
    }

    // Rendering
    //
    @Override
    public void render(Graphics2D gd) {
        switch (super.getState()) {
            case DisplayedButNotActive:
            case Placed: // Bonus placed on the map, I display the texture.
                super.render(gd);
                super.renderBonusText(gd, "+" + healthBoost + " hp");
            default:
        }
    }

    @Override
    public boolean stepBonus() {
        if (getState() == BonusState.Activated) {
            ((RobotPlayer) getRobot()).applyDamage(-healthBoost);

            return false;
        }
        return true;
    }

    @Override
    public void activateBonus() {
        super.activateBonus();
        SoundPlayer.play("bonusmedipack");
    }
}
