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

    private final Duree duree;

    public Leurre(Duree duree, TypeElementBase typeElement, BufferedImage texture, int x, int y) {
        super(typeElement, texture, x, y);
        this.duree = duree;
    }

    @Override
    public void activateBonus(Robot robot, World world) {
        
    }
    
    


}
