package com.ftc11392.sequoia.task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

import static com.ftc11392.sequoia.task.TaskBundle.requireUnbundled;

public class ConditionalTask extends SwitchTask {

	public ConditionalTask(Telemetry telemetry, Task ifTrue, Task ifFalse, BooleanSupplier checker) {
		super(telemetry, new HashMap<Object, Task>() {{
			put(true, ifTrue);
			put(false, ifFalse);
		}}, checker::getAsBoolean);
	}
	
}
