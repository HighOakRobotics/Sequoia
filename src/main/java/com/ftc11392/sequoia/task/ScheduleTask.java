package com.ftc11392.sequoia.task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ScheduleTask extends Task {
	private final Set<Task> toSchedule = new HashSet<>();

	public ScheduleTask(Telemetry telemetry, Task... tasks) {
		super(telemetry);
		toSchedule.addAll(Arrays.asList(tasks));
	}

	@Override
	public void init() {
		Scheduler instance = Scheduler.getInstance();
		for(Task task : toSchedule) {
			instance.schedule(task);
		}
	}

	@Override
	public void loop() {
		running = false;
		Scheduler instance = Scheduler.getInstance();
		for(Task task : toSchedule) {
			if(instance.isScheduled(task)) {
				running = true;
			}
		}
	}

	@Override
	public void stop(boolean interrupted) {
		Scheduler instance = Scheduler.getInstance();
		if (interrupted) {
			for(Task task : toSchedule) {
				instance.cancel(task);
			}
		}
	}
}
