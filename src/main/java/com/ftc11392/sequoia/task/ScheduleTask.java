package com.ftc11392.sequoia.task;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A {@link Task} that schedules the given tasks on initialization. This Task stops running when all its tasks have
 * stopped. If this Task in interrupted, it cancels all its remaining scheduled tasks.
 */
public class ScheduleTask extends Task {
	private final Set<Task> toSchedule = new HashSet<>();

	public ScheduleTask(Task... tasks) {
		toSchedule.addAll(Arrays.asList(tasks));
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
		running = false;
		Scheduler instance = Scheduler.getInstance();
		for (Task task : toSchedule) {
			if (instance.isScheduled(task)) {
				running = true;
			}
		}
	}

	@Override
	public void stop(boolean interrupted) {
		Scheduler instance = Scheduler.getInstance();
		if (interrupted) {
			for (Task task : toSchedule) {
				instance.cancel(task);
			}
		}
	}
}
