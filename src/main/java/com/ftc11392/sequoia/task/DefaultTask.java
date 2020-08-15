package com.ftc11392.sequoia.task;

/**
 * Usually the default {@link Task} in a {@link com.ftc11392.sequoia.subsystem.Subsystem} that does not
 * have its own default behavior.
 */
public class DefaultTask extends Task {
	public DefaultTask() {
		super(null);
	}

	@Override
	public void init() {
		// Empty as there is no desired behavior here
	}

	@Override
	public void loop() {
		// Empty as there is no desired behavior here
	}

	@Override
	public void stop(boolean interrupted) {
		// Empty as there is no desired behavior here
	}

	@Override
	public boolean isRunning() {
		return true;
	}
}
