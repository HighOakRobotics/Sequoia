package com.ftc11392.sequoia.triggers;

import com.ftc11392.sequoia.task.Task;

import java.util.function.BooleanSupplier;

/**
 * Trigger wrapper for the gamepad
 */
public class Button extends Trigger {
	public Button(BooleanSupplier isActive) {
		super(isActive);
	}

	public Button onPress(Task task) {
		rising(task);
		return this;
	}

	public Button onRelease(Task task) {
		falling(task);
		return this;
	}

	public Button onPressWithCancel(Task task) {
		risingWithCancel(task);
		return this;
	}

	public Button onReleaseWithCancel(Task task) {
		fallingWithCancel(task);
		return this;
	}

	public Button whilePressed(Task task) {
		whileOn(task);
		return this;
	}
}
