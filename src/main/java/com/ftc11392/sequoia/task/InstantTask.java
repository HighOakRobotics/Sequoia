package com.ftc11392.sequoia.task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class InstantTask extends Task {
	private final Runnable toRun;

	public InstantTask(Runnable toRun, Telemetry telemetry) {
		super(telemetry);
		this.toRun = toRun;
		running = false;
	}

	@Override
	public void init() {
		toRun.run();
	}

	@Override
	public void loop() { }

	@Override
	public void stop(boolean interrupted) { }
}
