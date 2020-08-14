package com.ftc11392.sequoia.task;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.Map;

public class ParallelTaskBundle extends TaskBundle {
    private final Map<Task, Boolean> commands = new HashMap<>(); // Task task, Boolean isRunning

    public ParallelTaskBundle(Telemetry telemetry, Task... tasks) {
        super(telemetry);

    }


    @Override
    public void addTasks(Task... tasks) {

    }
}
