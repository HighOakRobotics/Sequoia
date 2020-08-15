package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.util.Clock;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.TimeUnit;

public class WaitTask extends Task {
	private Clock clock = new Clock();
	private final TimeUnit unit;
	private final long amount;

	public WaitTask(long amount, TimeUnit unit, Telemetry telemetry) {
		super(telemetry);
		this.unit = unit;
		this.amount = amount;

		super.running = true;
	}

	public WaitTask(int seconds, Telemetry telemetry) {
		this(seconds, TimeUnit.SECONDS, telemetry);
	}

	@Override
	public void init() {
		clock.startTiming();
	}

	@Override
	public void loop() {
		if (clock.getTime(unit) > amount) {
			super.running = false;
		}
	}

	@Override
	public void stop(boolean interrupted) {
		// Empty as there is no desired behavior here
	}
}
