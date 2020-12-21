package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;
import com.ftc11392.sequoia.util.OpModeState;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SequentialTaskBundleTest {
	@DisplayName("Test Sequential Task Bundle")
	@Test
	public void TestSequentialTaskBundle() {
		Scheduler.getInstance().cancelAll();
		Scheduler.getInstance().clearSubsystems();
		Scheduler.getInstance().clearBehaviors();

		TelemetryImpl fakeTele = mock(TelemetryImpl.class);

		when(fakeTele.log()).thenReturn(mock(Telemetry.Log.class));
		when(fakeTele.addLine(anyString())).thenReturn(mock(Telemetry.Line.class, RETURNS_DEEP_STUBS));

		Scheduler.getInstance().init(fakeTele);

		Subsystem mockedSubsystem = mock(Subsystem.class);
		Subsystem mockedSubsystem2 = mock(Subsystem.class);

		Scheduler.getInstance().initSubsystems(null);
		Scheduler.getInstance().loop(OpModeState.INIT_LOOP);

		Task testTask = new Task() {
			@Override
			public void init() {
				running = true;
				addSubsystems(mockedSubsystem);
			}

			@Override
			public void loop() {
			}

			@Override
			public void stop(boolean interrupted) {
				running = false;
			}
		};

		Task testTask2 = new Task() {

			@Override
			public void init() {
				running = true;
				addSubsystems(mockedSubsystem);
				addSubsystems(mockedSubsystem2);
			}

			@Override
			public void loop() {
			}

			@Override
			public void stop(boolean interrupted) {
				running = false;
			}
		};

		SequentialTaskBundle testSequentialTaskBundle = new SequentialTaskBundle(testTask, testTask2);

		Scheduler.getInstance().schedule(testSequentialTaskBundle);
		Scheduler.getInstance().loop(OpModeState.RUN_LOOP);

		assertTrue(testTask.isRunning());
		assertFalse(testTask2.isRunning());

		testTask.stop(false);

		Scheduler.getInstance().loop(OpModeState.RUN_LOOP);

		assertFalse(testTask.isRunning());
		assertTrue(testTask2.isRunning());

		testTask2.stop(false);

		Scheduler.getInstance().loop(OpModeState.RUN_LOOP);

		assertFalse(testSequentialTaskBundle.isRunning());
	}
}
