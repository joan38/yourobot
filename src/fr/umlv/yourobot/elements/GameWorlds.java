/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.umlv.yourobot.elements;

import fr.umlv.yourobot.Settings;
import fr.umlv.yourobot.context.WorldSet;
import fr.umlv.yourobot.elements.area.Area;
import fr.umlv.yourobot.elements.area.SimpleArea;
import fr.umlv.yourobot.elements.bonus.BombeMagnetique;
import fr.umlv.yourobot.elements.bonus.Bonus;
import fr.umlv.yourobot.elements.bonus.Leurre;
import fr.umlv.yourobot.elements.bonus.Snap;
import fr.umlv.yourobot.elements.robot.RobotIA;
import fr.umlv.yourobot.elements.wall.Barres;
import fr.umlv.yourobot.elements.wall.Wall;
import java.awt.Color;
import java.io.IOException;

/**
 *
 * @author Damien
 */
public class GameWorlds implements WorldSet {

    private int worldIndex = -1;

    @Override
    public World getNextWorld() {
        worldIndex++;
        return getWorld(worldIndex);
    }

    @Override
    public String getNextHint() {
        switch (worldIndex + 1) {
            case 1:
                return "We will need to do some explosion\nin order to pass !!";
            case 2:
                return "There is some guard here.\nThey will try to hit us!!";
            case 3:
                return "We need to grab the attention of the ennemis\nin order to pass!";
            case 5:
                return "Nice, we took the elevator.\nLet's go to the bunker of Mr. Mad!";
            case 6:
                return "Meeeennn!!!\nWe must run quick!!!";
            case 7:
                return "We are in the center, let's make big explosions\nand run away fast!";
            case 8:
                return "We got the secret documents!\nGooooooooooooooo!!!!!!!!";
            case 9:
                return "You are an hero, we are proud of you!\nWith this plans we are now able to stop Dr. MAD\nGreat Success!!!!";
            default:
                return null;
        }
    }

    @Override
    public World getReplayWorld() {
        return getWorld(worldIndex);
    }

    @Override
    public boolean hasMoreWorld() {
        return worldIndex < 7;
    }

    @Override
    public void newGame() {
        worldIndex = -1;
    }

    private World getWorld(int worldIndex) {
        // Grid_X is 40
        // Grid_Y is 30
        try {
            worldIndex = 8;
            switch (worldIndex) {
                case 0: {
                    // Start and end areas.
                    Area[] areas = new Area[3];
                    areas[1] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 - 40, Color.BLUE); // StartArea P1
                    areas[2] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 + 40, Color.BLUE); // StartArea P2
                    areas[0] = new SimpleArea(Settings.getWidth() - (Settings.getSize() + 50), Settings.getHeight() / 2 - Settings.getSize() / 2, Color.GREEN); // EndArea

                    // Creation of the world.
                    World w = new World("Level 1", TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/metal_floor.jpg"), false), areas, "2");
                    World.fillBorder(w, SampleWorldFactory.class.getResource("/textures/tube_texture.png"));

                    // Placing elements.
                    for (int i = 0; i < Settings.getGridSizeY() - 10; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                10 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 10; i < Settings.getGridSizeY(); i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                30 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    // Placing bonus.

                    // Placing IA.

                    // Level created.
                    return w;
                }
                case 1: {
                    // Start and end areas.
                    Area[] areas = new Area[3];
                    areas[1] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 - 40, Color.BLUE); // StartArea P1
                    areas[2] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 + 40, Color.BLUE); // StartArea P2
                    areas[0] = new SimpleArea(Settings.getWidth() - (Settings.getSize() + 50), Settings.getHeight() / 2 - Settings.getSize() / 2, Color.GREEN); // EndArea

                    // Creation of the world.
                    World w = new World("Level 2", TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/metal_floor.jpg"), false), areas, "2");
                    World.fillBorder(w, SampleWorldFactory.class.getResource("/textures/tube_texture.png"));

                    // Placing elements.
                    for (int i = 0; i < 10; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                10 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 10; i < 20; i++) {
                        w.addElement(new Wall(TypeElementBase.Wood, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-bois-bleu.jpg"), true),
                                10 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 20; i < 30; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                10 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    // Second row to explode.
                    for (int i = 0; i < 10; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                30 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 10; i < 20; i++) {
                        w.addElement(new Wall(TypeElementBase.Ice, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-glace.jpg"), true),
                                30 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 20; i < 30; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                30 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    // Placing bonus.
                    w.addElement(new BombeMagnetique(TypeElementBase.Wood, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            8 * Settings.getSize(),
                            12 * Settings.getSize()));

                    w.addElement(new BombeMagnetique(TypeElementBase.Wood, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            8 * Settings.getSize(),
                            14 * Settings.getSize()));

                    w.addElement(new BombeMagnetique(TypeElementBase.Wood, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            8 * Settings.getSize(),
                            16 * Settings.getSize()));

                    w.addElement(new BombeMagnetique(TypeElementBase.Wood, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            8 * Settings.getSize(),
                            18 * Settings.getSize()));

                    // Second row.
                    w.addElement(new BombeMagnetique(TypeElementBase.Ice, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            28 * Settings.getSize(),
                            12 * Settings.getSize()));

                    w.addElement(new BombeMagnetique(TypeElementBase.Ice, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            28 * Settings.getSize(),
                            14 * Settings.getSize()));

                    w.addElement(new BombeMagnetique(TypeElementBase.Ice, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            28 * Settings.getSize(),
                            16 * Settings.getSize()));

                    w.addElement(new BombeMagnetique(TypeElementBase.Ice, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            28 * Settings.getSize(),
                            18 * Settings.getSize()));

                    // Placing IA.

                    // Level created.
                    return w;
                }
                case 2: {
                    // Start and end areas.
                    Area[] areas = new Area[3];
                    areas[1] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 - 40, Color.BLUE); // StartArea P1
                    areas[2] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 + 40, Color.BLUE); // StartArea P2
                    areas[0] = new SimpleArea(Settings.getWidth() - (Settings.getSize() + 50), Settings.getHeight() / 2 - Settings.getSize() / 2, Color.GREEN); // EndArea

                    // Creation of the world.
                    World w = new World("Level 3", TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/metal_floor.jpg"), false), areas, "2");
                    World.fillBorder(w, SampleWorldFactory.class.getResource("/textures/tube_texture.png"));

                    // Placing elements.
                    for (int i = 0; i < 12; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                10 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 18; i < 30; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                10 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    // Placing IA.
                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            15 * Settings.getSize(),
                            5 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            15 * Settings.getSize(),
                            25 * Settings.getSize()));

                    // Second walls.
                    for (int i = 5; i < 25; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                20 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    // Third walls.
                    for (int i = 30; i < 35; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                i * Settings.getSize(),
                                10 * Settings.getSize()));
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                i * Settings.getSize(),
                                20 * Settings.getSize()));
                    }

                    for (int i = 0; i <= 10; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                35 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 20; i < 30; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                35 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    // Wall to explode.
                    for (int i = 11; i <= 19; i++) {
                        w.addElement(new Wall(TypeElementBase.Wood, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-bois-bleu.jpg"), true),
                                30 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    // Placing bonus.
                    for (int i = 12; i <= 18; i += 2) {
                        w.addElement(new BombeMagnetique(TypeElementBase.Wood, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                                28 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    // Level created.
                    return w;
                }
                case 3: {
                    // Start and end areas.
                    Area[] areas = new Area[3];
                    areas[1] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 - 40, Color.BLUE); // StartArea P1
                    areas[2] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 + 40, Color.BLUE); // StartArea P2
                    areas[0] = new SimpleArea(Settings.getWidth() - (Settings.getSize() + 50), Settings.getHeight() / 2 - Settings.getSize() / 2, Color.GREEN); // EndArea

                    // Creation of the world.
                    World w = new World("Level 4", TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/metal_floor.jpg"), false), areas, "2");
                    World.fillBorder(w, SampleWorldFactory.class.getResource("/textures/tube_texture.png"));

                    // Placing elements.
                    w.addElement(new Leurre(TypeElementBase.Unasigned, TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_activated.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_before_activated.png"), true),
                            20,
                            5 * Settings.getSize(),
                            5 * Settings.getSize()));

                    w.addElement(new Leurre(TypeElementBase.Unasigned, TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_activated.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_before_activated.png"), true),
                            20,
                            5 * Settings.getSize(),
                            25 * Settings.getSize()));


                    for (int i = 0; i <= 5; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                10 * Settings.getSize(),
                                i * Settings.getSize()));

                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                20 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 25; i < 30; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                10 * Settings.getSize(),
                                i * Settings.getSize()));

                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                20 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 15; i < 20; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                i * Settings.getSize(),
                                5 * Settings.getSize()));

                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                i * Settings.getSize(),
                                25 * Settings.getSize()));
                    }

                    for (int i = 6; i <= 23; i++) {
                        w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                20 * Settings.getSize(),
                                i * Settings.getSize() + 8));

                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                32 * Settings.getSize(),
                                i * Settings.getSize() + 8));
                    }

                    for (int i = 24; i <= 28; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                i * Settings.getSize(),
                                5 * Settings.getSize()));

                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                i * Settings.getSize(),
                                25 * Settings.getSize()));
                    }

                    // Placing bonus.
                    w.addElement(new BombeMagnetique(TypeElementBase.Stone, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            17 * Settings.getSize(),
                            3 * Settings.getSize()));


                    w.addElement(new BombeMagnetique(TypeElementBase.Stone, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            17 * Settings.getSize(),
                            27 * Settings.getSize()));

                    // Placing IA.
                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            12 * Settings.getSize(),
                            3 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            12 * Settings.getSize(),
                            27 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            23 * Settings.getSize(),
                            3 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            23 * Settings.getSize(),
                            27 * Settings.getSize()));

                    // Level created.
                    return w;
                }
                case 4: {
                    // Start and end areas.
                    Area[] areas = new Area[3];
                    areas[1] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 - 40, Color.BLUE); // StartArea P1
                    areas[2] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 + 40, Color.BLUE); // StartArea P2
                    areas[0] = new SimpleArea(Settings.getWidth() - (Settings.getSize() + 50), Settings.getHeight() / 2 - Settings.getSize() / 2, Color.GREEN); // EndArea

                    // Creation of the world.
                    World w = new World("Level 5", TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/metal_floor.jpg"), false), areas, "2");
                    World.fillBorder(w, SampleWorldFactory.class.getResource("/textures/tube_texture.png"));

                    // Placing elements.
                    w.addElement(new Leurre(TypeElementBase.Unasigned, TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_activated.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_before_activated.png"), true),
                            20,
                            5 * Settings.getSize(),
                            5 * Settings.getSize()));

                    w.addElement(new Leurre(TypeElementBase.Unasigned, TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_activated.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_before_activated.png"), true),
                            20,
                            5 * Settings.getSize(),
                            25 * Settings.getSize()));

                    // Walls
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                    i * Settings.getSize() + 175,
                                    j * Settings.getSize() + 120));
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                    i * Settings.getSize() + 375,
                                    j * Settings.getSize() + 120));
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                    i * Settings.getSize() + 575,
                                    j * Settings.getSize() + 120));
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                    i * Settings.getSize() + 175,
                                    j * Settings.getSize() + 270));
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                    i * Settings.getSize() + 375,
                                    j * Settings.getSize() + 270));
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                    i * Settings.getSize() + 575,
                                    j * Settings.getSize() + 270));
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                    i * Settings.getSize() + 175,
                                    j * Settings.getSize() + 420));
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                    i * Settings.getSize() + 375,
                                    j * Settings.getSize() + 420));
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                    i * Settings.getSize() + 575,
                                    j * Settings.getSize() + 420));
                        }
                    }

                    // Placing bonus.
                    w.addElement(new BombeMagnetique(TypeElementBase.Stone, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            17 * Settings.getSize(),
                            3 * Settings.getSize()));


                    w.addElement(new BombeMagnetique(TypeElementBase.Stone, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            17 * Settings.getSize(),
                            27 * Settings.getSize()));

                    // Placing IA.
                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            12 * Settings.getSize(),
                            3 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            12 * Settings.getSize(),
                            27 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            23 * Settings.getSize(),
                            3 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            23 * Settings.getSize(),
                            27 * Settings.getSize()));

                    // Level created.
                    return w;
                }
                case 5: {
                    // Start and end areas.
                    Area[] areas = new Area[3];
                    areas[1] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 - 40, Color.BLUE); // StartArea P1
                    areas[2] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 + 40, Color.BLUE); // StartArea P2
                    areas[0] = new SimpleArea(Settings.getWidth() - (Settings.getSize() + 50), Settings.getHeight() / 2 - Settings.getSize() / 2, Color.GREEN); // EndArea

                    // Creation of the world.
                    World w = new World("Level 6", TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/metal_floor.jpg"), false), areas, "2");
                    World.fillBorder(w, SampleWorldFactory.class.getResource("/textures/tube_texture.png"));

                    // Placing elements.
                    w.addElement(new Leurre(TypeElementBase.Unasigned, TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_activated.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_before_activated.png"), true),
                            20,
                            5 * Settings.getSize(),
                            5 * Settings.getSize()));

                    w.addElement(new Leurre(TypeElementBase.Unasigned, TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_activated.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_before_activated.png"), true),
                            20,
                            5 * Settings.getSize(),
                            25 * Settings.getSize()));

                    // Walls
                    for (int i = 0; i < 22; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                Settings.getSize() + 125,
                                i * Settings.getSize()));
                    }

                    for (int i = 0; i < 7; i++) {
                        w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                Settings.getSize() + 245,
                                i * Settings.getSize()));
                    }

                    for (int i = 0; i < 22; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                Settings.getSize() + 245,
                                (i + 7) * Settings.getSize()));
                    }

                    for (int i = 0; i < 12; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                Settings.getSize() + 360,
                                i * Settings.getSize()));
                    }

                    for (int i = 0; i < 12; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                Settings.getSize() + 360,
                                (i + 17) * Settings.getSize()));
                    }

                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                    i * Settings.getSize() + 475,
                                    j * Settings.getSize() + 180));
                        }
                    }

                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                    i * Settings.getSize() + 475,
                                    j * Settings.getSize() + 360));
                        }
                    }

                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                    i * Settings.getSize() + 575,
                                    j * Settings.getSize() + 180));
                        }
                    }

                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                    i * Settings.getSize() + 575,
                                    j * Settings.getSize() + 360));
                        }
                    }

                    for (int i = 0; i < 15; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                Settings.getSize() + 660,
                                (i + 7) * Settings.getSize()));
                    }

                    // Placing bonus.
                    w.addElement(new BombeMagnetique(TypeElementBase.Stone, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            10 * Settings.getSize(),
                            4 * Settings.getSize()));

                    w.addElement(new BombeMagnetique(TypeElementBase.Stone, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            10 * Settings.getSize(),
                            6 * Settings.getSize()));

                    w.addElement(new Snap(TypeElementBase.Stone, TextureLoader.loadTexture(Bonus.class.getResource("/textures/snap.png"), true), 5,
                            19 * Settings.getSize(),
                            14 * Settings.getSize()));

                    // Placing IA.
                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            12 * Settings.getSize(),
                            3 * Settings.getSize()));


                    // Level created.
                    return w;
                }
                case 6: {
                    // Start and end areas.
                    Area[] areas = new Area[3];
                    areas[1] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 - 40, Color.BLUE); // StartArea P1
                    areas[2] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 + 40, Color.BLUE); // StartArea P2
                    areas[0] = new SimpleArea(Settings.getWidth() - (Settings.getSize() + 50), Settings.getHeight() / 2 - Settings.getSize() / 2, Color.GREEN); // EndArea

                    // Creation of the world.
                    World w = new World("Level 7", TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/metal_floor.jpg"), false), areas, "2");
                    World.fillBorder(w, SampleWorldFactory.class.getResource("/textures/tube_texture.png"));

                    // Placing elements.
                    w.addElement(new Leurre(TypeElementBase.Unasigned, TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_activated.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_before_activated.png"), true),
                            20,
                            5 * Settings.getSize(),
                            5 * Settings.getSize()));

                    w.addElement(new Leurre(TypeElementBase.Unasigned, TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_activated.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_before_activated.png"), true),
                            20,
                            5 * Settings.getSize(),
                            25 * Settings.getSize()));

                    // Walls
                    for (int i = 0; i < 11; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                6 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 0; i < 11; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                6 * Settings.getSize(),
                                (i + 18) * Settings.getSize()));
                    }

                    for (int i = 0; i < 22; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                13 * Settings.getSize(),
                                (i + 7) * Settings.getSize()));
                    }

                    for (int i = 0; i < 22; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                18 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 0; i < 13; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                23 * Settings.getSize(),
                                (16 + i) * Settings.getSize()));
                    }

                    for (int i = 0; i < 9; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                (19 + i) * Settings.getSize(),
                                10 * Settings.getSize()));
                    }

                    for (int i = 0; i < 11; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                28 * Settings.getSize(),
                                (10 + i) * Settings.getSize()));
                    }

                    for (int i = 0; i < 9; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                (24 + i) * Settings.getSize(),
                                5 * Settings.getSize()));
                    }

                    for (int i = 0; i < 24; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                33 * Settings.getSize(),
                                (5 + i) * Settings.getSize()));
                    }

                    // Placing bonus.
                    w.addElement(new BombeMagnetique(TypeElementBase.Stone, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            17 * Settings.getSize(),
                            3 * Settings.getSize()));

                    w.addElement(new BombeMagnetique(TypeElementBase.Stone, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            17 * Settings.getSize(),
                            27 * Settings.getSize()));

                    // Placing IA.
                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            8 * Settings.getSize(),
                            20 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            10 * Settings.getSize(),
                            20 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            12 * Settings.getSize(),
                            20 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            8 * Settings.getSize(),
                            22 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            10 * Settings.getSize(),
                            22 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            12 * Settings.getSize(),
                            22 * Settings.getSize()));

                    // Level created.
                    return w;
                }
                case 7: {
                    // Start and end areas.
                    Area[] areas = new Area[3];
                    areas[1] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 - 40, Color.BLUE); // StartArea P1
                    areas[2] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 + 40, Color.BLUE); // StartArea P2
                    areas[0] = new SimpleArea(Settings.getWidth() - (Settings.getSize() + 50), Settings.getHeight() / 2 - Settings.getSize() / 2, Color.GREEN); // EndArea

                    // Creation of the world.
                    World w = new World("Level 8", TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/metal_floor.jpg"), false), areas, "2");
                    World.fillBorder(w, SampleWorldFactory.class.getResource("/textures/tube_texture.png"));

                    // Placing elements.
                    w.addElement(new Leurre(TypeElementBase.Unasigned, TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_activated.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_before_activated.png"), true),
                            20,
                            5 * Settings.getSize(),
                            5 * Settings.getSize()));

                    w.addElement(new Leurre(TypeElementBase.Unasigned, TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_activated.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_before_activated.png"), true),
                            20,
                            5 * Settings.getSize(),
                            25 * Settings.getSize()));

                    // Walls
                    for (int i = 0; i < 11; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                10 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 0; i < 11; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                10 * Settings.getSize(),
                                (i + 18) * Settings.getSize()));
                    }

                    for (int i = 0; i < 15; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                17 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 15; i < 29; i++) {
                        w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                16 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 15; i < 29; i++) {
                        w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                17 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 15; i < 29; i++) {
                        w.addElement(new Wall(TypeElementBase.Stone, TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/texture-brique-blanche.jpg"), true),
                                18 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 0; i < 20; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                23 * Settings.getSize(),
                                (9 + i) * Settings.getSize()));
                    }

                    for (int i = 0; i < 20; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                27 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 0; i < 20; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                33 * Settings.getSize(),
                                (5 + i) * Settings.getSize()));
                    }

                    // Placing bonus.
                    w.addElement(new BombeMagnetique(TypeElementBase.Stone, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            17 * Settings.getSize(),
                            3 * Settings.getSize()));

                    w.addElement(new Snap(TypeElementBase.Stone, TextureLoader.loadTexture(Bonus.class.getResource("/textures/snap.png"), true), 5,
                            13 * Settings.getSize(),
                            14 * Settings.getSize()));

                    // Placing IA.
                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            8 * Settings.getSize(),
                            20 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            10 * Settings.getSize(),
                            20 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            12 * Settings.getSize(),
                            20 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            8 * Settings.getSize(),
                            22 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            10 * Settings.getSize(),
                            22 * Settings.getSize()));

                    w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                            12 * Settings.getSize(),
                            22 * Settings.getSize()));

                    // Level created.
                    return w;
                }
                case 8: {
                    // Start and end areas.
                    Area[] areas = new Area[3];
                    areas[1] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 - 40, Color.BLUE); // StartArea P1
                    areas[2] = new SimpleArea(Settings.getSize() + 40, Settings.getHeight() / 2 - Settings.getSize() / 2 + 40, Color.BLUE); // StartArea P2
                    areas[0] = new SimpleArea(Settings.getWidth() - (Settings.getSize() + 50), Settings.getHeight() / 2 - Settings.getSize() / 2, Color.GREEN); // EndArea

                    // Creation of the world.
                    World w = new World("Level 9", TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/metal_floor.jpg"), false), areas, "2");
                    World.fillBorder(w, SampleWorldFactory.class.getResource("/textures/tube_texture.png"));

                    // Placing elements.
                    w.addElement(new Leurre(TypeElementBase.Unasigned, TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_activated.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_before_activated.png"), true),
                            20,
                            5 * Settings.getSize(),
                            5 * Settings.getSize()));

                    w.addElement(new Leurre(TypeElementBase.Unasigned, TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_activated.png"), true),
                            TextureLoader.loadTexture(Bonus.class.getResource("/textures/leurre_before_activated.png"), true),
                            20,
                            5 * Settings.getSize(),
                            25 * Settings.getSize()));

                    // Walls
                    for (int i = 0; i < 11; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                7 * Settings.getSize(),
                                i * Settings.getSize()));
                    }

                    for (int i = 0; i < 11; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                7 * Settings.getSize(),
                                (i + 18) * Settings.getSize()));
                    }

                    for (int i = 0; i < 13; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                (21 + i) * Settings.getSize(),
                                10 * Settings.getSize()));
                    }

                    for (int i = 0; i < 13; i++) {
                        w.addElement(new Barres(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/tube_texture.png"), true),
                                (21 + i) * Settings.getSize(),
                                19 * Settings.getSize()));
                    }

                    // Placing bonus.
                    w.addElement(new BombeMagnetique(TypeElementBase.Stone, TextureLoader.loadTexture(Bonus.class.getResource("/textures/bomb.png"), true),
                            17 * Settings.getSize(),
                            3 * Settings.getSize()));

                    w.addElement(new Snap(TypeElementBase.Stone, TextureLoader.loadTexture(Bonus.class.getResource("/textures/snap.png"), true), 5,
                            13 * Settings.getSize(),
                            14 * Settings.getSize()));

                    // Placing IA.
                    for (int i = 10; i < 17; i++) {
                        w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                                i * Settings.getSize(),
                                10 * Settings.getSize()));
                    }
                    
                    for (int i = 10; i < 17; i++) {
                        w.addRobotIA(new RobotIA(TextureLoader.loadTexture(SampleWorldFactory.class.getResource("/textures/robot_enemie.png"), true),
                                i * Settings.getSize(),
                                19 * Settings.getSize()));
                    }

                    // Level created.
                    return w;
                }
                default:
                    return null;
            }
        } catch (IOException ex) {
            System.err.println("Texture not found. " + ex.getMessage());
            return null;
        }
    }
}
