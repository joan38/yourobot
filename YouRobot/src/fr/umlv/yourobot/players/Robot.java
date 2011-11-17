package fr.umlv.yourobot.players;

import fr.umlv.yourobot.field.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Robot extends Element {

    protected boolean isBoosting = false;
    protected boolean isBraking = false;
    protected BufferedImage textureBoost;
    protected BufferedImage textureBrake;

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
            gd.drawImage(textureBoost, bufferedTextureTransformerOp, x, y);
        } else if (isBraking == true) {
            gd.drawImage(textureBrake, bufferedTextureTransformerOp, x, y);
        } else {
            gd.drawImage(texture, bufferedTextureTransformerOp, x, y);
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
