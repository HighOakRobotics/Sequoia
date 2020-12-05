package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;
import com.ftc11392.sequoia.util.Clock;
import com.ftc11392.sequoia.util.OpModeState;
import com.qualcomm.robotcore.hardware.HardwareMap;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.*;


public final class Scheduler {
	private static final Scheduler instance = new Scheduler();
	//private static final Logger log = LogManager.getLogger(Scheduler.class);
	private final Clock clock = new Clock();

	private final List<Task> toSchedule = new ArrayList<>();
	private final List<Task> toCancel = new ArrayList<>();
	private final List<Subsystem> subsystems = new ArrayList<>();
	private final List<Task> scheduledTasks = new ArrayList<>();
	private final List<Runnable> behaviors = new ArrayList<>();

	private final Map<Subsystem, Task> bindings = new HashMap<>();
	private Telemetry telemetry;
	private boolean inLoop;

	public static synchronized Scheduler getInstance() {
		return instance;
	}

	/**
	 * Provides the Scheduler with its requirements.
	 *
	 * @param telemetry a Telemetry object from {@link com.qualcomm.robotcore.eventloop.opmode.OpMode} that allows
	 *                  the scheduler to send messages to the Driver Station
	 */
	public void init(Telemetry telemetry) {
		this.telemetry = telemetry;
	}

	/**
	 * Initializes all subsystem hardware.
	 *
	 * @param hardwareMap The {@link HardwareMap} to be used for subsystem initialization.
	 */
	public void initSubsystems(HardwareMap hardwareMap) {
		subsystems.sort(Comparator.comparingInt(Subsystem::getPriority));
		for (Subsystem subsystem : subsystems) {
			subsystem.assignTelemetry(telemetry);
			subsystem.initialize(hardwareMap);
		}
		telemetry.log().add("Initialized " + subsystems.size() + " subsystems.");
	}

	public void startSubsystems() {
		for (Subsystem subsystem : subsystems) {
			subsystem.start();
		}
	}

	public void stopSubsystems() {
		for (Subsystem subsystem : subsystems) {
			subsystem.stop();
		}
	}

	/**
	 * Adds the given subsystems ({@link Subsystem}) to the Scheduler.
	 *
	 * @param subsystems the subsystems to add
	 */
	public void registerSubsystem(Subsystem... subsystems) {
		this.subsystems.addAll(Arrays.asList(subsystems));
	}

	/**
	 * Clears all subsystems ({@link Subsystem}).
	 */
	public void clearSubsystems() {
		subsystems.clear();
	}

	/**
	 * Runs the <code>init()</code> method of the given {@link Task}. Binds the Task to its required
	 * subsystems ({@link Subsystem})
	 *
	 * @param task the Task to cancel
	 */
	private void initTask(Task task) {
		task.setTelemetry(telemetry);
		scheduledTasks.add(task);
		task.init();
		Subsystem[] taskSubsystems = task.getSubsystems().toArray(new Subsystem[0]);
		for (int i = 0; i < taskSubsystems.length; i++) {
			bindings.put(taskSubsystems[i], task);
		}
	}

	/**
	 * Schedules the given {@link Task}.
	 *
	 * @param task the Task to schedule
	 */
	public void schedule(Task task) {
		if (inLoop) {
			toSchedule.add(task);
			return;
		}

		if (!Collections.disjoint(bindings.keySet(), task.getSubsystems())) {
			Subsystem[] taskSubsystems = task.getSubsystems().toArray(new Subsystem[0]);
			for (int i = 0; i < taskSubsystems.length; i++) {
				if (bindings.containsKey(taskSubsystems[i]) && !bindings.get(taskSubsystems[i]).isInterruptible()) {
					return;
				}
			}
			for (int i = 0; i < taskSubsystems.length; i++)
				if (bindings.containsKey(taskSubsystems[i]))
					cancel(bindings.get(taskSubsystems[i]));
		}
		initTask(task);
	}

	/**
	 * Cancels the given {@link Task}.
	 *
	 * @param task the Task to cancel
	 */
	public void cancel(Task task) {
		if (inLoop) {
			toCancel.add(task);
			return;
		}

		task.stop(true);
		scheduledTasks.remove(task);
		bindings.keySet().removeAll(task.getSubsystems());
	}

	/**
	 * Cancels all tasks ({@link Task})
	 */
	public void cancelAll() {
		for (int i = scheduledTasks.size() - 1; i >= 0; i--) {
			cancel(scheduledTasks.get(i));
		}
	}

	public boolean isScheduled(Task... tasks) {
		return scheduledTasks.containsAll(Arrays.asList(tasks));
	}

	public void addBehavior(Runnable behavior) {
		behaviors.add(behavior);
	}

	public void clearBehaviors() {
		behaviors.clear();
	}


	private void runPeriodics(OpModeState state) {
		switch (state) {
			case INIT_LOOP:
				for (int i = 0; i < subsystems.size(); i++)
					subsystems.get(i).initPeriodic();
				break;
			case RUN_LOOP:
				for (int i = 0; i < subsystems.size(); i++)
					subsystems.get(i).runPeriodic();
				break;
			default:
				// Should there be a SchedulerException?
				throw new TaskException("The Scheduler should not be running in this state.");
		}
	}

	private void runTasks() {
		// NOW ENTERING RUN LOOP - set run loop boolean to true
		// Pipe any new scheduled tasks to a separate queue while the run loop boolean is true.
		inLoop = true;

		// For each command:
		// Run loop methods of this command (initloop or loop depending on state)
		// If command is now finished, remove it from iterator / any requirements it had required
		for (int i = 0; i < scheduledTasks.size(); i++) {
			scheduledTasks.get(i).loop();
			if (!scheduledTasks.get(i).isRunning()) {
				scheduledTasks.get(i).stop(false);
				bindings.keySet().removeAll(scheduledTasks.get(i).getSubsystems());
			}
		}

		// NOW EXITING RUN LOOP - set run loop boolean to false
		inLoop = false;
	}

	/**
	 * Run this method regularly. Runs {@link Subsystem} periodics,
	 * polls behaviors, and schedules tasks ({@link Task}).
	 *
	 * @param state the state of the {@link com.qualcomm.robotcore.eventloop.opmode.OpMode} right now
	 */
	public void loop(OpModeState state) {
		// Run this in a loop
		clock.startTiming();
		// Run loop methods of all subsystems (initloop or loop depending on robot state)
		runPeriodics(state);
		// Check for any triggers (poll buttons) and add any corresponding commands
		// (do nothing if it's trying to use already-used subsystems that can't be interrupted)
		for (int i = 0; i < behaviors.size(); i++) {
			behaviors.get(i).run();
		}

		runTasks();

		// Resolve any commands that had to be queued because of iteration here (schedule them)
		// Empty any to schedule / to cancel queues
		for (int i = 0; i < toSchedule.size(); i++)
			schedule(toSchedule.get(i));
		for (int i = 0; i < toCancel.size(); i++)
			cancel(toCancel.get(i));

		toSchedule.clear();
		toCancel.clear();

		// Now check all subsystems to see if they have been used - any unrequired subsystems
		// will have the default method called.
		for (int i = 0; i < subsystems.size(); i++) {
			schedule(subsystems.get(i).getDefaultTask());
		}

		long durationMs = clock.getMillis();
		double duration = clock.getSeconds();
		telemetry.addLine("Scheduler")
				.addData("Time", durationMs + " ms")
				.addData("Freq", "%.2f Hz", 1.0 / duration);
		//log.info("Time: " + durationMs + "ms, Freq: " + 1.0 / duration + "Hz");
	}
}
