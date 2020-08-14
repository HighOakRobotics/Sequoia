package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.HashSet;

public class Task {
	private HashSet<Subsystem> subsystems = new HashSet<>();
	private Telemetry telemetry;

	private boolean interruptible = true;

	protected boolean running;

	public Task(Telemetry telemetry) {
		this.telemetry = telemetry;
	}

	public final void addSubsystems(Subsystem... subsystems) {
		this.subsystems.addAll(Arrays.asList(subsystems));
	}

	public boolean isInterruptible() {
		return interruptible;
	}

	public void init() {

	}

	public void loop() {

	}

	public void stop(boolean interrupted) {

	}

	public boolean isRunning() {
		return running;
	}

	public HashSet<Subsystem> getSubsystems() {
		return subsystems;
	}

	public final boolean hasSubsystem(Subsystem requirement) {
		return getSubsystems().contains(requirement);
	}
}
