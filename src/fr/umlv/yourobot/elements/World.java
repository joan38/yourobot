package fr.umlv.yourobot.elements;

import fr.umlv.yourobot.elements.wall.Barres;
import fr.umlv.yourobot.elements.area.Area;
import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.robot.Robot;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;

/**
 * Manage the logic of the application.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class World {

    private Area[] areas;
    private final LinkedList<Element> elements; // Elements of the game.
    private BufferedImage backgroundPattern;
    // JBox2D
    private final org.jbox2d.dynamics.World jWorld;
    private final BodyDef jGroundBodyDef;
    private final Body jGroundBody;
    private final PolygonShape jGroundBox;

    /**
     * Empty world.
     * 
     * @param backgroundPattern Texture pattern for the background.
     * @param area area[0] = endArea, area[1] = startArea Player 1, area[2] = startArea Player 2.
     */
    public World(BufferedImage backgroundPattern, Area[] areas) {
        Objects.requireNonNull(backgroundPattern);
        Objects.requireNonNull(areas);

        this.elements = new LinkedList<Element>();
        this.backgroundPattern = backgroundPattern;

        if (areas.length != 3) {
            throw new IllegalArgumentException("A world must contain 3 area.");
        }
        this.areas = areas;

        // Creation of the world in JBox2D.
        this.jWorld = new org.jbox2d.dynamics.World(new Vec2(0.0f, 0.0f), true);
        this.jWorld.setContinuousPhysics(true);
        this.jGroundBodyDef = new BodyDef();
        jGroundBodyDef.position.set(0.0f, -10.0f);
        this.jGroundBody = jWorld.createBody(jGroundBodyDef);
        this.jGroundBox = new PolygonShape();
        jGroundBox.setAsBox(50.0f, 10.0f);
        jGroundBody.createFixture(jGroundBox, 0.0f);

        // Registering areas in Jbox2d
        for (Area a : areas) {
            a.attachToWorld(jWorld);
        }
    }

    /**
     * Render a world.
     * 
     * @param gd World to render.
     */
    public void render(Graphics2D gd) {
        // Drawing the background.
        if (backgroundPattern != null) {
            // An image is set.
            for (int i = 0; i < YouRobotSetting.getWidth(); i += backgroundPattern.getWidth()) {
                for (int j = 0; j < YouRobotSetting.getHeight(); j += backgroundPattern.getHeight()) {
                    gd.drawImage(backgroundPattern, i, j, null);
                }
            }
        } else {
            // Draw static background.
        }

        // Drawing start/end area
        for (Element e : areas) {
            e.render(gd);
        }

        // Drawing elements.
        for (Element e : elements) {
            e.render(gd);
        }

        // Ok.
    }

    /**
     * Set the background pattern element.
     * @param image Image to set.
     */
    public void setBackgroundPattern(BufferedImage image) {
        this.backgroundPattern = image;
    }

    /**
     * Add an element into the world.
     * 
     * @param e Element to add.
     */
    public void addElement(Element element) {
        if (element instanceof Robot) {
            elements.add(element);
        } else {
            elements.push(element);
        }

        // Adding the element to the physical world.
        element.attachToWorld(jWorld);
    }

    /**
     * Remove an element from the world.
     * 
     * @param e Element to remove.
     */
    public void removeElement(Element e) {
        elements.remove(e);

        // Removing the element from the physical world.
        e.detachFromWorld(jWorld);
    }

    /**
     * Fill the given world with an unbreakable tube around.
     * 
     * @param w World to fill.
     * @param texturePath Path of the texture to use.
     */
    public static void fillBorder(World w, String texturePath) throws IOException {
        BufferedImage tubeTexture = TextureLoader.loadTexture(texturePath, true);

        // Vertical.
        int rightPos = YouRobotSetting.getWidth() - YouRobotSetting.getSize();
        Element e;
        for (int i = 0; i < YouRobotSetting.getHeight(); i += YouRobotSetting.getSize()) {
            e = new Barres(TypeElementBase.Unasigned, tubeTexture, 0, i);
            e.setOrientation(90);
            w.addElement(e);

            e = new Barres(TypeElementBase.Unasigned, tubeTexture, rightPos, i);
            e.setOrientation(90);
            w.addElement(e);
        }

        // Horizontal.
        int lowerPos = YouRobotSetting.getHeight() - YouRobotSetting.getSize();
        for (int i = 0; i < YouRobotSetting.getWidth(); i += YouRobotSetting.getSize()) {
            w.addElement(new Barres(TypeElementBase.Unasigned, tubeTexture, i, 0));
            w.addElement(new Barres(TypeElementBase.Unasigned, tubeTexture, i, lowerPos));
        }
    }

    public org.jbox2d.dynamics.World getjbox2DWorld() {
        return jWorld;
    }

    public Area[] getAreas() {
        return areas;
    }

    /**
     * Returns true if the element will overlap another element.
     * 
     * @param x X position.
     * @param y Y position.
     * @param width Width of the element.
     * @param height Height of the element.
     * @return True if there is an overlap, false otherwise.
     */
    public boolean isOverlap(int x, int y, int width, int height) {
        // Preventing the overlapping.
        final Boolean[] isOverlapping = new Boolean[1];
        isOverlapping[0] = false;

        jWorld.queryAABB(new QueryCallback() {

            @Override
            public boolean reportFixture(Fixture fixture) {
                // Gosh, an overlap !
                isOverlapping[0] = true;
                return false; // Returning false to exit the reportFixture callback.
            }
        }, new AABB(new Vec2(x - (width / 2), y - (height / 2)), new Vec2(x + (width / 2), y + (height / 2))));

        return isOverlapping[0];
    }
}