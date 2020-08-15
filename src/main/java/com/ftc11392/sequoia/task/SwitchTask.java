package com.ftc11392.sequoia.task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Map;
import java.util.function.Supplier;

/**
 * A {@link Task} that, given a selection of tasks, runs one of the tasks based on the given
 * selector.
 */
public class SwitchTask extends Task {
	private final Map<Object, Task> mapping;
	private final Supplier<Object> selector;
	private Task selected;

	public SwitchTask(Telemetry telemetry, Map<Object, Task> mapping, Supplier<Object> selector) {
		super(telemetry);
		TaskBundle.requireUnbundled(mapping.values());
		TaskBundle.registerBundledTasks(mapping.values().toArray(new Task[]{}));

		this.mapping = mapping;
		this.selector = selector;

		for (Task task : mapping.values()) {
			subsystems.addAll(task.getSubsystems());
		}
	}

	@Override
	public void init() {
		if (!mapping.keySet().contains(selector.get())) {
			throw new TaskException("Invalid selector for SwitchTask");
		}
		selected = mapping.get(selector.get());
		selected.init();
	}

	@Override
	public void loop() {
		selected.loop();
	}

	@Override
	public void stop(boolean interrupted) {
		selected.stop(interrupted);
	}

	@Override
	public boolean isRunning() {
		return selected.isRunning();
	}
}
