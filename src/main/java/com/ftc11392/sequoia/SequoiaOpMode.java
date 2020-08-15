package com.ftc11392.sequoia;

import com.ftc11392.sequoia.subsystem.Subsystem;
import com.ftc11392.sequoia.task.Scheduler;
import com.ftc11392.sequoia.util.OpModeState;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public abstract class SequoiaOpMode extends OpMode {

	@Override
	public void init() {
		resetScheduler();
		Scheduler.getInstance().init(telemetry);
		Scheduler.getInstance().registerSubsystem(addSubsystems());
		initTriggers();
	}

	@Override
	public void init_loop() {
		Scheduler.getInstance().loop(OpModeState.INIT_LOOP);
	}

	@Override
	public void start() {
		Scheduler.getInstance().clearBehaviors();
		runTriggers();
	}

	@Override
	public void loop() {
		Scheduler.getInstance().loop(OpModeState.RUN_LOOP);

	}

	@Override
	public void stop() {
		resetScheduler();
	}

	private void resetScheduler() { // would this be better placed in Scheduler?
		Scheduler.getInstance().cancelAll();
		Scheduler.getInstance().clearBehaviors();
		Scheduler.getInstance().clearSubsystems();
	}

	/**
	 * Adds the given subsystems ({@link Subsystem}) to the {@link Scheduler}.
	 *
	 * @return the subsystems to add to the Scheduler
	 */
	public abstract Subsystem[] addSubsystems();

	/**
	 * Place triggers that should run during the initialization loop here.
	 */
	public abstract void initTriggers();

	/**
	 * Place triggers that should run during the run loop here.
	 */
	public abstract void runTriggers();
}
