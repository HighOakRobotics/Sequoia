package com.ftc11392.sequoia.task;

import com.ftc11392.sequoia.subsystem.Subsystem;
import com.ftc11392.sequoia.util.PIDFController;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

/**
 * A {@link Task} that controls a {@link Subsystem} using a {@link PIDFController}. This Task is perpetual until
 * interrupted.
 */
public class PIDFTask extends Task {

    protected final PIDFController controller;
    protected DoubleSupplier feedback, setpoint;
    protected DoubleConsumer output;

    public PIDFTask(Telemetry telemetry, PIDFController controller,
                    DoubleSupplier feedback, DoubleSupplier setpoint,
                    DoubleConsumer output, Subsystem... subsystems) {
        super(telemetry);
        this.controller = controller;
        this.feedback = feedback;
        this.setpoint = setpoint;
        this.output = output;
        this.subsystems.addAll(Arrays.asList(subsystems));
        this.running = true;
    }

    public PIDFTask(Telemetry telemetry, PIDFController controller,
                    DoubleSupplier feedback, double setpoint,
                    DoubleConsumer output, Subsystem... subsystems) {
        this(telemetry, controller, feedback, () -> setpoint, output, subsystems);
    }

    @Override
    public void init() {
        controller.resetController();
    }

    @Override
    public void loop() {
        output.accept(controller.control(setpoint.getAsDouble(), feedback.getAsDouble()));
    }

    @Override
    public void stop(boolean interrupted) {
        output.accept(0);
    }

    public  PIDFController getController() {
        return controller;
    }
}
