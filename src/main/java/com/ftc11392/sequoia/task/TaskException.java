package com.ftc11392.sequoia.task;

/**
 * Exceptions related to tasks. Usually also related to tasks (and their dependencies) in bundles.
 */
public class TaskException extends RuntimeException {
	public TaskException() {
		super();
	}

	public TaskException(String message) {
		super(message);
	}
}
