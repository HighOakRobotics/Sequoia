package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.util.Clock;

import java.util.concurrent.TimeUnit;

/**
 * A {@link Task} that does nothing but stop running after a given amount of time.
 */
public class WaitTask extends Task {
	private final Clock clock = new Clock();
	private final TimeUnit unit;
	private final long amount;

	public WaitTask(long amount, TimeUnit unit) {
		this.unit = unit;
		this.amount = amount;

		super.running = true;
	}

	public WaitTask(int seconds) {
		this(seconds, TimeUnit.SECONDS);
	}

	@Override
	public void init() {
		clock.startTiming();
	}

	@Override
	public void loop() {
		if (clock.getTime(unit) >= amount) {
			super.running = false;
		}
	}

	@Override
	public void stop(boolean interrupted) {
		// Empty as there is no desired behavior here
	}
}
