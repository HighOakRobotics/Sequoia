package com.ftc11392.sequoia.task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ParallelDeadlineBundle extends TaskBundle {
	private final Set<Task> tasks = new HashSet<>();
	private Task deadline;

	public ParallelDeadlineBundle(Telemetry telemetry, Task deadline, Task... tasks) {
		super(telemetry);
		
		this.deadline = deadline;
		
		addTasks(tasks);
		if (!this.tasks.contains(deadline)) {
			addTasks(deadline);
		}
	}

	public void setDeadline(Task deadline) {
		if(tasks.contains(deadline)) {
			addTasks(deadline);
		}
		this.deadline = deadline;
	}

	public void addTasks(Task... tasks) {
		requireUnbundled(tasks);

		if(running) {
			throw new TaskException("Tasks cannot be added to a TaskBundle while the bundle is running.");
		}

		registerBundledTasks(tasks);

		for(Task task : tasks) {
			if(!Collections.disjoint(task.getSubsystems(), subsystems)) {
				throw new TaskException("Multiple tasks in a parallel bundle cannot require the same subsystems");
			}

			this.tasks.add(task);
			subsystems.addAll(task.getSubsystems());
		}
	}

	@Override
	public void init() {
		running = true;
		for(Task task : tasks) {
			task.init();
		}
	}

	@Override
	public void loop() {
		for(Task task : tasks) {
			if(!task.isRunning()) {
				continue;
			}
			task.loop();
			if(!task.isRunning()) {
				task.stop(false);
				if(task == deadline) {
					running = false;
				}
			}
		}
	}

	@Override
	public void stop(boolean interrupted) {
		for(Task task : tasks) {
			if(task.isRunning()) {
				task.stop(true);
			}
		}
	}
}
