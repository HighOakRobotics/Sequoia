package com.ftc11392.sequoia.util;

import java.util.concurrent.TimeUnit;

public class Clock {
	private long startTime;

	/**
	 * Starts the clock.
	 */
	public void startTiming() {
		startTime = System.nanoTime();
	}

	/**
	 * Returns the time since the clock started in nanoseconds.
	 *
	 * @return The time since the clock started in nanoseconds.
	 */
	public long getNanoseconds() {
		return System.nanoTime() - startTime;
	}

	/**
	 * Returns the time since the clock started in the given {@link TimeUnit}.
	 *
	 * @param time the unit of time desired
	 * @return The time since the clock started in the given TimeUnit.
	 */
	public long getTime(TimeUnit time) {
		return time.convert(startTime, TimeUnit.NANOSECONDS);
	}

	/**
	 * Returns the time since the clock started in milliseconds.
	 *
	 * @return The time since the clock started in milliseconds.
	 */
	public long getMillis() {
		return getTime(TimeUnit.MILLISECONDS);
	}

	/**
	 * Returns the time since the clock started in seconds.
	 *
	 * @return The time since the clock started in seconds.
	 */
	public double getSeconds() { return getTime(TimeUnit.SECONDS); }
}
