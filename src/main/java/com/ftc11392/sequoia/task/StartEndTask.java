package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;

/**
 * A {@link Task} that runs a {@link Runnable} once when it is initialized and another Runnable once
 * when it is interrupted. This task runs perpetually until interrupted.
 */
public class StartEndTask extends Task {
	protected final Runnable init;
	protected final Runnable stop;

	public StartEndTask(Runnable init, Runnable stop, Subsystem... subsystems) {
		this.init = init;
		this.stop = stop;
		this.running = true;

		addSubsystems(subsystems);
	}

	@Override
	public void init() {
		init.run();
	}

	@Override
	public void loop() {
		// Empty as there is no desired behavior here
	}

	@Override
	public void stop(boolean interrupted) {
		stop.run();
	}
}
