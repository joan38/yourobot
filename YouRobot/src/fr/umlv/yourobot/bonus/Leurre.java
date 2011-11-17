package fr.umlv.yourobot.bonus;

import fr.umlv.yourobot.field.TypeElementBase;
import java.awt.image.BufferedImage;

public class Leurre extends Bonus {

    private final Duree duree;

    public Leurre(Duree duree, TypeElementBase typeElement, BufferedImage texture) {
        super(typeElement, texture);
        this.duree = duree;
    }
}
