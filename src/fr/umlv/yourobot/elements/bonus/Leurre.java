package fr.umlv.yourobot.elements.bonus;

import fr.umlv.yourobot.elements.robot.Robot;
import fr.umlv.yourobot.elements.TypeElementBase;
import fr.umlv.yourobot.elements.World;
import java.awt.image.BufferedImage;

/**
 * Represent a Leurre bonus.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Leurre extends Bonus {

    public Leurre(TypeElementBase typeElement, BufferedImage texture, int durationOfBonusInSeconds, int x, int y) {
        super(typeElement, texture,durationOfBonusInSeconds, x, y);
    }

    @Override
    public void activateBonus(Robot robot, World world) {
        
    }

    @Override
    public boolean stepBonus() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
