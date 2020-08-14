package com.ftc11392.sequoia.task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.List;

public class SequentialTaskBundle extends TaskBundle {
	private final List<Task> tasks = new ArrayList<>();
	private int currentTaskIndex = -1;

	public SequentialTaskBundle(Telemetry telemetry, Task... tasks) {
		super(telemetry);
		addTasks(tasks);
	}

	public void addTasks(Task... tasks) {
		requireUnbundled(tasks);

		if(currentTaskIndex != -1) {
			throw new TaskException("Tasks cannot be added to a TaskBundle while the bundle is running.");
		}

		registerBundledTasks(tasks);

		for(Task task : tasks) {
			this.tasks.add(task);
			subsystems.addAll(task.getSubsystems());
		}
	}

	@Override
	public void init() {
		currentTaskIndex = 0;

		if(!tasks.isEmpty()) {
			tasks.get(0).init();
		}
	}

	@Override
	public void loop() {
		if(tasks.isEmpty()) {
			return;
		}

		Task currentTask = tasks.get(currentTaskIndex);

		currentTask.loop();
		if(!currentTask.isRunning()) {
			currentTask.stop(false);
			currentTaskIndex++;

			if(currentTaskIndex < tasks.size()) {
				tasks.get(currentTaskIndex).init();
			}
		}
	}

	@Override
	public void stop(boolean interrupted) {
		if(interrupted && !tasks.isEmpty() && currentTaskIndex > -1 && currentTaskIndex < tasks.size()) {
			tasks.get(currentTaskIndex).stop(true);
		}
		currentTaskIndex = -1;
	}

	@Override
	public boolean isRunning() {
		return currentTaskIndex != tasks.size();
	}
}
