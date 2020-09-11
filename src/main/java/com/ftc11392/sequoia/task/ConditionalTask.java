package com.ftc11392.sequoia.task;

import java.util.HashMap;
import java.util.function.BooleanSupplier;

/**
 * A {@link Task} that schedules a Task based on the value a boolean.
 */
public class ConditionalTask extends SwitchTask {

	public ConditionalTask(Task ifTrue, Task ifFalse, BooleanSupplier checker) {
		super(new HashMap<Object, Task>() {{
			put(true, ifTrue);
			put(false, ifFalse);
		}}, checker::getAsBoolean);
	}

}
