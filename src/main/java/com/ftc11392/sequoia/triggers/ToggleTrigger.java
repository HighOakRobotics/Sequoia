package com.ftc11392.sequoia.triggers;

import java.util.function.BooleanSupplier;

public class ToggleTrigger extends Trigger {
	private final boolean rising;
	private boolean lastPoll = false;
	private boolean currentState = false;

	/**
	 * Creates a toggling trigger.
	 *
	 * @param isActive The function to poll.
	 * @param rising   Whether to toggle on the rising or the falling edge.
	 */
	public ToggleTrigger(BooleanSupplier isActive, boolean rising) {
		super(isActive);
		this.rising = rising;
	}

	public ToggleTrigger(BooleanSupplier isActive) {
		this(isActive, true);
	}

	/**
	 * Converts signals from button presses to status toggles (toggles the status upon a rising/falling edge) that are then processed by Trigger.
	 *
	 * @return The data from the controller, converted.
	 */
	@Override
	public boolean poll() {
		boolean curPoll = super.poll();
		if ((rising && curPoll && !lastPoll) || (!rising && !curPoll && lastPoll)) {
			currentState = !currentState;
		}
		lastPoll = curPoll;

		return currentState;
	}
}
