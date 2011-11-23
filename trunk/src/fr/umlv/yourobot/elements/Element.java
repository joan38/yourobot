package fr.umlv.yourobot.elements;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

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
    private int orientation = 0;
    // Affinetransform for the texture (rotation)
    private AffineTransform textureTransformer = new AffineTransform();
    // JBox2D
    private Body body;
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    public Element(TypeElementBase typeElement, BufferedImage texture, int x, int y) {
        this.typeElement = typeElement;
        this.texture = texture;

        this.textureTransformer = new AffineTransform();
        this.textureTransformer.setToIdentity();

        // JBox2D.
        this.bodyDef = new BodyDef();
        this.fixtureDef = new FixtureDef();

        // Initial position.
        this.bodyDef.position = new Vec2(x, y);
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
        this.textureTransformer.setToIdentity();
        if (body != null) {
            textureTransformer.rotate(body.getAngle(), texture.getWidth() / 2.0, texture.getHeight() / 2.0);
        } else {
            textureTransformer.rotate(Math.toRadians(orientation), texture.getWidth() / 2.0, texture.getHeight() / 2.0);
        }

        gd.drawImage(texture, new AffineTransformOp(textureTransformer, AffineTransformOp.TYPE_BILINEAR), getX(), getY());
    }

    /**
     * Define the orientation of the element to render.
     * 
     * @param degree Orientation to set in degree. 
     */
    public void setOrientation(int degree) {
        orientation = degree;
        if (body != null) {
            body.setTransform(body.getPosition(), (float) Math.toRadians(degree));
        }
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

    /**
     * Get the position of the element.
     * @return The actual position of the element.
     */
    public int getX() {
        if (body == null) {
            return (int) bodyDef.position.x;
        } else {
            return (int) body.getPosition().x;
        }
    }

    /**
     * Get the position of the element.
     * @return The actual position of the element.
     */
    public int getY() {
        if (body == null) {
            return (int) bodyDef.position.y;
        } else {
            return (int) body.getPosition().y;
        }
    }

    /**
     * Force the position.
     * @note Break all the physics. (Apply a transform, do not use)
     * @param x New x.
     */
    public void setX(int x) {
        if (body == null) {
            bodyDef.position.x = x;
        } else {
            body.setTransform(new Vec2(x, getY()), body.getAngle());
        }
    }

    /**
     * Force the position.
     * @note Break all the physics. (Apply a transform, do not use)
     * @param y New y.
     */
    public void setY(int y) {
        if (body == null) {
            bodyDef.position.y = y;
        } else {
            body.setTransform(new Vec2(getX(), y), body.getAngle());
        }
    }

    // JBox2D Register and Unregister
    //
    /**
     * Attach the element with the JBox2D World w.
     * @param w JBox2D World.
     */
    public void attachToWorld(org.jbox2d.dynamics.World w) {
        // Associating this object with the body.
        // This is made here and not in the constructor to prevent a constructor this leak.
        this.bodyDef.userData = (Object) this; 
        
        body = w.createBody(bodyDef);
        body.createFixture(fixtureDef);

        setOrientation(orientation);
    }

    /**
     * Remove the body from JBox2D world w.
     * @param w JBox2D World
     */
    public void detachFromWorld(org.jbox2d.dynamics.World w) {
        w.destroyBody(body);
        body = null;
    }

    // Getters.
    // Only for the subclasses.
    //
    /**
     * Get the body of the superclass.
     * @return The body. Can be null if the element is not registered with a world.
     */
    public Body getBody() {
        return body;
    }

    /**
     * Get the texture of the element.
     * @return The texture of the element.
     */
    protected BufferedImage getTexture() {
        return texture;
    }

    /**
     * Get the definition of the body.
     * @return The definition of the body.
     * 
     * @note Once the element is registered, the definition is ignored. Use only inside the constructor.
     */
    protected BodyDef getBodyDef() {
        return bodyDef;
    }

    /**
     * The fixtureDefinition of the element.
     * @return The fixturedefinition of element.
     * 
     * @note Once the element is registered, the definition is ignored. Use only inside the constructor.
     */
    protected FixtureDef getFixtureDef() {
        return fixtureDef;
    }
}
