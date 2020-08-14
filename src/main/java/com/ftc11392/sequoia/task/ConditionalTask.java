package com.ftc11392.sequoia.task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;

import static com.ftc11392.sequoia.task.TaskBundle.requireUnbundled;

public class ConditionalTask extends Task {
	private final Task ifTrue;
	private final Task ifFalse;
	private final BooleanSupplier checker;

	private Task selectedTask;

	public ConditionalTask(Telemetry telemetry, Task ifTrue, Task ifFalse, BooleanSupplier checker) {
		super(telemetry);

		requireUnbundled(ifTrue, ifFalse);
		TaskBundle.registerBundledTasks(ifTrue, ifFalse);

		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
		this.checker = checker;

		subsystems.addAll(ifTrue.getSubsystems());
		subsystems.addAll(ifFalse.getSubsystems());
	}

	@Override
	public void init() {
		if (checker.getAsBoolean()) {
			selectedTask = ifTrue;
		} else {
			selectedTask = ifFalse;
		}
		selectedTask.init();
	}

	@Override
	public void loop() {
		selectedTask.loop();
	}

	@Override
	public void stop(boolean interrupted) {
		selectedTask.stop(interrupted);
	}

	@Override
	public boolean isRunning() {
		return selectedTask.isRunning();
	}
}
