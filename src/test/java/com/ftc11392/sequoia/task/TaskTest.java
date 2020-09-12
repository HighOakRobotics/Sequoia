package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;
import com.ftc11392.sequoia.util.OpModeState;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class TaskTest {

	@DisplayName("Test Task Lifecycle")
	@Test
	public void TestTaskLifecycle() {
		Scheduler.getInstance().cancelAll();
		Scheduler.getInstance().clearSubsystems();
		Scheduler.getInstance().clearBehaviors();
		Scheduler.getInstance().init(mock(Telemetry.class));

		Subsystem mockedSubsystem = mock(Subsystem.class);

		Scheduler.getInstance().initSubsystems(mock(HardwareMap.class));

		Scheduler.getInstance().loop(OpModeState.INIT_LOOP);

		Task testTask = new Task() {
			@Override
			public void init() {
				addSubsystems(mockedSubsystem);
			}

			@Override
			public void loop() { }

			@Override
			public void stop(boolean interrupted) { }
		};

		assertEquals(testTask.getSubsystems().toArray()[0], mockedSubsystem);

		Scheduler.getInstance().schedule(testTask);

		Scheduler.getInstance().loop(OpModeState.RUN_LOOP);

		assertTrue(testTask.isRunning());

		Scheduler.getInstance().cancelAll();

		Scheduler.getInstance().loop(OpModeState.RUN_LOOP);

		assertFalse(testTask.isRunning());
	}
}
