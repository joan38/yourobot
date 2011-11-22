package fr.umlv.yourobot.elements;

import fr.umlv.yourobot.elements.TypeElementBase;
import java.awt.image.BufferedImage;

public class Snap extends Bonus {

    private final Duree duree;

    public Snap(Duree duree, TypeElementBase typeElement, BufferedImage texture, int x, int y) {
        super(typeElement, texture, x, y);
        this.duree = duree;
    }

    @Override
    public void activateBonus(Robot robot, World world) {
        
    }
    
    
}
