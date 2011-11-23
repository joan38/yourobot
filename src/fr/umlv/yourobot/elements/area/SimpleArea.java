package fr.umlv.yourobot.elements.area;

import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.elements.TypeElementBase;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;

/**
 * Represent an area.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class SimpleArea extends Area {

    private final Color color;
    // JBox2D.
    private final CircleShape dynamicCircle;

    public SimpleArea(int x, int y, Color color) {
        super(TypeElementBase.Unasigned, null, x, y);

        this.color = color;

        // Init of JBox2D.
        getBodyDef().type = BodyType.STATIC; // An area is static.

        this.dynamicCircle = new CircleShape();
        this.dynamicCircle.m_radius = (float) YouRobotSetting.getSize() / 2.0f;

        getFixtureDef().shape = dynamicCircle;
    }

    @Override
    public void render(Graphics2D gd) {
        gd.setStroke(new BasicStroke(2.0f));
        gd.setPaint(color);
        if (getBody() != null) {
            gd.drawOval((int) getBody().getPosition().x, (int) getBody().getPosition().y, YouRobotSetting.getSize(), YouRobotSetting.getSize());
        }
    }
}
