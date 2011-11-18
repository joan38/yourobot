package fr.umlv.yourobot.field;

import java.awt.image.BufferedImage;
import org.jbox2d.dynamics.World;

public class Barres extends Element {

    public Barres(TypeElementBase typeElement, BufferedImage texture) {
        super(typeElement, texture);
    }

    public Barres(TypeElementBase typeElement, BufferedImage texture, int x, int y) {
        super(typeElement, texture, x, y);
    }

    @Override
    public void jboxBodyInit(World w) {
        // TODO
    }

    @Override
    public void jboxBodyDestroy(World w) {
        // TODO
    }
}
