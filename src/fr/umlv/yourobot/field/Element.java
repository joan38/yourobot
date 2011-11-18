package fr.umlv.yourobot.field;

import fr.umlv.yourobot.YouRobotSetting;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

/**
 * Represent an element.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public abstract class Element {

    private final TypeElementBase typeElement; // Type of the element.
    private BufferedImage texture; // Texture of the element.
    // Positions
    private int x = 0;
    private int y = 0;
    private int orientation = 0;
    // Affinetransform for the texture.
    private AffineTransform textureTransformer = new AffineTransform();
    private AffineTransformOp bufferedTextureTransformerOp;

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
     * 
     * @note You can override the position function getX() and getY(). The rendering will be made on the overriden method.
     */
    public void render(Graphics2D gd) {
        gd.drawImage(texture, bufferedTextureTransformerOp, getX(), getY());
    }

    /**
     * Define the orientation of the element to render.
     * 
     * @param degree Orientation to set in degree. 
     */
    public void setOrientation(int degree) {
        orientation = degree;
        textureTransformer.rotate(Math.toRadians(degree), texture.getWidth() / 2.0, texture.getHeight() / 2.0);
        this.bufferedTextureTransformerOp = new AffineTransformOp(textureTransformer, AffineTransformOp.TYPE_BILINEAR);
    }

    /**
     * Get the orientation of the element.
     * @return The orientation of the element.
     */
    public int getOrientation() {
        return orientation;
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

    protected AffineTransformOp getBufferedTextureTransformerOp() {
        return bufferedTextureTransformerOp;
    }
    
    // JBox2D
    /**
     * Init the body with JBox2D.
     * @param w JBox2D World.
     */
    public abstract void jboxBodyInit(org.jbox2d.dynamics.World w);
    
    /**
     * Remove the body from JBox2D.
     * @param w JBox2D World
     */
    public abstract void jboxBodyDestroy(org.jbox2d.dynamics.World w);
    
    
}
