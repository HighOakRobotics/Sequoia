package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;
import com.ftc11392.sequoia.util.OpModeState;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ParallelTaskBundleTest {

	@DisplayName("Test Parallel Task Bundle")
	@Test
	public void TestParallelTaskBundle() {
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

		ParallelTaskBundle testParallelTaskBundle = new ParallelTaskBundle(testTask, testTask2);

		Scheduler.getInstance().schedule(testParallelTaskBundle);
		Scheduler.getInstance().loop(OpModeState.RUN_LOOP);

		assertTrue(testTask.isRunning());

		Scheduler.getInstance().cancelAll();
		Scheduler.getInstance().loop(OpModeState.RUN_LOOP);

		assertFalse(testTask.isRunning());
	}

	@DisplayName("Test Parallel Task Bundle Same Subsystem")
	@Test
	public void TestSameSubsystem() {
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
			}

			@Override
			public void loop() {
			}

			@Override
			public void stop(boolean interrupted) {
				running = false;
			}
		};

		testTask.addSubsystems(mockedSubsystem);

		Task testTask2 = new Task() {

			@Override
			public void init() {
				running = true;
			}

			@Override
			public void loop() {
			}

			@Override
			public void stop(boolean interrupted) {
				running = false;
			}
		};

		testTask2.addSubsystems(mockedSubsystem);

		assertEquals(testTask.getSubsystems().toArray()[0], testTask2.getSubsystems().toArray()[0]);
		Throwable thrownException = assertThrows(TaskException.class, () -> {
			ParallelTaskBundle testParallelTaskBundle = new ParallelTaskBundle(testTask, testTask2);
		});
		assertEquals(thrownException.getMessage(), "Multiple tasks in a parallel bundle cannot require the same subsystems");
	}
}
