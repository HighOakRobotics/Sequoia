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
		resetScheduler();
		scheduler.init(telemetry);
		scheduler.initSubsystems(hardwareMap);
		initTriggers();
	}

	@Override
	public void init_loop() {
		scheduler.loop(OpModeState.INIT_LOOP);
	}

	@Override
	public void start() {
		gamepad1H = new GamepadHandler(gamepad1);
		gamepad2H = new GamepadHandler(gamepad2);
		scheduler.clearBehaviors();
		scheduler.startSubsystems();
		runTriggers();
		System.out.println("started opmode");
	}

	@Override
	public void loop() {
		scheduler.loop(OpModeState.RUN_LOOP);
	}

	@Override
	public void stop() {
		scheduler.stopSubsystems();
		resetScheduler();
	}

	private void resetScheduler() { // would this be better placed in Scheduler?
		scheduler.cancelAll();
		scheduler.clearBehaviors();
		scheduler.clearSubsystems();
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
