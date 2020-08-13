package com.ftc11392.sequoia.triggers;

import com.ftc11392.sequoia.task.Task;

import java.util.function.BooleanSupplier;

/**
 * This is literally just Trigger with some methods renamed.
 * It's so I don't have to mess around with trigger buttons on the gamepad and the method names can be nicer.
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
}
