package fr.umlv.yourobot.players;

import fr.umlv.yourobot.field.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Robot extends Element {

    private boolean isBoosting = false;
    private boolean isBraking = false;
    private BufferedImage textureBoost;
    private BufferedImage textureBrake;

    public Robot(BufferedImage texture, BufferedImage textureBoost, BufferedImage textureBrake) {
        super(TypeElementBase.Robot, texture);

        this.textureBoost = textureBoost;
        this.textureBrake = textureBrake;
    }

    /**
     * Render the element on the graphic gd.
     * This method simply draw the texture.
     * 
     * @param gd Graphic to draw on.
     */
    @Override
    public void render(Graphics2D gd) {
        if (isBoosting == true) {
            gd.drawImage(textureBoost, super.getBufferedTextureTransformerOp(), getX(), getY());
        } else if (isBraking == true) {
            gd.drawImage(textureBrake, super.getBufferedTextureTransformerOp(), getX(), getY());
        } else {
            super.render(gd);
        }
    }

    public boolean isIsBoosting() {
        return isBoosting;
    }

    public void setIsBoosting(boolean isBoosting) {
        this.isBoosting = isBoosting;
    }

    public boolean isIsBraking() {
        return isBraking;
    }

    public void setIsBraking(boolean isBraking) {
        this.isBraking = isBraking;
    }
}
