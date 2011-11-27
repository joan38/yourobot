package fr.umlv.yourobot.context;

import fr.umlv.zen.ApplicationRenderCode;

/**
 * Manage the logic of a menu.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public interface MenuManager extends ApplicationRenderCode {
    
    public int getSelectedIndex();
    public int getNumberOfElements();
    public void setSelectedIndex(int i);
    
}
