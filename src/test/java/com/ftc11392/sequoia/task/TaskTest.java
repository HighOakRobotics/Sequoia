package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;
import com.ftc11392.sequoia.util.OpModeState;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskTest {

	@DisplayName("Test Task Lifecycle")
	@Test
	public void TestTaskLifecycle() {
		Scheduler.getInstance().cancelAll();
		Scheduler.getInstance().clearSubsystems();
		Scheduler.getInstance().clearBehaviors();

		TelemetryImpl fakeTele = mock(TelemetryImpl.class);

		when(fakeTele.log()).thenReturn(mock(Telemetry.Log.class));
		when(fakeTele.addLine(anyString())).thenReturn(mock(Telemetry.Line.class, RETURNS_DEEP_STUBS));

		Scheduler.getInstance().init(fakeTele);

		Subsystem mockedSubsystem = mock(Subsystem.class);

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

		Scheduler.getInstance().schedule(testTask);

		assertEquals(testTask.getSubsystems().toArray()[0], mockedSubsystem);

		Scheduler.getInstance().loop(OpModeState.RUN_LOOP);

		assertTrue(testTask.isRunning());

		Scheduler.getInstance().cancelAll();
		Scheduler.getInstance().loop(OpModeState.RUN_LOOP);

		assertFalse(testTask.isRunning());
	}

	@DisplayName("Test Task Timeout")
	@Test
	public void testTaskTimeout() {
		int timeoutCount = 4;

		Scheduler.getInstance().cancelAll();
		Scheduler.getInstance().clearSubsystems();
		Scheduler.getInstance().clearBehaviors();

		TelemetryImpl fakeTele = mock(TelemetryImpl.class);

		when(fakeTele.log()).thenReturn(mock(Telemetry.Log.class));
		when(fakeTele.addLine(anyString())).thenReturn(mock(Telemetry.Line.class, RETURNS_DEEP_STUBS));

		Scheduler.getInstance().init(fakeTele);

		Subsystem[] mockedSubsystems = new Subsystem[timeoutCount];
		for (int i = 0; i < timeoutCount; i++) {
			mockedSubsystems[i] = mock(Subsystem.class);
		}

		Scheduler.getInstance().initSubsystems(null);
		Scheduler.getInstance().loop(OpModeState.INIT_LOOP);

		TimeUnit[] timeUnits = {TimeUnit.NANOSECONDS, TimeUnit.MICROSECONDS,
				TimeUnit.MILLISECONDS, TimeUnit.SECONDS};
		long[] testValues = {1000000000L, 1000000L, 1000L, 1L};
		if (timeUnits.length != timeoutCount) throw new AssertionError("Check test array declarations.");
		if (testValues.length != timeoutCount) throw new AssertionError("Check test array declarations.");
		Task[] tasks = new Task[timeoutCount];
		for (int i = 0; i < timeoutCount; i++) {
			int finalI = i;
			tasks[i] = new Task() {
				@Override
				public void init() {
					running = true;
					addSubsystems(mockedSubsystems[finalI]);
				}

				@Override
				public void loop() {
				}

				@Override
				public void stop(boolean interrupted) {
					running = false;
				}
			}.withTimeout(testValues[i], timeUnits[i]);

			Scheduler.getInstance().schedule(tasks[i]);
		}

		// ---START TIMEOUT TEST---
		long startTime = System.nanoTime();
		long currentTime;
		long[] testMargins = { // 50 ms of margin
				50000000L,
				50000000L,
				50000000L,
				50000000L
		};
		int completedTasks = 0;
		boolean[] completed = new boolean[timeoutCount];
		while (((currentTime = System.nanoTime()) - startTime) < 120000000000L && completedTasks < timeoutCount) {
			Scheduler.getInstance().loop(OpModeState.RUN_LOOP);
			for (int i = 0; i < timeoutCount; i++) {
				boolean running = tasks[i].isRunning();
				if (!completed[i] && !running) {
					completed[i] = true;
					long taskRuntime = currentTime - startTime;
					long allowedRuntime = TimeUnit.NANOSECONDS.convert(testValues[i], timeUnits[i]);
					long runtimeDifference = Math.abs(taskRuntime - allowedRuntime);
					assertTrue(runtimeDifference <= testMargins[i],
							"Testing:            " + i +
									"\nExpected runtime:   " + allowedRuntime +
									"\nActual runtime:     " + taskRuntime +
									"\nRuntime difference: " + runtimeDifference +
									"\nAllowed difference: " + testMargins[i]);
					completedTasks++;
				}
			}
		}
		assertEquals(completedTasks, timeoutCount);
	}

	@DisplayName("Test Task Seconds")
	@Test
	public void testTaskTimeoutSeconds() {

	}
}
