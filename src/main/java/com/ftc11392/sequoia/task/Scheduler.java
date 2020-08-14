package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;
import com.ftc11392.sequoia.util.OpModeState;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Scheduler {
	private static final Scheduler instance = new Scheduler();
	private Telemetry telemetry;

	public void init(Telemetry telemetry) {
		this.telemetry = telemetry;
	}


	public static Scheduler getInstance() {
		return instance;
	}

	public void registerSubsystem(Subsystem... subsystems) {
		this.subsystems.addAll(Arrays.asList(subsystems));
	}

	private void initTask(Task task) {
		scheduledTasks.add(task);
		task.init();
		for (Subsystem subsystem : task.getSubsystems()) {
			bindings.put(subsystem, task);
		}
	}

	public void schedule(Task task) {
		if (inLoop) {
			toSchedule.add(task);
			return;
		}

		if (!Collections.disjoint(bindings.keySet(), task.getSubsystems())) {
			for (Subsystem subsystem : task.getSubsystems()) {
				if (bindings.containsKey(subsystem) && !bindings.get(subsystem).isInterruptible()) {
					return;
				}
			}
			for (Subsystem subsystem : task.getSubsystems())
				if (bindings.containsKey(subsystem))
					cancel(bindings.get(subsystem));
		}
		initTask(task);
	}

	public void cancel(Task task) {
		if (inLoop) {
			toCancel.add(task);
			return;
		}

		task.stop(true);
		scheduledTasks.remove(task);
		bindings.keySet().removeAll(task.getSubsystems());
	}

	public void cancelAll() {
		for (Task task : scheduledTasks) {
			cancel(task);
		}
	}

	public void addBehavior(Runnable behavior) {
		behaviors.add(behavior);
	}

	public void clearBehaviors() {
		behaviors.clear();
	}

	private final List<Task> toSchedule = new ArrayList<>();
	private final List<Task> toCancel = new ArrayList<>();
	private final List<Subsystem> subsystems = new ArrayList<>();
	private final List<Task> scheduledTasks = new ArrayList<>();
	private final List<Runnable> behaviors = new ArrayList<>();

	private final Map<Subsystem, Task> bindings = new HashMap<>();
	private boolean inLoop;

	public void loop(OpModeState state) {
		// Run this in a loop

		// Run loop methods of all subsystems (initloop or loop depending on robot state)
		switch (state) {
			case INIT_LOOP:
				for (Subsystem subsystem : subsystems)
					subsystem.initLoop();
				break;
			case RUN_LOOP:
				for (Subsystem subsystem : subsystems)
					subsystem.loop();
				break;
			default:
				// Should there be a SchedulerException?
				throw new RuntimeException("The Scheduler should not be running in this state.");
		}

		// Check for any triggers (poll buttons) and add any corresponding commands
		// (do nothing if it's trying to use already-used subsystems that can't be interrupted)

		for (Runnable behavior : behaviors) {
			behavior.run();
		}

		// NOW ENTERING RUN LOOP - set run loop boolean to true
		// Pipe any new scheduled tasks to a separate queue while the run loop boolean is true.
		inLoop = true;

		// For each command:
		// Run loop methods of this command (initloop or loop depending on state)
		// If command is now finished, remove it from iterator / any requirements it had required
		for (Task task : scheduledTasks) {
			task.loop();
			if (!task.isRunning()) {
				task.stop(false);
				bindings.keySet().removeAll(task.getSubsystems());
			}
		}

		// NOW EXITING RUN LOOP - set run loop boolean to false
		inLoop = false;

		// Resolve any commands that had to be queued because of iteration here (schedule them)
		// Empty any to schedule / to cancel queues
		for (Task task : toSchedule)
			schedule(task);
		for (Task task : toCancel)
			cancel(task);

		toSchedule.clear();
		toCancel.clear();

		// Now check all subsystems to see if they have been used - any unrequired subsystems
		// will have the default method called.
		for (Subsystem subsystem : subsystems) {
			if (subsystem.getDefaultTask() != null) {
				schedule(subsystem.getDefaultTask());
			}
		}
	}
}