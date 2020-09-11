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

	// Sticks
	public BooleanSupplier sticks(double threshold) {
		return () -> Math.abs(gamepad.left_stick_x) > threshold || Math.abs(gamepad.left_stick_y) > threshold
				|| Math.abs(gamepad.right_stick_x) > threshold || Math.abs(gamepad.right_stick_y) > threshold;
	}

	public BooleanSupplier leftStick(double threshold) {
		return () -> Math.abs(gamepad.left_stick_x) > threshold || Math.abs(gamepad.left_stick_y) > threshold;
	}

	public BooleanSupplier leftStickX(double threshold) {
		return () -> Math.abs(gamepad.left_stick_x) > threshold;
	}

	public BooleanSupplier leftStickY(double threshold) {
		return () -> Math.abs(gamepad.left_stick_y) > threshold;
	}

	public BooleanSupplier rightStick(double threshold) {
		return () -> Math.abs(gamepad.right_stick_x) > threshold || Math.abs(gamepad.right_stick_y) > threshold;
	}

	public BooleanSupplier rightStickX(double threshold) {
		return () -> Math.abs(gamepad.right_stick_x) > threshold;
	}

	public BooleanSupplier rightStickY(double threshold) {
		return () -> Math.abs(gamepad.right_stick_y) > threshold;
	}

	// Stick buttons
	public BooleanSupplier rightStickButton() {
		return () -> gamepad.right_stick_button;
	}

	public BooleanSupplier leftStickButton() {
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
	public Button sticksButton(double threshold) {
		return new Button(sticks(threshold));
	}

	public Button leftStickButton(double threshold) {
		return new Button(leftStick(threshold));
	}

	public Button leftStickXButton(double threshold) {
		return new Button(leftStickX(threshold));
	}

	public Button leftStickYButton(double threshold) {
		return new Button(leftStickY(threshold));
	}

	public Button rightStickButton(double threshold) {
		return new Button(rightStick(threshold));
	}

	public Button rightStickXButton(double threshold) {
		return new Button(rightStickX(threshold));
	}

	public Button rightStickYButton(double threshold) {
		return new Button(rightStickY(threshold));
	}


	// Stick button buttons
	public Button rightStickButtonButton() {
		return new Button(rightStickButton());
	}

	public Button leftStickButtonButton() {
		return new Button(leftStickButton());
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
	public ToggleButton sticksToggleButton(double threshold) {
		return new ToggleButton(sticks(threshold));
	}

	public ToggleButton leftStickToggleButton(double threshold) {
		return new ToggleButton(leftStick(threshold));
	}

	public ToggleButton leftStickXToggleButton(double threshold) {
		return new ToggleButton(leftStickX(threshold));
	}

	public ToggleButton leftStickYToggleButton(double threshold) {
		return new ToggleButton(leftStickY(threshold));
	}

	public ToggleButton rightStickToggleButton(double threshold) {
		return new ToggleButton(rightStick(threshold));
	}

	public ToggleButton rightStickXToggleButton(double threshold) {
		return new ToggleButton(rightStickX(threshold));
	}

	public ToggleButton rightStickYToggleButton(double threshold) {
		return new ToggleButton(rightStickY(threshold));
	}

	// Stick toggle buttons
	public ToggleButton rightStickButtonToggleButton() {
		return new ToggleButton(rightStickButton());
	}

	public ToggleButton leftStickButtonToggleButton() {
		return new ToggleButton(leftStickButton());
	}
}
