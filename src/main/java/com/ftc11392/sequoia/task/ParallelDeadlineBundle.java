package com.ftc11392.sequoia.task;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A {@link TaskBundle} of a deadline {@link Task} and other tasks. The tasks are run in parallel until the
 * deadline Task has stopped running, after which all tasks that are still running are interrupted.
 */
public class ParallelDeadlineBundle extends TaskBundle {
	private final Set<Task> tasks = new HashSet<>();
	private Task deadline;

	public ParallelDeadlineBundle(Task deadline, Task... tasks) {

		this.deadline = deadline;

		addTasks(tasks);
		if (!this.tasks.contains(deadline)) {
			addTasks(deadline);
		}
	}

	public void setDeadline(Task deadline) {
		if (tasks.contains(deadline)) {
			addTasks(deadline);
		}
		this.deadline = deadline;
	}

	public void addTasks(Task... tasks) {
		requireUnbundled(tasks);

		if (running) {
			throw new TaskException("Tasks cannot be added to a TaskBundle while the bundle is running.");
		}

		registerBundledTasks(tasks);

		for (Task task : tasks) {
			if (!Collections.disjoint(task.getSubsystems(), subsystems)) {
				throw new TaskException("Multiple tasks in a parallel bundle cannot require the same subsystems");
			}

			this.tasks.add(task);
			subsystems.addAll(task.getSubsystems());
		}
	}

	@Override
	public void init() {
		running = true;
		for (Task task : tasks) {
			task.init();
		}
	}

	@Override
	public void loop() {
		for (Task task : tasks) {
			if (!task.isRunning()) {
				continue;
			}
			task.loop();
			if (!task.isRunning()) {
				task.stop(false);
				if (task.equals(deadline)) {
					running = false;
				}
			}
		}
	}

	@Override
	public void stop(boolean interrupted) {
		for (Task task : tasks) {
			if (task.isRunning()) {
				task.stop(true);
			}
		}
	}
}
