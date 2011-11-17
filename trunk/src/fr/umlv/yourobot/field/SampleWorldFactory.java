/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.umlv.yourobot.field;

import fr.umlv.yourobot.YouRobotSetting;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Damien Girard <dgirar02@etudiant.univ-mlv.fr>
 * @copyright GNU Public license v3.
 */
public class SampleWorldFactory {

    /**
     * Generate a sample world.
     */
    public static World getDummyWorld1() {
        // Creation of a dummy world.
        try {
            World w = new World(TextureLoader.loadTexture("src/textures/metal_floor.jpg"));

            World.fillBorder(w, "src/textures/tube_texture.png");
            // Adding elements to this world.
            //w.addElement(new Wall(TypeElementBase.Ice, TextureLoader.loadTexture("src/textures/texture-glace.jpg"), 0, 0));
            //w.addElement(new Wall(TypeElementBase.Wood, TextureLoader.loadTexture("src/textures/texture-bois-bleu.jpg"), 50, 50));
            //Element e = new Wall(TypeElementBase.Stone, TextureLoader.loadTexture("src/textures/texture-brique-blanche.jpg"), 100, 100);
            Element e;

            // Generating 15 random walls.
            Random random = new Random(); // Seed set to prevent random :)
            for (int i = 0; i < 5; i++) {
                e = new Wall(TypeElementBase.Ice, TextureLoader.loadTexture("src/textures/texture-glace.jpg"),
                        random.nextInt(YouRobotSetting.getWidth() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize(),
                        random.nextInt(YouRobotSetting.getHeight() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize());
                e.setOrientation(random.nextInt(360));
                w.addElement(e);
            }
            for (int i = 0; i < 5; i++) {
                e = new Wall(TypeElementBase.Wood, TextureLoader.loadTexture("src/textures/texture-bois-bleu.jpg"),
                        random.nextInt(YouRobotSetting.getWidth() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize(),
                        random.nextInt(YouRobotSetting.getHeight() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize());
                e.setOrientation(random.nextInt(360));
                w.addElement(e);
            }
            for (int i = 0; i < 5; i++) {
                e = new Wall(TypeElementBase.Stone, TextureLoader.loadTexture("src/textures/texture-brique-blanche.jpg"),
                        random.nextInt(YouRobotSetting.getWidth() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize(),
                        random.nextInt(YouRobotSetting.getHeight() - (YouRobotSetting.getSize() * 2)) + YouRobotSetting.getSize());
                e.setOrientation(random.nextInt(360));
                w.addElement(e);
            }

            return w;
        } catch (IOException ex) {
            System.err.println("Texture not found. " + ex.getMessage());
            return null;
        }
    }
}
