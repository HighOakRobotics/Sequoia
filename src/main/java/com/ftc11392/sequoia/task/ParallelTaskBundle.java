package com.ftc11392.sequoia.task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ParallelTaskBundle extends TaskBundle {
    private final Map<Task, Boolean> tasks = new HashMap<>(); // Task task, Boolean isRunning

    public ParallelTaskBundle(Telemetry telemetry, Task... tasks) {
        super(telemetry);

    }


    @Override
    public void addTasks(Task... tasks) {
        if (this.tasks.containsValue(true))
            throw new IllegalStateException("Do not add tasks will this bundle is still running.");

        registerBundledTasks(tasks);

        for (Task task : tasks) {
            if (!Collections.disjoint(task.getSubsystems(), subsystems)) {
                throw new IllegalArgumentException("Tasks have conflicting subsystem requirements.");
            }
            this.tasks.put(task, false);
            subsystems.addAll(task.getSubsystems());
        }
    }

    @Override
    public void init() {
        super.init();
        for (Map.Entry<Task, Boolean> task : tasks.entrySet()) {
            task.getKey().init();
            task.setValue(true);
        }
    }

    @Override
    public void loop() {
        super.loop();
        for (Map.Entry<Task, Boolean> task : tasks.entrySet()) {
            if (task.getValue()) {
                task.getKey().loop();
                if (!task.getKey().isRunning()) {
                    task.getKey().stop(false);
                    task.setValue(false);
                }
            }
        }
    }

    @Override
    public void stop(boolean interrupted) {
        super.stop(interrupted);
        if (interrupted) {
            for (Map.Entry<Task, Boolean> task : tasks.entrySet()) {
                if (task.getValue()) {
                    task.getKey().stop(true);
                }
            }
        }
    }

    @Override
    public boolean isRunning() {
        return tasks.values().contains(true);
    }
}
