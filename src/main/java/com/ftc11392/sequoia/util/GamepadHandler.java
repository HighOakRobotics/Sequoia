package com.ftc11392.sequoia.util;

import com.ftc11392.sequoia.triggers.Button;
import com.ftc11392.sequoia.triggers.ToggleButton;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.BooleanSupplier;

/**
 * Provides {@link BooleanSupplier} objects for various gamepad buttons and triggers.
 */
public class GamepadHandler {
	private final Gamepad gamepad;

	public GamepadHandler(Gamepad gamepad) {
		this.gamepad = gamepad;
	}

	//-----------------------------
	// Boolean supplier getters
	//-----------------------------

	// D-pad
	public BooleanSupplier left() {
		return () -> gamepad.dpad_left;
	}

	public BooleanSupplier right() {
		return () -> gamepad.dpad_right;
	}

	public BooleanSupplier up() {
		return () -> gamepad.dpad_up;
	}

	public BooleanSupplier down() {
		return () -> gamepad.dpad_down;
	}

	// ABXY
	public BooleanSupplier a() {
		return () -> gamepad.a;
	}

	public BooleanSupplier b() {
		return () -> gamepad.b;
	}

	public BooleanSupplier x() {
		return () -> gamepad.x;
	}

	public BooleanSupplier y() {
		return () -> gamepad.y;
	}

	// Triggers and bumpers
	public BooleanSupplier leftBumper() {
		return () -> gamepad.left_bumper;
	}

	public BooleanSupplier rightBumper() {
		return () -> gamepad.right_bumper;
	}

	public BooleanSupplier leftTrigger(double threshold) {
		return () -> gamepad.left_trigger > threshold; //idk lol
	}

	public BooleanSupplier rightTrigger(double threshold) {
		return () -> gamepad.right_trigger > threshold;
	}

	// Stick buttons
	public BooleanSupplier rightStick() {
		return () -> gamepad.right_stick_button;
	}

	public BooleanSupplier leftStick() {
		return () -> gamepad.left_stick_button;
	}

	//-----------------------------
	// Trigger getters
	//-----------------------------

	// D-pad
	public Button leftButton() {
		return new Button(left());
	}

	public Button rightButton() {
		return new Button(right());
	}

	public Button upButton() {
		return new Button(up());
	}

	public Button downButton() {
		return new Button(down());
	}

	// ABXY
	public Button aButton() {
		return new Button(a());
	}

	public Button bButton() {
		return new Button(b());
	}

	public Button xButton() {
		return new Button(x());
	}

	public Button yButton() {
		return new Button(y());
	}

	// Triggers and bumpers
	public Button leftBumperButton() {
		return new Button(leftBumper());
	}

	public Button rightBumperButton() {
		return new Button(rightBumper());
	}

	public Button leftTriggerButton(double threshold) {
		return new Button(leftTrigger(threshold));
	}

	public Button rightTriggerButton(double threshold) {
		return new Button(rightTrigger(threshold));
	}

	// Stick buttons
	public Button rightStickButton() {
		return new Button(rightStick());
	}

	public Button leftStickButton() {
		return new Button(leftStick());
	}

	// D-pad Toggle Buttons (who would even use these? lol)
	public ToggleButton leftToggleButton() {
		return new ToggleButton(left());
	}

	public ToggleButton rightToggleButton() {
		return new ToggleButton(right());
	}

	public ToggleButton upToggleButton() {
		return new ToggleButton(up());
	}

	public ToggleButton downToggleButton() {
		return new ToggleButton(down());
	}

	// ABXY Toggle Buttons
	public ToggleButton aToggleButton() {
		return new ToggleButton(a());
	}

	public ToggleButton bToggleButton() {
		return new ToggleButton(b());
	}

	public ToggleButton xToggleButton() {
		return new ToggleButton(x());
	}

	public ToggleButton yToggleButton() {
		return new ToggleButton(y());
	}

	// Triggers and bumpers toggle buttons
	public ToggleButton leftBumperToggleButton() {
		return new ToggleButton(leftBumper());
	}

	public ToggleButton rightBumperToggleButton() {
		return new ToggleButton(rightBumper());
	}

	public ToggleButton leftTriggerToggleButton(double threshold) {
		return new ToggleButton(leftTrigger(threshold));
	}

	public ToggleButton rightTriggerToggleButton(double threshold) {
		return new ToggleButton(rightTrigger(threshold));
	}

	// Stick toggle buttons
	public ToggleButton rightStickToggleButton() {
		return new ToggleButton(rightStick());
	}

	public ToggleButton leftStickToggleButton() {
		return new ToggleButton(leftStick());
	}
}
