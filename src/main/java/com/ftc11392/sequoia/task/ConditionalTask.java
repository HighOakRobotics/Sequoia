package com.ftc11392.sequoia.task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.function.BooleanSupplier;

/**
 * A {@link Task} that schedules a Task based on the value a boolean.
 */
public class ConditionalTask extends SwitchTask {

	public ConditionalTask(Telemetry telemetry, Task ifTrue, Task ifFalse, BooleanSupplier checker) {
		super(telemetry, new HashMap<Object, Task>() {{
			put(true, ifTrue);
			put(false, ifFalse);
		}}, checker::getAsBoolean);
	}
	
}
