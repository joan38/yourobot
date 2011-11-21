package fr.umlv.yourobot.elements;

import fr.umlv.yourobot.YouRobotSetting;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/**
 * Represent an area.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class SimpleArea implements Area {

    private final int x;
    private final int y;
    private final Color color;
    // JBox2D
    private Body body;
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    public SimpleArea(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;

        // JBox2D.
        this.bodyDef = new BodyDef();
        this.fixtureDef = new FixtureDef();

        // Initial position.
        this.bodyDef.position = new Vec2(x, y);
        this.bodyDef.userData = (Object) this; // Associating this object with the body.
        
        bodyDef.type = BodyType.STATIC; // An area is static.
        
    }

    @Override
    public void render(Graphics2D gd) {
        gd.setStroke(new BasicStroke(2.0f));
        gd.setPaint(color);
        gd.drawOval(x, y, YouRobotSetting.getSize(), YouRobotSetting.getSize() - (YouRobotSetting.getSize() / 4));
    }
}
