package com.ftc11392.sequoia.task;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public abstract class TaskBundle extends Task {
	private static final HashSet<Task> bundledTasks = new HashSet<>();

	public static boolean isBundled(Task task) {
		return bundledTasks.contains(task);
	}

	/**
	 * Adds the given tasks ({@link Task}) to the TaskBundle.
	 *
	 * @param tasks the tasks to add
	 */
	public static void registerBundledTasks(Task... tasks) {
		bundledTasks.addAll(Arrays.asList(tasks));
	}

	public static void clearBundledTasks() {
		bundledTasks.clear();
	}

	public static void removedBundledTask(Task task) {
		bundledTasks.remove(task);
	}

	public static void requireUnbundled(Collection<Task> tasks) {
		if (!Collections.disjoint(tasks, bundledTasks)) {
			throw new TaskException("oy buffoon why you bundle task twice");
		}
	}

	public static void requireUnbundled(Task... tasks) {
		requireUnbundled(Arrays.asList(tasks));
	}

	public abstract void addTasks(Task... tasks);
}
