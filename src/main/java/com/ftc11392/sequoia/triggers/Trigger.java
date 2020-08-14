package com.ftc11392.sequoia.triggers;

import com.ftc11392.sequoia.task.Scheduler;
import com.ftc11392.sequoia.task.Task;

import java.util.function.BooleanSupplier;

public class Trigger {
	public enum WhileBehavior {
		NOTHING,
		SCHEDULE
	}

	public enum EdgeBehavior {
		NOTHING,
		SCHEDULE,
		CANCEL
	}

	private final BooleanSupplier isActive;

	public Trigger(BooleanSupplier isActive) {
		this.isActive = isActive;
	}

	public Trigger() {
		isActive = () -> false;
	}

	public boolean poll() {
		return isActive.getAsBoolean();
	}

	/**
	 * Schedules the task only once when the input changes from off to on.
	 * <p>
	 * In other words, this is a rising-edge monostable circuit.
	 * <p>
	 * WHILE OFF : Nothing
	 * OFF -> ON : Schedules task once
	 * WHILE ON  : Nothing
	 * ON -> OFF : Nothing
	 *
	 * @param task The task to run.
	 * @return This trigger, for chaining method calls.
	 */
	public Trigger rising(final Task task) {
		return useBehavior(task, WhileBehavior.NOTHING, EdgeBehavior.SCHEDULE, WhileBehavior.NOTHING, EdgeBehavior.NOTHING);
	}

	/**
	 * Schedules the task only once when the input changes from on to off.
	 * <p>
	 * In other words, this is a falling-edge monostable circuit.
	 * <p>
	 * WHILE OFF : Nothing
	 * OFF -> ON : Nothing
	 * WHILE ON  : Nothing
	 * ON -> OFF : Schedules task once
	 *
	 * @param task The task to run.
	 * @return This trigger, for chaining method calls.
	 */
	public Trigger falling(final Task task) {
		return useBehavior(task, WhileBehavior.NOTHING, EdgeBehavior.NOTHING, WhileBehavior.NOTHING, EdgeBehavior.SCHEDULE);
	}

	/**
	 * Schedules the task only once when the input changes from off to on, and cancels the task when the input changes from on to off.
	 * <p>
	 * WHILE OFF : Nothing
	 * OFF -> ON : Schedules task once
	 * WHILE ON  : Nothing
	 * ON -> OFF : Cancels task
	 *
	 * @param task The task to run.
	 * @return This trigger, for chaining method calls.
	 */
	public Trigger risingWithCancel(final Task task) {
		return useBehavior(task, WhileBehavior.NOTHING, EdgeBehavior.SCHEDULE, WhileBehavior.NOTHING, EdgeBehavior.CANCEL);
	}

	/**
	 * Schedules the task only once when the input changes from on to off, and cancels the task when the input changes from off to on.
	 * <p>
	 * WHILE OFF : Nothing
	 * OFF -> ON : Cancels task
	 * WHILE ON  : Nothing
	 * ON -> OFF : Schedules task once
	 *
	 * @param task The task to run.
	 * @return This trigger, for chaining method calls.
	 */
	public Trigger fallingWithCancel(final Task task) {
		return useBehavior(task, WhileBehavior.NOTHING, EdgeBehavior.SCHEDULE, WhileBehavior.NOTHING, EdgeBehavior.CANCEL);
	}

	/**
	 * Big boy method for consolidating all rising/falling/while logic.
	 *
	 * @param task    The task to schedule / cancel.
	 * @param off     The intended behavior while off.
	 * @param rising  The intended behavior upon rising edge.
	 * @param on      The intended behavior while on.
	 * @param falling The intended behavior upon falling edge.
	 * @return The Trigger, for convenience.
	 */
	public Trigger useBehavior(Task task, WhileBehavior off, EdgeBehavior rising, WhileBehavior on, EdgeBehavior falling) {
		Scheduler scheduler = Scheduler.getInstance();

		scheduler.addBehavior(new Runnable() {
			boolean lastActive = false;

			@Override
			public void run() {
				boolean active = poll();

				if (active && lastActive) {
					// While on
					switch (on) {
						case NOTHING:
							break;
						case SCHEDULE:
							scheduler.schedule(task);
							break;
					}
				} else if (!active && !lastActive) {
					// While off
					switch (off) {
						case NOTHING:
							break;
						case SCHEDULE:
							scheduler.schedule(task);
							break;
					}
				} else if (active) {
					// Rising edge
					switch (rising) {
						case NOTHING:
							break;
						case SCHEDULE:
							scheduler.schedule(task);
							break;
						case CANCEL:
							scheduler.cancel(task);
							break;
					}
				} else {
					// Falling edge
					switch (falling) {
						case NOTHING:
							break;
						case SCHEDULE:
							scheduler.schedule(task);
							break;
						case CANCEL:
							scheduler.cancel(task);
							break;
					}
				}

				lastActive = active;
			}
		});

		return this;
	}
}
