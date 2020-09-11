package com.ftc11392.sequoia.task;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A {@link TaskBundle} that runs a bundle of tasks ({@link Task}) in parallel. Once any of them finish,
 * this task finishes, interrupting all other tasks.
 */
public class ParallelRaceBundle extends TaskBundle {
	private final Set<Task> tasks = new HashSet<>();

	public ParallelRaceBundle(Task... tasks) {
		addTasks(tasks);
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
			task.loop();
			if (!task.isRunning()) {
				running = false;
			}
		}
	}

	@Override
	public void stop(boolean interrupted) {
		for (Task task : tasks) {
			task.stop(task.isRunning());
		}
	}
}
