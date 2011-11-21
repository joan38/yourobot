package fr.umlv.yourobot;

import fr.umlv.yourobot.elements.Robot;
import fr.umlv.zen.KeyboardKey;
import java.util.EnumMap;
import java.util.Objects;

/**
 * Représente un joueur.
 * 
 * @copyright GNU Public license v3.
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Player {

    private Robot robot;
    private EnumMap<RobotKeyAction, KeyboardKey> controls = new EnumMap<RobotKeyAction, KeyboardKey>(RobotKeyAction.class);
    private EnumMap<KeyboardKey, RobotKeyAction> controls_inverted;

    public Player(Robot robot) {
        this.robot = robot;

        // Default controls.
        controls.put(RobotKeyAction.Take, KeyboardKey.A);
        controls.put(RobotKeyAction.Boost, KeyboardKey.F);
        controls.put(RobotKeyAction.Brake, KeyboardKey.S);
        controls.put(RobotKeyAction.Turn_Left, KeyboardKey.Q);
        controls.put(RobotKeyAction.Turn_Right, KeyboardKey.D);
    }

    private void generateInvertedControlHashMap() {
        controls_inverted = new EnumMap<KeyboardKey, RobotKeyAction>(KeyboardKey.class);
        for (RobotKeyAction robotKeyAction : controls.keySet()) {
            controls_inverted.put(controls.get(robotKeyAction), robotKeyAction);
        }
    }

    /**
     * Set a keybinding.
     * 
     * @param robotKeyAction Action of the robot to set.
     * @param keyboardKey Key to assign.
     */
    public void setKeyBinding(RobotKeyAction robotKeyAction, KeyboardKey keyboardKey) {
        controls.put(robotKeyAction, keyboardKey);
        controls_inverted = null;
    }

    /**
     * Set the robot of the player.
     * 
     * @param robot Robot to set.
     */
    public void setRobot(Robot robot) {
        Objects.requireNonNull(robot);

        this.robot = robot;
    }

    public Robot getRobot() {
        return robot;
    }

    /**
     * Returns the associated action with the keyBinding.
     * 
     * @param keyboardKey Keybinding.
     */
    public RobotKeyAction getKeyBinding(KeyboardKey keyboardKey) {
        if (controls_inverted == null) {
            generateInvertedControlHashMap();
        }

        return controls_inverted.get(keyboardKey);
    }
}
