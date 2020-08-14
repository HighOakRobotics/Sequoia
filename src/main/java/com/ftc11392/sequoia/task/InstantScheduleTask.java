package com.ftc11392.sequoia.task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InstantScheduleTask extends Task {
	private final Set<Task> toSchedule = new HashSet<>();

	public InstantScheduleTask(Telemetry telemetry, Task... tasks) {
		super(telemetry);
		toSchedule.addAll(Arrays.asList(tasks));
		running = false;
	}

	@Override
	public void init() {
		Scheduler instance = Scheduler.getInstance();
		for(Task task : toSchedule) {
			instance.schedule(task);
		}
	}

}
