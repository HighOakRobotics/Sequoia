package com.ftc11392.sequoia.util;

import java.util.concurrent.TimeUnit;

public class Clock {
	private long startTime;

	public void startTiming() {
		startTime = System.nanoTime();
	}

	public long getNanoseconds() {
		return System.nanoTime() - startTime;
	}

	public long getTime(TimeUnit time) {
		return time.convert(startTime, TimeUnit.NANOSECONDS);
	}

	public long getMillis() {
		return getTime(TimeUnit.MILLISECONDS);
	}
}
