package com.ftc11392.sequoia;

import com.ftc11392.sequoia.task.Scheduler;
import com.ftc11392.sequoia.util.GamepadHandler;
import com.ftc11392.sequoia.util.OpModeState;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public abstract class SequoiaOpMode extends OpMode {

	protected GamepadHandler gamepad1H;
	protected GamepadHandler gamepad2H;
	protected Scheduler scheduler;

	@Override
	public void init() {
		scheduler = Scheduler.getInstance();
		scheduler.init(telemetry);
		Scheduler.getInstance().initSubsystems(hardwareMap);
		initTriggers();
	}

	@Override
	public void init_loop() {
		Scheduler.getInstance().loop(OpModeState.INIT_LOOP);
	}

	@Override
	public void start() {
		gamepad1H = new GamepadHandler(gamepad1);
		gamepad2H = new GamepadHandler(gamepad2);
		Scheduler.getInstance().clearBehaviors();
		Scheduler.getInstance().startSubsystems();
		runTriggers();
	}

	@Override
	public void loop() {
		Scheduler.getInstance().loop(OpModeState.RUN_LOOP);
	}

	@Override
	public void stop() {
		Scheduler.getInstance().stopSubsystems();
		resetScheduler();
	}

	private void resetScheduler() { // would this be better placed in Scheduler?
		Scheduler.getInstance().cancelAll();
		Scheduler.getInstance().clearBehaviors();
		Scheduler.getInstance().clearSubsystems();
	}

	/**
	 * Place triggers that should run during the initialization loop here.
	 */
	public abstract void initTriggers();

	/**
	 * Place triggers that should run during the run loop here.
	 */
	public abstract void runTriggers();
}
