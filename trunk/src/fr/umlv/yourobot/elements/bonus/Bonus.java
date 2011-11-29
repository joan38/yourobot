package fr.umlv.yourobot.elements.bonus;

import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.elements.*;
import fr.umlv.yourobot.elements.Element;
import fr.umlv.yourobot.elements.robot.Robot;
import fr.umlv.yourobot.elements.TypeElementBase;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

/**
 * Represent a bonus.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public abstract class Bonus extends Element {

    private Robot robot;
    private long bonusActivationDate;
    final Integer durationOfBonusInSeconds;
    private BonusState state;

    /**
     * Current state of the bonus.
     */
    public enum BonusState {

        /** Placed on the world. */
        Placed, 
        /** Grabbed by a robot. */
        Grabbed,
        /** Activated by a robot. */
        Activated,
        /** The bonus is drawn on the world but not active. */
        DisplayedButNotActive
    }

    /**
     * A Bonus.
     * 
     * @param typeElement Type of the element.
     * @param texture Texture of the bonus.
     * @param durationOfBonusInSeconds Duration of the bonus.
     * @param x Initial x position of the bonus.
     * @param y Initial y position of the bonus.
     */
    public Bonus(TypeElementBase typeElement, BufferedImage texture, int durationOfBonusInSeconds, int x, int y) {
        super(typeElement, texture, x, y);

        // A bonus do not rotate.
        getBodyDef().fixedRotation = true;
        this.durationOfBonusInSeconds = durationOfBonusInSeconds;

        // A bonus is placed at the beggining.
        this.state = BonusState.Placed;
    }

    /**
     * Step the bonus, no effect if activateBonus was not activated.
     * 
     * @note The bonus effect will end after the duration of the bonus.
     * 
     * @return false is the bonus MUST BE DELETED. Because its effect has finished. true otherwise.
     */
    public abstract boolean stepBonus();

    /**
     * Call this method when a robot is grabbing the bonus.
     * Disable the box2d rendering and the rendering of the bonus.
     * 
     * Associate the bonus with a world and a robot.
     * 
     * @param robot Robot that activate the bonus.
     * 
     * @note The bonus is not removed from the world, just not visible. Do not forget to remove it when stepBonus return false.
     */
    public void grabBonus(Robot robot) {
        this.robot = robot;

        state = BonusState.Grabbed;
        getBody().setActive(false);
    }

    /**
     * Activate the bonus.
     * 
     * @note Call automatically stepBonus.
     */
    public void activateBonus() {
        state = BonusState.Activated;
        bonusActivationDate = Calendar.getInstance().getTimeInMillis();

        stepBonus();
    }

    /**
     * Get the current state of the bonus.
     * 
     * @return The state of the bonus.
     */
    public BonusState getState() {
        return state;
    }

    /**
     * Set the state of the bonus.
     * 
     * @param state State of the bonus.
     * 
     * @note Protected because other objects must not change the state of a bonus.
     */
    protected void setState(BonusState state) {
        this.state = state;
    }

    /**
     * Returns the activation date of the bonus.
     * 
     * @return The activation date of the bonus.
     */
    protected long getBonusActivationDate() {
        return bonusActivationDate;
    }

    /**
     * Get the robot associated with the bonus.
     * @return The robot associated.
     */
    protected Robot getRobot() {
        return robot;
    }
    
    // Rendering
    //
    @Override
    public void render(Graphics2D gd) {
        switch (state) {
            case DisplayedButNotActive:
            case Placed: // Bonus placed on the map, I display the texture.
                super.render(gd);
            case Activated: // Bonus activated on the map, I render the duration.
                renderDuration(gd);
            default:
        }
    }

    @Override
    protected void render(Graphics2D gd, BufferedImage texture) {
        switch (state) {
            case DisplayedButNotActive:
            case Placed: // Bonus placed on the map, I display the texture.
                super.render(gd, texture);
            case Activated: // Bonus activated on the map, I render the duration.
                renderDuration(gd);
            default:
        }
    }

    /**
     * This method do a rendering and ignore the bonus logic. (Activated/Placed etc... It render !)
     * 
     * @param gd Graphic to draw on.
     * @param texture The texture to draw.
     * 
     * @see render
     */
    protected void renderIgnoreBonusLogic(Graphics2D gd, BufferedImage texture) {
        super.render(gd, texture);
        renderDuration(gd);
    }

    /**
     * Render the duration of the bonus.
     * @param gd Graphics 2D to draw on.
     */
    protected void renderDuration(Graphics2D gd) {
        String duration = "";
        if (durationOfBonusInSeconds != 0) {
            duration = " " + durationOfBonusInSeconds.toString();
        }

        gd.setFont(new Font("Arial", Font.BOLD, 12));
        gd.setPaint(Color.yellow.darker());
        if (state != BonusState.Activated) {
            int x = (int) (this.getX() + Settings.getSize());
            int y = (int) (this.getY());

            // Drawing the text.
            gd.drawString(this.getTypeElement() + duration, x, y);
        } else {
            int x = (int) (robot.getX() + Settings.getSize());
            int y = (int) (robot.getY());
            Long tmp = durationOfBonusInSeconds - Math.round((double) (Calendar.getInstance().getTimeInMillis() - bonusActivationDate) / 1000.0);
            gd.drawString(tmp.toString(), x, y);
        }
    }

    // Factory
    //
    /**
     * Get a new random bonus.
     * 
     * @return A bonus.
     */
    public static Bonus getRandomBonus() {
        Random rand = new Random();
        TypeElementBase typeElement;

        switch (rand.nextInt(3)) {
            case 0:
                typeElement = TypeElementBase.Ice;
                break;
            case 1:
                typeElement = TypeElementBase.Wood;
                break;
            default:
                typeElement = TypeElementBase.Stone;
                break;
        }

        switch (rand.nextInt(3)) {
            case 0:
                try {
                    return new BombeMagnetique(typeElement, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            rand.nextInt(Settings.getWidth() - (Settings.getSize() * 2)) + Settings.getSize(),
                            rand.nextInt(Settings.getHeight() - (Settings.getSize() * 2)) + Settings.getSize());
                } catch (IOException ex) {
                    System.err.println("Error while loading texture : /textures/bomb.png");
                }
                break;
            case 1:
                try {
                    return new Snap(typeElement, TextureLoader.loadTexture(Bonus.class.getResource("/textures/snap.png"), true), rand.nextInt(4) + 1,
                            rand.nextInt(Settings.getWidth() - (Settings.getSize() * 2)) + Settings.getSize(),
                            rand.nextInt(Settings.getHeight() - (Settings.getSize() * 2)) + Settings.getSize());
                } catch (IOException ex) {
                    System.err.println("Error while loading texture : /textures/bomb.png");
                }
                break;
            default:
                try {
                    return new Leurre(typeElement, TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_activated.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_before_activated.png"), true),
                            rand.nextInt(5) + 5,
                            rand.nextInt(Settings.getWidth() - (Settings.getSize() * 2)) + Settings.getSize(),
                            rand.nextInt(Settings.getHeight() - (Settings.getSize() * 2)) + Settings.getSize());
                } catch (IOException ex) {
                    System.err.println("Error while loading texture : /textures/bomb.png");
                }
                break;
        }

        // Should never happen.
        throw new IllegalStateException("This case must not happen. return null");
    }
}
