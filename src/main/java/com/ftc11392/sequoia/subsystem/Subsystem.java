package com.ftc11392.sequoia.subsystem;

import com.ftc11392.sequoia.task.DefaultTask;
import com.ftc11392.sequoia.task.Scheduler;
import com.ftc11392.sequoia.task.Task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * An abstraction above hardware devices.
 */
public abstract class Subsystem {
	Telemetry telemetry;

	Task defaultTask = new DefaultTask();

	/**
	 * Instantiates a Subsystem
	 *
	 * @param telemetry {@link Telemetry} from an {@link com.qualcomm.robotcore.eventloop.opmode.OpMode}
	 *                                   for the task to be able to send information to a Driver Station.
	 * @param defaultTask the default Task of the Subsystem
	 */
	public Subsystem(Telemetry telemetry, Task defaultTask) {
		this.telemetry = telemetry;
		this.defaultTask = defaultTask;
		Scheduler.getInstance().registerSubsystem(this);
	}

	/**
	 * Instantiates a Subsystem with a DefaultTask
	 *
	 * @param telemetry {@link Telemetry} from an {@link com.qualcomm.robotcore.eventloop.opmode.OpMode}
	 *                                   for the task to be able to send information to a Driver Station.
	 */
	public Subsystem(Telemetry telemetry) {
		this(telemetry, new DefaultTask());
	}

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
