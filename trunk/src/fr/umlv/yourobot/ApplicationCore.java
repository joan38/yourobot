package fr.umlv.yourobot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Handle the rendering of the application.
 *
 * @author Damien Girard
 */
public class ApplicationCore {

    private final JComponent canvas;
    private final BufferedImage buffer;
    private final BufferedImage backBuffer;
    private final Set<Integer> keyPressed = Collections.synchronizedSet(new HashSet<Integer>());
    private final Set<Integer> keyPressedRepeatKiller = Collections.synchronizedSet(new HashSet<Integer>());

    /**
     * Application context.
     *
     * @param canvas Swing canvas that will receive the input and render the
     * game.
     */
    public ApplicationCore(String title, int width, int height) {
        // Creation of the window.
        buffer = new BufferedImage(width, height, 2);
        backBuffer = new BufferedImage(width, height, 2);

        canvas = new JComponent() {
            private static final long serialVersionUID = 1360301844144298605L;

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(Settings.getWidth(), Settings.getHeight());
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(buffer, 0, 0, null);
            }
        };
        canvas.setBorder(BorderFactory.createEmptyBorder());
        JPanel panel = new JPanel();
        panel.add(canvas);

        JFrame frame = new JFrame(title);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(3);
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        // Registering keyboard events.
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                synchronized (keyPressed) {
                    if (!keyPressedRepeatKiller.contains(e.getKeyCode())) {
                        keyPressed.add(e.getKeyCode());
                        keyPressedRepeatKiller.add(e.getKeyCode());
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                synchronized (keyPressed) {
                    keyPressed.remove(e.getKeyCode());
                    keyPressedRepeatKiller.remove(e.getKeyCode());
                }
            }
        });

        // Game window created.
    }

    /**
     * Render the game. Handle the double buffering.
     *
     * @param gd Graphic device.
     */
    public void render(ApplicationRenderer renderer) {
        // Double buffer.
        Graphics2D g2bi = (Graphics2D) backBuffer.createGraphics();
        g2bi.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Calling the rendering.
        try {
            renderer.render(g2bi);
        } finally {
            g2bi.dispose();
        }

        // Refreshing the screen.
        buffer.createGraphics().drawImage(backBuffer, 0, 0, null);
        canvas.repaint();
    }

    /**
     * Returns true if the key is pressed.
     *
     * @param key Key to check.
     * @return True if the key is pressed.
     */
    public boolean isPressed(Integer key) {
        synchronized (keyPressed) {
            return keyPressed.contains(key);
        }
    }

    /**
     * Returns true if the key is pressed and remove it from the pressed key
     * list.
     *
     * Usefull to track the keyUp and then one more time keyDown event. Allow to
     * prevent multiple keydown grabbing.
     *
     * @param key Key to check.
     * @return True if the key is pressed.
     */
    public boolean isPressedAndEat(Integer key) {
        synchronized (keyPressed) {
            return keyPressed.remove(key);
        }
    }
}
