package com.ftc11392.sequoia.task;

import com.qualcomm.robotcore.robocol.Command;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class TaskBundle extends Task {
	private static final HashSet<Task> bundledTasks = new HashSet<>();
	public TaskBundle(Telemetry telemetry) {
		super(telemetry);
	}

	public static boolean isBundled(Task task) {
		return bundledTasks.contains(task);
	}

	static void registerBundledTasks(Task... tasks) {
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


}
