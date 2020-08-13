package com.ftc11392.sequoia.task;

public class DefaultTask extends Task {
	public DefaultTask() {
		super(null);
	}

	@Override
	public boolean isRunning() {
		return true;
	}
}
