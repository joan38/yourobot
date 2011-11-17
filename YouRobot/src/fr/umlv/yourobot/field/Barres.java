package fr.umlv.yourobot.field;

import java.awt.image.BufferedImage;

public class Barres extends Element {

    public Barres(TypeElementBase typeElement, BufferedImage texture) {
        super(typeElement, texture);
    }

    public Barres(TypeElementBase typeElement, BufferedImage texture, int x, int y) {
        super(typeElement, texture, x, y);
    }
}
