package org.firstinspires.ftc.teamcode.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.HashMap;

public class GamepadEx {
    private Gamepad gamepad;
    private HashMap<Buttons, Boolean> buttonStates;
    private HashMap<Buttons, Boolean> prevButtonStates;


    public enum Buttons {
        A, B, X, Y, LEFT_BUMPER, RIGHT_BUMPER, BACK, START, DPAD_UP, DPAD_DOWN, DPAD_LEFT,
        DPAD_RIGHT, LEFT_CLICK, RIGHT_CLICK
    }

    public GamepadEx(Gamepad gamepad) {
        this.gamepad = gamepad;

        buttonStates = new HashMap<>();
        for (Buttons button : Buttons.values()) {
            buttonStates.put(button, GetButton(button));
        }
        prevButtonStates = buttonStates;
    }
    
    public void Update() {
        prevButtonStates = buttonStates;
        for (Buttons button : Buttons.values()) {
            buttonStates.put(button, GetButton(button));
        }
    }

    public boolean GetButton(Buttons button) {
        boolean value = false;
        // Get the corresponding value from the gamepad

        switch (button) {
            case A:
                value = gamepad.a;
                break;
            case B:
                value = gamepad.b;
                break;
            case X:
                value = gamepad.x;
                break;
            case Y:
                value = gamepad.y;
                break;
            case LEFT_BUMPER:
                value = gamepad.left_bumper;
                break;
            case RIGHT_BUMPER:
                value = gamepad.right_bumper;
                break;
            case BACK:
                value = gamepad.back;
                break;
            case START:
                value = gamepad.start;
                break;
            case DPAD_UP:
                value = gamepad.dpad_up;
                break;
            case DPAD_DOWN:
                value = gamepad.dpad_down;
                break;
            case DPAD_LEFT:
                value = gamepad.dpad_left;
                break;
            case DPAD_RIGHT:
                value = gamepad.dpad_right;
                break;
            case LEFT_CLICK:
                value = gamepad.left_stick_button;
                break;
            case RIGHT_CLICK:
                value = gamepad.right_stick_button;
                break;
        }
        return value;
    }

    public boolean GetButtonJustPressed(Buttons button) {
        return GetButton(button) && !prevButtonStates.get(button);
    }
}
