package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class StartEndTask extends Task {
    protected final Runnable init;
    protected final Runnable stop;

    public StartEndTask(Telemetry telemetry, Runnable init, Runnable stop, Subsystem... subsystems) {
        super(telemetry);
        this.init = init;
        this.stop = stop;

        addSubsystems(subsystems);
    }

    @Override
    public void init() {
        init.run();
    }

    @Override
    public void loop() {

    }

    @Override
    public void stop(boolean interrupted) {
        stop.run();
    }
}
