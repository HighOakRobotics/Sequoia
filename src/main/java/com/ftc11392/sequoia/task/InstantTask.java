package com.ftc11392.sequoia.task;

/**
 * A {@link Task} that will only initialize, running the given {@link Runnable}. Stops immediately.
 */
public class InstantTask extends Task {
	private final Runnable toRun;

	public InstantTask(Runnable toRun) {
		this.toRun = toRun;
	}

	@Override
	public void init() {
		toRun.run();
		running = false;
	}

	@Override
	public void loop() {
		// Empty as there is no desired behavior here
	}

	@Override
	public void stop(boolean interrupted) {
		// Empty as there is no desired behavior here
	}
}
