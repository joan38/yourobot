/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.umlv.yourobot.elements;

import fr.umlv.yourobot.elements.wall.Wall;
import fr.umlv.yourobot.elements.area.SimpleArea;
import fr.umlv.yourobot.elements.area.Area;
import fr.umlv.yourobot.elements.bonus.BombeMagnetique;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.YouRobotSetting;
import fr.umlv.yourobot.elements.bonus.Snap;
import fr.umlv.yourobot.elements.robot.RobotIA;
import java.awt.Color;
import java.io.IOException;
import java.util.Random;

/**
 * Factory that create sampleworlds.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class SampleWorldFactory {

    /**
     * Generate a sample world.
     */
    public static World getRandomDummyWorld() {
        // Creation of a dummy world.
        try {
            Area[] areas = new Area[3];
            areas[0] = new SimpleArea(YouRobotSetting.getSize() * 18, YouRobotSetting.getSize() * 3, Color.GREEN); // EndArea
            areas[1] = new SimpleArea(YouRobotSetting.getSize() * 3, YouRobotSetting.getSize() * 3, Color.yellow); // StartArea P1
            areas[2] = new SimpleArea(YouRobotSetting.getSize() * 3, YouRobotSetting.getSize() * 8, Color.blue); // StartArea P2


            World w = new World(TextureLoader.loadTexture("src/textures/metal_floor.jpg", false), areas);

            World.fillBorder(w, "src/textures/tube_texture.png");
            // Adding elements to this world.
            //w.addElement(new Wall(TypeElementBase.Ice, TextureLoader.loadTexture("src/textures/texture-glace.jpg"), 0, 0));
            //w.addElement(new Wall(TypeElementBase.Wood, TextureLoader.loadTexture("src/textures/texture-bois-bleu.jpg"), 50, 50));
            //Element e = new Wall(TypeElementBase.Stone, TextureLoader.loadTexture("src/textures/texture-brique-blanche.jpg"), 100, 100);
            Element e;

            // Generating 15 random walls.
            Random random = new Random(); // Seed set to prevent random :)
            for (int i = 0; i < 3; i++) {
                do {
                    e = new Wall(TypeElementBase.Ice, TextureLoader.loadTexture("src/textures/texture-glace.jpg", true),
                            random.nextInt(YouRobotSetting.getWidth() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize(),
                            random.nextInt(YouRobotSetting.getHeight() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize());
                } while (w.isOverlap(e.getX(), e.getY(), e.getTexture().getWidth(), e.getTexture().getHeight()));
                //e.setOrientation(random.nextInt(360));
                w.addElement(e);
                do {
                    e = new Wall(TypeElementBase.Wood, TextureLoader.loadTexture("src/textures/texture-bois-bleu.jpg", true),
                            random.nextInt(YouRobotSetting.getWidth() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize(),
                            random.nextInt(YouRobotSetting.getHeight() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize());
                } while (w.isOverlap(e.getX(), e.getY(), e.getTexture().getWidth(), e.getTexture().getHeight()));
                //e.setOrientation(random.nextInt(360));
                w.addElement(e);
                do {
                    e = new Wall(TypeElementBase.Stone, TextureLoader.loadTexture("src/textures/texture-brique-blanche.jpg", true),
                            random.nextInt(YouRobotSetting.getWidth() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize(),
                            random.nextInt(YouRobotSetting.getHeight() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize());
                } while (w.isOverlap(e.getX(), e.getY(), e.getTexture().getWidth(), e.getTexture().getHeight()));
                //e.setOrientation(random.nextInt(360));
                w.addElement(e);

            }

            Bonus b = new BombeMagnetique(TypeElementBase.Stone, TextureLoader.loadTexture("src/textures/bomb.png", true), 90, 90);
            w.addElement(b);

            b = new Snap(TypeElementBase.Stone, TextureLoader.loadTexture("src/textures/bomb.png", true), 2, 120, 120);
            w.addElement(b);

            // Adding a IA Robot to the world.
            RobotIA r = new RobotIA(TextureLoader.loadTexture("src/textures/robot_enemie.png", true),
                    160, 200);
            w.addRobotIA(r);


            return w;
        } catch (IOException ex) {
            System.err.println("Texture not found. " + ex.getMessage());
            return null;
        }
    }

    public static World getDummyWorldLevel1() {
        // Creation of a dummy world.
        try {
            Area[] areas = new Area[3];
            areas[1] = new SimpleArea(YouRobotSetting.getSize() + 40, YouRobotSetting.getHeight() / 2 - 40, Color.blue); // StartArea P1
            areas[2] = new SimpleArea(YouRobotSetting.getSize() + 40, YouRobotSetting.getHeight() / 2 + 40, Color.blue); // StartArea P2
            areas[0] = new SimpleArea(YouRobotSetting.getWidth() - (YouRobotSetting.getSize() + 50), YouRobotSetting.getHeight() / 2, Color.GREEN); // EndArea

            World w = new World(TextureLoader.loadTexture("src/textures/metal_floor.jpg", false), areas);
            World.fillBorder(w, "src/textures/tube_texture.png");

            // Adding elements to this world.
            for (int i = YouRobotSetting.getSize() + 110; i < 690; i += YouRobotSetting.getSize()) {
                w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture("src/textures/texture-brique-blanche.jpg", true), i, YouRobotSetting.getHeight() / 2 - 70));
            }

            for (int i = YouRobotSetting.getSize() + 110; i < 690; i += YouRobotSetting.getSize()) {
                w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture("src/textures/texture-brique-blanche.jpg", true), i, YouRobotSetting.getHeight() / 2 + 70));
            }

            // Adding a IA Robot to the world.
            RobotIA r = new RobotIA(TextureLoader.loadTexture("src/textures/robot_enemie.png", true),
                    160, 200);
             w.addRobotIA(r);

            return w;
        } catch (IOException ex) {
            System.err.println("Texture not found. " + ex.getMessage());
            return null;
        }
    }
}
