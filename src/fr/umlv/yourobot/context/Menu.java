package fr.umlv.yourobot.context;

import fr.umlv.yourobot.Manager;
import fr.umlv.yourobot.MusicPlayer;
import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.SoundPlayer;
import fr.umlv.yourobot.YouRobot;
import fr.umlv.zen.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;

/**
 * Manage the logic of the application.
 *
 * License: GNU Public license v3.
 *
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Menu implements ApplicationCode, ApplicationRenderCode, MenuManager {

    private final Manager manager;

    /**
     * Create a menu.
     *
     * @param manager Manager to associate with the menu.
     */
    public Menu(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void run(ApplicationContext context) {
        // Current menu displayed.
        MenuManager menu = this;

        // Game management.
        int numberOfPlayers = 0;
        int currentMapIndex = 0;
        Manager.Difficuly difficuly = Manager.Difficuly.BringItOn;
        Game game = null;

        // Drawing the menu.
        context.render(menu);

        // Playing the intro sound.
        MusicPlayer.getMusiquePlayer().playMusic("intro");

        // Managing the menu.
        for (;;) {
            final KeyboardEvent event = context.waitKeyboard();
            if (event == null) {
                return;
            }

            switch (event.getKey()) {
                case DOWN:
                case S:
                    if (menu.getSelectedIndex() < menu.getNumberOfElements() - 1) {
                        SoundPlayer.play("menu");
                        menu.setSelectedIndex(menu.getSelectedIndex() + 1);
                    }
                    break;
                case UP:
                case Z:
                    if (menu.getSelectedIndex() > 0) {
                        SoundPlayer.play("menu");
                        menu.setSelectedIndex(menu.getSelectedIndex() - 1);
                    }
                    break;
                case UNDEFINED:
                    // Enter.
                    if (menu == this) {
                        // One or two player screen (Main screen) ?
                        SoundPlayer.play("menuEnter");
                        numberOfPlayers = menu.getSelectedIndex() + 1;
                        menu = new MenuSelectDifficuly();
                        game = null;
                    } else if (menu instanceof MenuSelectDifficuly) {
                        // New game.
                        difficuly = Manager.Difficuly.values()[menu.getSelectedIndex()];
                        currentMapIndex = 0;
                        manager.newGame();
                        game = manager.newGame(manager.getMaps().getNextWorld(), false, numberOfPlayers, difficuly, 0);
                    } else if (menu instanceof MenuVictory) {
                        // Victory or loosing menu.
                        if (game.getVictoriousPlayer() == 0) {
                            switch (menu.getSelectedIndex()) {
                                case 0:
                                    // Retry
                                    game = manager.newGame(manager.getMaps().getReplayWorld(), true, numberOfPlayers, difficuly, 0);
                                    break;
                                case 1:
                                    // Retry with life boost
                                    game = manager.newGame(manager.getMaps().getReplayWorld(), true, numberOfPlayers, difficuly, 50);
                                    break;
                                default:
                                    // Exit.
                                    // Go back to menu.
                                    game = null;
                                    menu = this;
                            }
                        } else {
                            // Next Level
                            currentMapIndex++;
                            game = manager.newGame(manager.getMaps().getNextWorld(), false, numberOfPlayers, difficuly, 0);
                        }
                    } else if (menu instanceof MenuGameHint) {
                        // Next Level
                        currentMapIndex++;
                        game = manager.newGame(manager.getMaps().getNextWorld(), false, numberOfPlayers, difficuly, 0);
                    } else if (menu instanceof MenuGameOver) {
                        // Game over menu. Go back to menu.
                        menu = this;
                        game = null;
                    }

                    // If a game was loaded, then I launch it.
                    if (game != null) {
                        game.run(context); // Launching the game.

                        // Game ended, what is the result ?
                        if (game.getVictoriousPlayer() == 0) {
                            menu = new MenuVictory(game.getVictoriousPlayer());
                        } else {
                            // No more map.
                            if (manager.getMaps().hasMoreWorld() == false) {
                                menu = new MenuGameOver();
                            } else {
                                // If there is a game hint and I win, I show it.
                                if (manager.getMaps().getNextHint() == null || game.getVictoriousPlayer() == 0) {
                                    menu = new MenuVictory(game.getVictoriousPlayer());
                                } else {
                                    menu = new MenuGameHint(manager.getMaps().getNextHint());
                                }
                            }
                        }
                    }

                    break;
            }

            // Rendering the menu.
            context.render(menu);
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
    private final String gameSubTitle = "AGAINST MR. MAD";
    private final Font fontTitle = new Font("arial", Font.BOLD, 80);
    private final Font fontSubTitle1 = new Font("arial", Font.BOLD, 40);
    private final Font fontSubTitle2 = new Font("arial", Font.BOLD, 20);
    private final Font fontHint = new Font("arial", Font.BOLD | Font.ITALIC, 30);
    private final Color colorHint = new Color(130, 130, 130); // TODO
    private final Color colorTitle = new Color(255, 68, 14);
    private final Color backColor = new Color(0, 0, 0);
    private final Font menuFont = new Font("arial", Font.BOLD, 30);
    private final Color menuColor = new Color(178, 0, 0);
    private int selectedIndex = 0;
    private int numberOfMenuElements = 2;

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
        gd.setColor(gd.getColor().darker());
        drawCenter(gd, "V" + YouRobot.versionMajor + "." + YouRobot.versionMinor, 150);

        // Drawing the menu.
        gd.setFont(menuFont);
        gd.setPaint(menuColor);
        float y = Settings.getHeight() / 2.5f;


        drawCenter(gd, drawMenuText("New Game", 0, getSelectedIndex()), y);
        y += 50;
        drawCenter(gd, drawMenuText("New Game - 2 Player", 1, getSelectedIndex()), y);

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

    /**
     * Display the victory or game over menu.
     */
    private class MenuVictory implements MenuManager {

        private final int playerThatHaveWon;
        private int selectedIndex = 0;

        public MenuVictory(int playerThatHaveWon) {
            this.playerThatHaveWon = playerThatHaveWon;
        }

        @Override
        public int getSelectedIndex() {
            return selectedIndex;
        }

        @Override
        public void setSelectedIndex(int i) {
            selectedIndex = i;
        }

        @Override
        public int getNumberOfElements() {
            if (playerThatHaveWon == 0) {
                return 3;
            } else {
                return 2;
            }
        }

        @Override
        public void render(Graphics2D gd) {
            // Drawing the background.
            gd.setColor(backColor);
            gd.fillRect(0, 0, Settings.getWidth(), Settings.getHeight());

            // Drawing the title.
            gd.setPaint(colorTitle);
            gd.setFont(fontTitle);

            gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (playerThatHaveWon == 0) {
                drawCenter(gd, "YOU LOOSE", 80);
                gd.setFont(fontSubTitle2);
                gd.setColor(gd.getColor().darker());
                drawCenter(gd, "Instead of writing Java, why not try again?", 130);

                // Drawing menu.
                gd.setFont(menuFont);
                gd.setPaint(menuColor);
                float y = Settings.getHeight() / 2.5f;
                drawCenter(gd, drawMenuText("RETRY", 0, getSelectedIndex()), y);
                y += 60;
                drawCenter(gd, drawMenuText("RETRY WITH 50 MORE HEALTH", 1, getSelectedIndex()), y);
                y += 60;
                drawCenter(gd, drawMenuText("GO BACK TO MENU", 2, getSelectedIndex()), y);
            } else {
                drawCenter(gd, "YOU WIN", 80);
                gd.setFont(fontSubTitle2);
                gd.setColor(gd.getColor().darker());
                drawCenter(gd, "Hey master, next level?", 130);

                // Drawing menu.
                gd.setFont(menuFont);
                gd.setPaint(menuColor);
                float y = Settings.getHeight() / 2.5f;
                drawCenter(gd, drawMenuText("NEXT LEVEL", 0, getSelectedIndex()), y);
                y += 60;
                drawCenter(gd, drawMenuText("GO BACK TO MENU", 1, getSelectedIndex()), y);
            }
        }
    }

    /**
     * Game Over menu.
     */
    private class MenuGameOver implements MenuManager {

        @Override
        public void render(Graphics2D gd) {
            // Drawing the background.
            gd.setColor(backColor);
            gd.fillRect(0, 0, Settings.getWidth(), Settings.getHeight());

            // Drawing the title.
            gd.setPaint(colorTitle);
            gd.setFont(fontTitle);

            gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            drawCenter(gd, "GAME OVER", 80);
            gd.setFont(fontSubTitle1);
            gd.setColor(gd.getColor().darker());
            drawCenter(gd, "!! YOU ARE AN HERO !!", 190);

            // Drawing menu.
            gd.setFont(menuFont);
            gd.setPaint(menuColor);
            float y = Settings.getHeight() / 2.0f;
            drawCenter(gd, "[ GO BACK TO MENU ]", y);
        }

        @Override
        public int getNumberOfElements() {
            return 1;
        }

        @Override
        public int getSelectedIndex() {
            return 0;
        }

        @Override
        public void setSelectedIndex(int i) {
            return;
        }
    }

    /**
     * Menu with an hint.
     */
    private class MenuGameHint implements MenuManager {

        private final String gameHint;
        private final int numberOfLines;
        private final FontRenderContext fontRenderContext = new FontRenderContext(new AffineTransform(), true, false);

        public MenuGameHint(String gameHint) {
            this.gameHint = gameHint;

            int count = 0;
            for (int i = 0; i < gameHint.length(); i++) {
                if (gameHint.charAt(i) == '\n') {
                    count++;
                }
            }
            numberOfLines = count;
        }

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
            gd.setFont(fontHint);
            gd.setColor(colorHint);

            // Drawing the text hint.
            float lineHeight = fontHint.getLineMetrics(gameHint, fontRenderContext).getHeight();
            float y = (float) ((Settings.getHeight() / 2.5) - Math.round(numberOfLines / 2.0) * lineHeight);

            StringTokenizer st = new StringTokenizer(gameHint, "\n");
            while (st.hasMoreTokens()) {
                String textLine = st.nextToken();

                y += lineHeight;
                drawCenter(gd, textLine, y);
            }

            // Drawing menu.
            gd.setFont(menuFont);
            gd.setPaint(menuColor);
            y = Settings.getHeight() / 1.2f;
            drawCenter(gd, "[ LET'S GO ]", y);
        }

        @Override
        public int getNumberOfElements() {
            return 1;
        }

        @Override
        public int getSelectedIndex() {
            return 0;
        }

        @Override
        public void setSelectedIndex(int i) {
            return;
        }
    }

    /**
     * Select a difficulty.
     */
    private class MenuSelectDifficuly implements MenuManager {

        private int selectedIndex = 0;
        private final int numberOfMenuElements = Manager.Difficuly.values().length;

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
            gd.setColor(gd.getColor().darker());
            drawCenter(gd, "Difficulty", 150);

            // Drawing difficulties
            gd.setFont(menuFont);
            gd.setPaint(menuColor);
            float y = Settings.getHeight() / 3.0f;

            int i = 0;
            for (Manager.Difficuly d : Manager.Difficuly.values()) {
                if (i == selectedIndex) {
                    drawCenter(gd, "[ " + d.toString() + " ]", y);
                } else {
                    drawCenter(gd, d.toString(), y);
                }
                y += 50;
                i++;
            }
        }

        @Override
        public int getSelectedIndex() {
            return selectedIndex;
        }

        @Override
        public void setSelectedIndex(int i) {
            selectedIndex = i;
        }

        @Override
        public int getNumberOfElements() {
            return numberOfMenuElements;
        }
    }
}
