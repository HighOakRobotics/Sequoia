package com.ftc11392.sequoia.task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A {@link TaskBundle} that runs tasks ({@link Task}) in parallel. Stops running when all tasks have stopped
 * running.
 */
public class ParallelTaskBundle extends TaskBundle {
	private final Set<Task> tasks = new HashSet<>(); // Task task, Boolean isRunning

	public ParallelTaskBundle(Telemetry telemetry, Task... tasks) {
		super(telemetry);
		addTasks(tasks);
	}


	@Override
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
		for (Task task: tasks) {
			if (!task.isRunning()) {
				continue;
			}
			task.loop();
			if (!task.isRunning()) {
				task.stop(false);
			}
		}
	}

	@Override
	public void stop(boolean interrupted) {
		if (interrupted) {
			for (Task task : tasks) {
				if (task.isRunning()) {
					task.stop(true);
				}
			}
		}
	}

	@Override
	public boolean isRunning() {
		return tasks.stream().anyMatch(Task::isRunning);
	}
}
