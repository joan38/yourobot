package fr.umlv.yourobot.elements.bonus;

import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.SoundPlayer;
import fr.umlv.yourobot.elements.TypeElementBase;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import org.jbox2d.collision.shapes.CircleShape;

/**
 * Represent a Leurre bonus.
 *
 * License: GNU Public license v3.
 *
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Leurre extends Bonus {

    private final BufferedImage activatedTexture;
    private final BufferedImage beforeActivatedTexture;
    // JBox2D.
    private final CircleShape dynamicCircle;

    /**
     * Create a BombeMagnetique.
     *
     * @param typeElement Type of the bomb.
     * @param texture Texture to use.
     * @param activatedTexture Texture to use when the leurre is active.
     * @param beforeActivatedTexture Texture to use before the leurre is active.
     * @param durationOfBonusInSeconds Duration of the leurre.
     * @param x X position of the bonus.
     * @param y Y position of the bonus.
     */
    public Leurre(TypeElementBase typeElement, BufferedImage texture, BufferedImage activatedTexture, BufferedImage beforeActivatedTexture, int durationOfBonusInSeconds, int x, int y) {
        super(typeElement, texture, durationOfBonusInSeconds, x, y, false);

        this.dynamicCircle = new CircleShape();
        this.dynamicCircle.m_radius = (float) Settings.getSize() / 2.0f;

        getFixtureDef().shape = dynamicCircle;

        this.activatedTexture = activatedTexture;
        this.beforeActivatedTexture = beforeActivatedTexture;
    }

    // I override activateBonus just to mark the bonus as not activated.
    // Because when the bonus will be activated, the RobotIA will track this bonus.
    @Override
    public void activateBonus() {
        super.activateBonus();
        setState(BonusState.DisplayedButNotActive);

        this.setX(getRobot().getX());
        this.setY(getRobot().getY());
    }
    private long playSoundAt = 0;

    @Override
    public boolean stepBonus() {
        // Playing sound each seconds if activated.
        if (getState() == BonusState.Activated) {
            if (playSoundAt < Calendar.getInstance().getTimeInMillis()) {
                SoundPlayer.play("bonusLeurre");
                playSoundAt = Calendar.getInstance().getTimeInMillis() + 1000;
            }
        }

        // Leurre effect
        if (((Calendar.getInstance().getTimeInMillis() - getBonusActivationDate())) > Settings.getLeurreDurationBeforeActivation() * 1000) {
            if (((Calendar.getInstance().getTimeInMillis() - getBonusActivationDate())) > durationOfBonusInSeconds * 1000 + Settings.getLeurreDurationBeforeActivation() * 1000) {
                return false; // End of the bonus.
            }
            if (getState() == BonusState.DisplayedButNotActive) {
                // Re-activating the bonus in the physical world.
                getBody().setActive(true);
                setState(BonusState.Activated); // Activation of the bonus.
            }
            return true; // Activation of the bonus.
        }

        return true;
    }

    @Override
    public void render(Graphics2D gd) {
        switch (getState()) {
            case Activated:
                super.renderIgnoreBonusLogic(gd, activatedTexture);
                break;
            case DisplayedButNotActive:
                super.renderIgnoreBonusLogic(gd, beforeActivatedTexture);
                break;
            default:
                super.render(gd);
        }
    }

    @Override
    protected void renderDuration(Graphics2D gd) {
        // Render the duration.
        String duration = "";
        Long durationToDisplay = 0l;
        if (durationOfBonusInSeconds != 0) {
            duration = " " + durationOfBonusInSeconds.toString() +"s";
        }

        gd.setFont(new Font("Arial", Font.BOLD, 12));
        switch (getState()) {
            default:
                gd.setPaint(Color.yellow.darker());
                break;
            case DisplayedButNotActive:
                gd.setPaint(Color.red.darker());
                durationToDisplay = Settings.getLeurreDurationBeforeActivation() - Math.round((double) (Calendar.getInstance().getTimeInMillis() - getBonusActivationDate()) / 1000.0);
                break;
            case Activated:
                gd.setPaint(Color.lightGray);
                durationToDisplay = durationOfBonusInSeconds + Settings.getLeurreDurationBeforeActivation() - Math.round((double) (Calendar.getInstance().getTimeInMillis() - getBonusActivationDate()) / 1000.0);
                break;
        }

        if (getRobot() == null) {
            int x = (int) (this.getX() + Settings.getSize());
            int y = (int) (this.getY());

            // Drawing the text.
            gd.drawString(duration, x, y);
        } else {
            int x = (int) (this.getX() + Settings.getSize());
            int y = (int) (this.getY());
            gd.drawString(durationToDisplay.toString(), x, y);
        }
    }
}
