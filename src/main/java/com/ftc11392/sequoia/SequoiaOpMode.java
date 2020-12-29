package com.ftc11392.sequoia;

import com.ftc11392.sequoia.task.Scheduler;
import com.ftc11392.sequoia.triggers.Trigger;
import com.ftc11392.sequoia.util.GamepadHandler;
import com.ftc11392.sequoia.util.OpModeState;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public abstract class SequoiaOpMode extends OpMode {

	protected GamepadHandler gamepad1H;
	protected GamepadHandler gamepad2H;
	protected Scheduler scheduler;

	private boolean initFlag = false;
	private boolean initLoopFlag = false;
	private boolean startFlag = false;
	private boolean runLoopFlag = false;
	private boolean stopFlag = false;

	protected Trigger initTrigger = new Trigger(() -> initFlag);
	protected Trigger initLoopTrigger = new Trigger(() -> initLoopFlag);
	protected Trigger startTrigger = new Trigger(() -> startFlag);
	protected Trigger runLoopTrigger = new Trigger(() -> runLoopFlag);
	protected Trigger stopTrigger = new Trigger(() -> stopFlag);

	@Override
	public void init() {
		resetFlags();
		scheduler = Scheduler.getInstance();
		resetScheduler();
		scheduler.init(telemetry);
		scheduler.initSubsystems(hardwareMap);
		initTriggers();
		initFlag = true;
		scheduler.loop(OpModeState.INIT);
		initFlag = false;
		initLoopFlag = true;
	}

	@Override
	public void init_loop() {
		scheduler.loop(OpModeState.INIT_LOOP);
	}

	@Override
	public void start() {
		initLoopFlag = false;
		gamepad1H = new GamepadHandler(gamepad1);
		gamepad2H = new GamepadHandler(gamepad2);
		scheduler.clearBehaviors();
		scheduler.startSubsystems();
		runTriggers();
		startFlag = true;
		scheduler.loop(OpModeState.STARTED);
		startFlag = false;
		System.out.println("started opmode");
		runLoopFlag = true;
	}

	@Override
	public void loop() {
		scheduler.loop(OpModeState.RUN_LOOP);
	}

	@Override
	public void stop() {
		runLoopFlag = false;
		stopFlag = true;
		scheduler.stopSubsystems();
		for (int i = 0; i < 2; i++)
			scheduler.loop(OpModeState.STOPPED);
		resetScheduler();
	}

	private void resetScheduler() { // would this be better placed in Scheduler?
		scheduler.cancelAll();
		scheduler.clearBehaviors();
		scheduler.clearSubsystems();
	}

	private void resetFlags() {
		initFlag = false;
		initLoopFlag = false;
		startFlag = false;
		runLoopFlag = false;
		stopFlag = false;
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
