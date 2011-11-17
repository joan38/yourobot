package fr.umlv.yourobot.field;

import fr.umlv.yourobot.YouRobotSetting;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Represent an element.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public abstract class Element {

    protected final TypeElementBase typeElement; // Type of the element.
    protected BufferedImage texture; // Texture of the element.
    // Positions
    protected int x = 0;
    protected int y = 0;
    // Affinetransform for the texture.
    protected AffineTransform textureTransformer = new AffineTransform();
    protected AffineTransformOp bufferedTextureTransformerOp;

    public Element(TypeElementBase typeElement, BufferedImage texture) {
        this.typeElement = typeElement;
        this.texture = texture;

        this.textureTransformer = new AffineTransform();
        this.textureTransformer.setToIdentity();
        // Setting up the scaling down of the texture.
        double scale = (double) YouRobotSetting.getSize() / (double) texture.getHeight();
        this.textureTransformer.setToScale(scale, scale);
        this.bufferedTextureTransformerOp = new AffineTransformOp(textureTransformer, AffineTransformOp.TYPE_BILINEAR);
    }

    public Element(TypeElementBase typeElement, BufferedImage texture, int x, int y) {
        this(typeElement, texture);

        this.x = x;
        this.y = y;
    }

    /**
     * Render the element on the graphic gd.
     * This method simply draw the texture.
     * 
     * @param gd Graphic to draw on.
     */
    public void render(Graphics2D gd) {
        gd.drawImage(texture, bufferedTextureTransformerOp, x, y);
    }

    /**
     * Define the orientation of the element to render.
     * 
     * @param degree Orientation to set in degree. 
     */
    public void setOrientation(int degree) {
        textureTransformer.rotate(Math.toRadians(degree), texture.getWidth() / 2.0, texture.getHeight() / 2.0);
        this.bufferedTextureTransformerOp = new AffineTransformOp(textureTransformer, AffineTransformOp.TYPE_BILINEAR);
    }

    /**
     * Get the type of this element.
     * 
     * @return The type of this element.
     */
    public TypeElementBase getTypeElement() {
        return typeElement;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
