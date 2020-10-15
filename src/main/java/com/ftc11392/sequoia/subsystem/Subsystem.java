package com.ftc11392.sequoia.subsystem;

import com.ftc11392.sequoia.task.DefaultTask;
import com.ftc11392.sequoia.task.Scheduler;
import com.ftc11392.sequoia.task.Task;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * An abstraction above hardware devices.
 */
public abstract class Subsystem {
	protected Telemetry telemetry;

	protected Task defaultTask = new DefaultTask();

	/**
	 * Instantiates a Subsystem
	 *
	 * @param defaultTask the default Task of the Subsystem
	 */
	public Subsystem(Task defaultTask) {
		this.defaultTask = defaultTask;
		Scheduler.getInstance().registerSubsystem(this);
	}

	/**
	 * Instantiates a Subsystem with a DefaultTask
	 */
	public Subsystem() {
		this(new DefaultTask());
	}

	/**
	 * Used internally in {@link Scheduler}.
	 *
	 * @param telemetry The {@link Telemetry} to be passed
	 */
	public void assignTelemetry(Telemetry telemetry) {
		this.telemetry = telemetry;
	}

	/**
	 * Initialize hardware devices here (run during {@link com.qualcomm.robotcore.eventloop.opmode.OpMode}
	 * <code>init()</code>).
	 *
	 * @param hardwareMap the {@link HardwareMap} to be passed.
	 */
	public abstract void initialize(HardwareMap hardwareMap);

	/**
	 * Runs once in the {@link com.qualcomm.robotcore.eventloop.opmode.OpMode} <code>start()</code> method between init and loop.
	 */
	public abstract void start();

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
