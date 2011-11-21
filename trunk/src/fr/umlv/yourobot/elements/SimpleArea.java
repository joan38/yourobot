package fr.umlv.yourobot.elements;

import fr.umlv.yourobot.YouRobotSetting;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;

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

    public SimpleArea(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    @Override
    public void render(Graphics2D gd) {
        gd.setStroke(new BasicStroke(2.0f));
        gd.setPaint(color);
        gd.drawOval(x, y, YouRobotSetting.getSize(), YouRobotSetting.getSize() - (YouRobotSetting.getSize() / 4));
    }
}
