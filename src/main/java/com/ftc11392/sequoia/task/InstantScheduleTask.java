package com.ftc11392.sequoia.task;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Schedules the given Tasks ({@link Task}). Stops immediately.
 */
public class InstantScheduleTask extends Task {
	private final Set<Task> toSchedule = new HashSet<>();

	public InstantScheduleTask(Task... tasks) {
		toSchedule.addAll(Arrays.asList(tasks));
		running = false;
	}

	@Override
	public void init() {
		Scheduler instance = Scheduler.getInstance();
		for (Task task : toSchedule) {
			instance.schedule(task);
		}
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
