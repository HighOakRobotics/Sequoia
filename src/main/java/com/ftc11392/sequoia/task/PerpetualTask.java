package com.ftc11392.sequoia.task;

/**
 * A {@link Task} that prevents its given Task from stopping itself, only allowing it to be interrupted.
 */
public class PerpetualTask extends Task {
	protected final Task task;

	public PerpetualTask(Task task) {
		this.task = task;
		running = true;
		subsystems.addAll(task.getSubsystems());
	}

	@Override
	public void init() {
		task.init();
	}

	@Override
	public void loop() {
		task.loop();
	}

	@Override
	public void stop(boolean interrupted) {
		task.stop(interrupted);
	}
}
