package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public abstract class Task {
	protected HashSet<Subsystem> subsystems = new HashSet<>();
	private Telemetry telemetry;

	protected boolean interruptible = true;
	protected boolean running;

	/**
	 * Instantiates a Task.
	 *
	 * @param telemetry {@link Telemetry} from an {@link com.qualcomm.robotcore.eventloop.opmode.OpMode}
	 *                                   for the task to be able to send information to a Driver Station.
	 */
	public Task(Telemetry telemetry) {
		this.telemetry = telemetry;
	}

	/**
	 * Adds {@link Subsystem} to the Task as dependencies for it to be scheduled.
	 *
	 * @param subsystems the Subsystems to add.
	 */
	public final void addSubsystems(Subsystem... subsystems) {
		this.subsystems.addAll(Arrays.asList(subsystems));
	}

	public boolean isInterruptible() {
		return interruptible;
	}

	public abstract void init();

	public abstract void loop();

	public abstract void stop(boolean interrupted);

	public boolean isRunning() {
		return running;
	}

	public HashSet<Subsystem> getSubsystems() {
		return subsystems;
	}

	public final boolean hasSubsystem(Subsystem requirement) {
		return getSubsystems().contains(requirement);
	}

	public ParallelRaceBundle withTimeout(long amount, TimeUnit unit) {
		return new ParallelRaceBundle(telemetry, this, new WaitTask(amount, unit, telemetry));
	}
	public ParallelRaceBundle withTimeout(int seconds) {
		return withTimeout(seconds, TimeUnit.SECONDS);
	}
}
