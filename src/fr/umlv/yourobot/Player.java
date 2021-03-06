package fr.umlv.yourobot;

import fr.umlv.yourobot.elements.robot.RobotPlayer;
import java.awt.event.KeyEvent;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Objects;

/**
 * Represent an human player.
 *
 * License: GNU Public license v3.
 *
 * @author Damien Girard <dgirard@nativesoft.fr>
 * @author Joan Goyeau <joan.goyeau@gmail.com>
 */
public class Player {

    private RobotPlayer robot;
    private EnumMap<RobotKeyAction, Integer> controls = new EnumMap<RobotKeyAction, Integer>(RobotKeyAction.class);
    private HashMap<Integer, RobotKeyAction> controls_inverted;

    /**
     * Create a new player.
     *
     * @param robot Robot to be associated with the player.
     */
    public Player(RobotPlayer robot) {
        this.robot = robot;

        // Default controls.
        controls.put(RobotKeyAction.Take, KeyEvent.VK_A);
        controls.put(RobotKeyAction.Boost, KeyEvent.VK_Z);
        controls.put(RobotKeyAction.Turn_Left, KeyEvent.VK_Q);
        controls.put(RobotKeyAction.Turn_Right, KeyEvent.VK_D);
    }

    private void generateInvertedControlHashMap() {
        controls_inverted = new HashMap<Integer, RobotKeyAction>();
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
    public void setKeyBinding(RobotKeyAction robotKeyAction, int keyboardKey) {
        controls.put(robotKeyAction, keyboardKey);
        controls_inverted = null;
    }

    /**
     * Set the robot of the player.
     *
     * @param robot Robot to set.
     */
    public void setRobot(RobotPlayer robot) {
        Objects.requireNonNull(robot);

        this.robot = robot;
    }

    /**
     * Return the robot associated with the player.
     *
     * @return The robot.
     */
    public RobotPlayer getRobot() {
        return robot;
    }

    /**
     * Returns the associated action with the keyBinding.
     *
     * @param keyboardKey Keybinding.
     * @return The action that must be took with the defined key. null if the
     * key is not defined.
     */
    public RobotKeyAction getKeyBinding(Integer keyboardKey) {
        if (controls_inverted == null) {
            generateInvertedControlHashMap();
        }

        return controls_inverted.get(keyboardKey);
    }

    /**
     * Returns the keys associated to the action action.
     *
     * @param action Action to get the keybinding of.
     * @return The key code.
     */
    public Integer getActionKeyBinding(RobotKeyAction action) {
        return controls.get(action);
    }
}
