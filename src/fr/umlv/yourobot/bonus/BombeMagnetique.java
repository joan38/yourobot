package fr.umlv.yourobot.bonus;

import fr.umlv.yourobot.field.*;
import java.awt.image.BufferedImage;

public class BombeMagnetique extends Bonus {

    private final TypeElementBase element;

    public BombeMagnetique(TypeElementBase element, TypeElementBase typeElement, BufferedImage texture, int x, int y) {
        super(typeElement, texture, x, y);
        this.element = element;
    }

  
    
}
