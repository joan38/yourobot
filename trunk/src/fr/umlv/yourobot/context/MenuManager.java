package fr.umlv.yourobot.context;

import fr.umlv.zen.ApplicationRenderCode;

/**
 * Manage the logic of a menu.
 * 
 * License: GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public interface MenuManager extends ApplicationRenderCode {

    /**
     * Gets the currently selected index in the menu.
     * @return The selected index.
     */
    public int getSelectedIndex();

    /**
     * Gets the number of elements in the menu.
     * @return The number of elements in the menu.
     */
    public int getNumberOfElements();

    /**
     * Set the currently selected index in the menu.
     * @param i The new index.
     */
    public void setSelectedIndex(int i);
}
