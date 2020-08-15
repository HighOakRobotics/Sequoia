package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * A {@link Task} that runs a {@link Runnable} until interrupted.
 */
public class RunTask extends Task{
	protected final Runnable run;

	public RunTask(Telemetry telemetry, Runnable run, Subsystem requirements){
		super(telemetry);
		this.run = run;
		this.running = true;
		addSubsystems(requirements);
	}

	@Override
	public void init() {
		// Empty as there is no desired behavior here
	}

	@Override
	public void loop(){ run.run(); }

	@Override
	public void stop(boolean interrupted) {
		// Empty as there is no desired behavior here
	}
}
