/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.umlv.yourobot.elements;

import fr.umlv.yourobot.elements.wall.Wall;
import fr.umlv.yourobot.elements.area.SimpleArea;
import fr.umlv.yourobot.elements.area.Area;
import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.context.WorldSet;
import fr.umlv.yourobot.elements.robot.RobotIA;
import java.awt.Color;
import java.io.IOException;
import java.util.Random;

/**
 * Factory that create sampleworlds.
 *
 * License: GNU Public license v3.
 *
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class SampleWorldFactory {

    /**
     * World set 1.
     *
     * @return A world set.
     */
    public static WorldSet getWorldSet1() {
        return new WorldSet() {
            private int index = -1;
            private final int numberOfWorlds = 4;

            private World getWorld(int i) {
                switch (i) {
                    case 0: // level 1
                        return SampleWorldFactory.getDummyWorldLevel1();
                    case 1: // level 2
                        return SampleWorldFactory.getRandomDummyWorld();
                    case 2: // level 3
                        return SampleWorldFactory.getDummyWorldLevel2();
                    case 3: // level 4
                        return SampleWorldFactory.getDummyWorldLevel3();
                }
                return null;
            }

            @Override
            public World getNextWorld() {
                index++;
                return getWorld(index);
            }

            @Override
            public World getReplayWorld() {
                return getWorld(index);
            }

            @Override
            public boolean hasMoreWorld() {
                return !(index + 1 >= numberOfWorlds);
            }

            @Override
            public void newGame() {
                index = -1;
            }

            @Override
            public String getNextHint() {
                switch (index) {
                    case 0:
                        return "Hey sir!\nhere is your fckin mission\nKill everybody and be the best !!!!!";
                }
                return null;
            }
        };
    }

    /**
     * Generate a sample world. Elements are placed randomly.
     *
     * @return A sample random world to be played.
     */
    public static World getRandomDummyWorld() {
        // Creation of a dummy world.
        try {
            Area[] areas = new Area[3];
            areas[1] = new SimpleArea(Settings.getSize() + 60, 200, Color.BLUE); // StartArea P1
            areas[2] = new SimpleArea(Settings.getSize() + 60, 450, Color.BLUE); // StartArea P2
            areas[0] = new SimpleArea(Settings.getSize() + 640, 290, Color.GREEN); // EndArea

            World w = new World("Random World", TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/metal_floor.jpg"), false), areas, "1");
            World.fillBorder(w, SampleWorldFactory.class.getResource("/textures/tube_texture.png"));

            Element e;
            // Generating 15 random walls.
            Random random = new Random(); // Seed set to prevent random :)
            for (int i = 0; i < 30; i++) {
                do {
                    e = new Wall(TypeElementBase.Ice, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-glace.jpg"), true),
                            random.nextInt(Settings.getWidth() - (Settings.getSize() * 2)) + Settings.getSize(),
                            random.nextInt(Settings.getHeight() - (Settings.getSize() * 2)) + Settings.getSize());
                } while (w.isOverlap(e.getX(), e.getY(), e.getTexture().getWidth(), e.getTexture().getHeight()));
                //e.setOrientation(random.nextInt(360));
                w.addElement(e);
                do {
                    e = new Wall(TypeElementBase.Wood, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-bois-bleu.jpg"), true),
                            random.nextInt(Settings.getWidth() - (Settings.getSize() * 2)) + Settings.getSize(),
                            random.nextInt(Settings.getHeight() - (Settings.getSize() * 2)) + Settings.getSize());
                } while (w.isOverlap(e.getX(), e.getY(), e.getTexture().getWidth(), e.getTexture().getHeight()));
                //e.setOrientation(random.nextInt(360));
                w.addElement(e);
                do {
                    e = new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                            random.nextInt(Settings.getWidth() - (Settings.getSize() * 2)) + Settings.getSize(),
                            random.nextInt(Settings.getHeight() - (Settings.getSize() * 2)) + Settings.getSize());
                } while (w.isOverlap(e.getX(), e.getY(), e.getTexture().getWidth(), e.getTexture().getHeight()));
                //e.setOrientation(random.nextInt(360));
                w.addElement(e);

            }

            // Adding a IA Robot to the world.
            for (int i = 0; i < 20; i++) {
                w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 400, 280));
            }

            return w;
        } catch (IOException ex) {
            System.err.println("Texture not found. " + ex.getMessage());
            return null;
        }
    }

    /**
     * Get the world level 1.
     *
     * @return The world.
     */
    public static World getDummyWorldLevel1() {
        // Creation of a dummy world.
        try {
            Area[] areas = new Area[3];
            areas[1] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 - 40, Color.BLUE); // StartArea P1
            areas[2] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 + 40, Color.BLUE); // StartArea P2
            areas[0] = new SimpleArea(Settings.getWidth() - (Settings.getSize() + 50), Settings.getHeight() / 2 - Settings.getSize() / 2, Color.GREEN); // EndArea

            World w = new World("Level 1", TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/metal_floor.jpg"), false), areas, "2");
            World.fillBorder(w, SampleWorldFactory.class.getResource("/textures/tube_texture.png"));

            // Adding elements to this world.
            for (int i = Settings.getSize() + 110; i < 690; i += Settings.getSize()) {
                w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true), i, Settings.getHeight() / 2 - Settings.getSize() / 2 - 70));
            }

            for (int i = Settings.getSize() + 110; i < 690; i += Settings.getSize()) {
                w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true), i, Settings.getHeight() / 2 - Settings.getSize() / 2 + 70));
            }

            // Adding a IA Robot to the world.
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 390, 120));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 390, 290));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 390, 470));

            return w;
        } catch (IOException ex) {
            System.err.println("Texture not found. " + ex.getMessage());
            return null;
        }
    }

    /**
     * Get the world level 2.
     *
     * @return The world.
     */
    public static World getDummyWorldLevel2() {
        // Creation of a dummy world.
        try {
            Area[] areas = new Area[3];
            areas[1] = new SimpleArea(Settings.getSize() + 30, 520, Color.BLUE); // StartArea P1
            areas[2] = new SimpleArea(Settings.getSize() + 100, 520, Color.BLUE); // StartArea P2
            areas[0] = new SimpleArea(Settings.getWidth() - (Settings.getSize() + 50), 520, Color.GREEN); // EndArea

            World w = new World("Level 2", TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/metal_floor.jpg"), false), areas, "3");
            World.fillBorder(w, SampleWorldFactory.class.getResource("/textures/tube_texture.png"));

            // Adding elements to this world.
            for (int i = 140; i < Settings.getHeight() - Settings.getSize(); i += Settings.getSize()) {
                w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true), 170, i));
            }

            for (int i = 0; i < 100; i += Settings.getSize()) {
                for (int j = 0; j < 100; j += Settings.getSize()) {
                    w.addElement(new Wall(TypeElementBase.Ice, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-glace.jpg"), true), i + 280, j + 80));
                }
            }

            for (int i = 180; i < Settings.getHeight() - Settings.getSize() - 100; i += Settings.getSize()) {
                w.addElement(new Wall(TypeElementBase.Wood, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-bois-bleu.jpg"), true), 360, i));
            }

            for (int i = 400; i < Settings.getHeight() - Settings.getSize(); i += Settings.getSize()) {
                w.addElement(new Wall(TypeElementBase.Wood, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-bois-bleu.jpg"), true), 680, i));
            }

            for (int i = 0; i < 100; i += Settings.getSize()) {
                for (int j = 0; j < 100; j += Settings.getSize()) {
                    w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true), i + 480, j + 390));
                }
            }

            // Adding a IA Robot to the world.
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 135, 45));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 135, 205));

            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 310, 45));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 310, 205));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 310, 375));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 310, 540));

            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 470, 45));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 470, 205));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 470, 375));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 470, 540));

            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 645, 45));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 645, 205));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 645, 375));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 645, 540));

            return w;
        } catch (IOException ex) {
            System.err.println("Texture not found. " + ex.getMessage());
            return null;
        }
    }

    /**
     * Get the world level 2.
     *
     * @return The world.
     */
    public static World getDummyWorldLevel3() {
        // Creation of a dummy world.
        try {
            Area[] areas = new Area[3];
            areas[1] = new SimpleArea(Settings.getSize() + 30, Settings.getHeight() / 2 - Settings.getSize() / 2 - 170, Color.BLUE); // StartArea P1
            areas[2] = new SimpleArea(Settings.getSize() + 30, Settings.getHeight() / 2 - Settings.getSize() / 2 + 170, Color.BLUE); // StartArea P2
            areas[0] = new SimpleArea(Settings.getWidth() - (Settings.getSize() + 50), Settings.getHeight() / 2 - Settings.getSize() / 2, Color.GREEN); // EndArea

            World w = new World("Level 3", TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/metal_floor.jpg"), false), areas, "4");
            World.fillBorder(w, SampleWorldFactory.class.getResource("/textures/tube_texture.png"));

            // Adding elements to this world.
            for (int i = 0; i < 100; i += Settings.getSize()) {
                for (int j = 0; j < 100; j += Settings.getSize()) {
                    for (int k = 0; k < 4; k++) {
                        w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true), i + Settings.getSize() + 75 + k * 170, j + Settings.getSize() + 60));
                    }
                }
            }

            for (int i = 0; i < 100; i += Settings.getSize()) {
                for (int j = 0; j < 100; j += Settings.getSize()) {
                    for (int k = 0; k < 4; k++) {
                        w.addElement(new Wall(TypeElementBase.Wood, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-bois-bleu.jpg"), true), i + Settings.getSize() + 75 + k * 170, j + Settings.getSize() + 230));
                    }
                }
            }

            for (int i = 0; i < 100; i += Settings.getSize()) {
                for (int j = 0; j < 100; j += Settings.getSize()) {
                    for (int k = 0; k < 4; k++) {
                        w.addElement(new Wall(TypeElementBase.Ice, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-glace.jpg"), true), i + Settings.getSize() + 75 + k * 170, j + Settings.getSize() + 400));
                    }
                }
            }

            // Adding a IA Robot to the world.
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 135, 45));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 135, 205));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 135, 375));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 135, 540));

            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 310, 45));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 310, 205));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 310, 375));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 310, 540));

            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 470, 45));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 470, 205));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 470, 375));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 470, 540));

            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 645, 45));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 645, 205));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 645, 375));
            w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true), 645, 540));

            return w;
        } catch (IOException ex) {
            System.err.println("Texture not found. " + ex.getMessage());
            return null;
        }
    }
}
