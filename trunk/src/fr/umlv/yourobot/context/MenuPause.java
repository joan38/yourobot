package fr.umlv.yourobot.context;

import fr.umlv.yourobot.ApplicationCode;
import fr.umlv.yourobot.ApplicationCore;
import fr.umlv.yourobot.ApplicationRenderer;
import fr.umlv.yourobot.Manager;
import fr.umlv.yourobot.MusicPlayer;
import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.SoundPlayer;
import fr.umlv.yourobot.YouRobot;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Pause menu.
 *
 * @author Damien Girard
 */
public class MenuPause implements MenuManager, ApplicationCode, ApplicationRenderer {

    public enum MenuPauseResults {

        CONTINUE,
        RETRY,
        QUIT
    }
    private MenuPauseResults menuResult = MenuPauseResults.CONTINUE;

    public MenuPauseResults getMenuResult() {
        return menuResult;
    }

    /**
     * Create a menu.
     *
     * @param manager Manager to associate with the menu.
     */
    public MenuPause() {
    }

    @Override
    public void run(ApplicationCore core) {
        // Current menu displayed.
        MenuManager menu = this;

        // Drawing the menu.
        core.render(menu);

        // Managing the menu.
        for (;;) {
            if (core.isPressedAndEat(KeyEvent.VK_DOWN) || core.isPressedAndEat(KeyEvent.VK_S)) {
                if (menu.getSelectedIndex() < menu.getNumberOfElements() - 1) {
                    SoundPlayer.play("menu");
                    menu.setSelectedIndex(menu.getSelectedIndex() + 1);
                }
            } else if (core.isPressedAndEat(KeyEvent.VK_UP) || core.isPressedAndEat(KeyEvent.VK_Z)) {
                if (menu.getSelectedIndex() > 0) {
                    SoundPlayer.play("menu");
                    menu.setSelectedIndex(menu.getSelectedIndex() - 1);
                }
            } else if (core.isPressedAndEat(KeyEvent.VK_ENTER)) {
                // Enter.
                if (menu == this) {
                    switch (menu.getSelectedIndex()) {
                        case 0:
                            menuResult = MenuPauseResults.CONTINUE;
                            break;
                        case 1:
                            menuResult = MenuPauseResults.RETRY;
                            break;
                        case 2:
                            menuResult = MenuPauseResults.QUIT;
                            break;
                    }
                    return;
                }
            }else if (core.isPressedAndEat(KeyEvent.VK_ESCAPE)) {
                menuResult = MenuPauseResults.CONTINUE;
                return;
            }

            // Rendering the menu.
            core.render(menu);
        }
    }

    @Override
    public int getSelectedIndex() {
        return selectedIndex;
    }

    @Override
    public int getNumberOfElements() {
        return numberOfMenuElements;
    }

    @Override
    public void setSelectedIndex(int i) {
        selectedIndex = i;
    }
    private final String gameTitle = "HOOVER MAN";
    private final String gameSubTitle = "PAUSE";
    private final Font fontTitle = new Font("arial", Font.BOLD, 80);
    private final Font fontSubTitle1 = new Font("arial", Font.BOLD, 40);
    private final Font fontSubTitle2 = new Font("arial", Font.BOLD, 20);
    private final Color colorTitle = new Color(255, 68, 14);
    private final Color backColor = new Color(0, 0, 0);
    private final Font menuFont = new Font("arial", Font.BOLD, 30);
    private final Color menuColor = new Color(178, 0, 0);
    private int selectedIndex = 0;
    private int numberOfMenuElements = 3;

    @Override
    public void render(Graphics2D gd) {
        // Drawing the background.
        gd.setColor(backColor);
        gd.fillRect(0, 0, Settings.getWidth(), Settings.getHeight());

        // Drawing the title.
        gd.setPaint(colorTitle);
        gd.setFont(fontTitle);

        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawCenter(gd, gameTitle, 80);
        gd.setFont(fontSubTitle1);
        gd.setColor(gd.getColor().darker());
        drawCenter(gd, gameSubTitle, 130);
        gd.setFont(fontSubTitle2);

        // Drawing the menu.
        gd.setFont(menuFont);
        gd.setPaint(menuColor);
        float y = Settings.getHeight() / 2.8f;

        drawCenter(gd, drawMenuText("Continue", 0, getSelectedIndex()), y);
        y += 50;
        drawCenter(gd, drawMenuText("Restart this level", 1, getSelectedIndex()), y);
        y += 50;
        drawCenter(gd, drawMenuText("Exit to main menu", 2, getSelectedIndex()), y);

        AffineTransform textureTransformer = new AffineTransform();
        textureTransformer.setToIdentity();

        try {
            gd.drawImage(ImageIO.read(YouRobot.class
                    .getResource("/textures/controls.png")), new AffineTransformOp(textureTransformer, AffineTransformOp.TYPE_BILINEAR), 55, 350);
        } catch (IOException ex) {
            System.err.println("Texture not found. " + ex.getMessage());
        }
    }

    private static String drawMenuText(String str, int index, int selectedIndex) {
        if (index == selectedIndex) {
            return "[ " + str + " ]";
        } else {
            return str;
        }
    }

    /**
     * Draw the string centered.
     *
     * @param gd Graphic contect.
     * @param txt Text to draw.
     * @param y Y position.
     */
    private void drawCenter(Graphics2D gd, String txt, float y) {
        FontMetrics fm = gd.getFontMetrics();
        float border = (Settings.getWidth() - fm.stringWidth(txt)) / 2.0f;
        gd.drawString(txt, border, y);
    }
}
