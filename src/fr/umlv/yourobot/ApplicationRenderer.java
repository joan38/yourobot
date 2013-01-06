/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.umlv.yourobot;

import java.awt.Graphics2D;

/**
 *
 * @author Damien
 */
public interface ApplicationRenderer {

    /**
     * Render the game.
     *
     * @param gd Graphic device.
     */
    public void render(Graphics2D gd);
}
