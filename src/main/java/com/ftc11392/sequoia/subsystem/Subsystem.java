package com.ftc11392.sequoia.subsystem;

import com.ftc11392.sequoia.task.DefaultTask;
import com.ftc11392.sequoia.task.Task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class Subsystem {
	Telemetry telemetry;

	Task defaultTask = new DefaultTask();

	public abstract void runPeriodic();

	public abstract void initPeriodic();

	public abstract void stop();

	public void setDefaultTask(Task defaultTask) {
		this.defaultTask = defaultTask;
	}

	public Task getDefaultTask() {
		return defaultTask;
	}
}
