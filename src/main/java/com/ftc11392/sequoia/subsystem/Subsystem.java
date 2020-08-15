package com.ftc11392.sequoia.subsystem;

import com.ftc11392.sequoia.task.DefaultTask;
import com.ftc11392.sequoia.task.Task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class Subsystem {
	Telemetry telemetry;

	Task defaultTask = new DefaultTask();

	/**
	 * Runs periodically in the {@link com.qualcomm.robotcore.eventloop.opmode.OpMode} run loop.
	 */
	public abstract void runPeriodic();

	/**
	 * Runs periodically in the {@link com.qualcomm.robotcore.eventloop.opmode.OpMode} init loop.
	 */
	public abstract void initPeriodic();

	/**
	 * Runs when the {@link com.qualcomm.robotcore.eventloop.opmode.OpMode} <code>stop()</code> method
	 * is called. Usually cleanup actions.
	 */
	public abstract void stop();

	public void setDefaultTask(Task defaultTask) {
		this.defaultTask = defaultTask;
	}

	public Task getDefaultTask() {
		return defaultTask;
	}
}
