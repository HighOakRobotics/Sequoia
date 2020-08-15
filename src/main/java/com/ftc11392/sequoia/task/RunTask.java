package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RunTask extends Task{
	protected final Runnable run;

	public RunTask(Telemetry telemetry, Runnable run, Subsystem requirements){
		super(telemetry);
		this.run = run;
		addSubsystems(requirements);
	}

	@Override
	public void init() { }

	@Override
	public void loop(){ run.run(); }

	@Override
	public void stop(boolean interrupted) { }
}
