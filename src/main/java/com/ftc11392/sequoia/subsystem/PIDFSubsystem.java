package com.ftc11392.sequoia.subsystem;

import com.ftc11392.sequoia.task.DefaultTask;
import com.ftc11392.sequoia.task.Task;
import com.ftc11392.sequoia.util.PIDFController;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * A {@link Subsystem} that uses a {@link PIDFController} to control itself.
 */
public abstract class PIDFSubsystem extends Subsystem{
    protected final PIDFController controller;

    protected boolean enabled = false;
    private double setpoint;

    /**
     * Instantiates a PIDFSubsystem.
     *
     * @param controller the PIDFController to use
     * @param setpoint the initial setpoint
     * @param telemetry {@link Telemetry} from an {@link com.qualcomm.robotcore.eventloop.opmode.OpMode}
     *                                   for the task to be able to send information to a Driver Station.
     * @param defaultTask the default Task of the Subsystem
     */
    public PIDFSubsystem(PIDFController controller, double setpoint, Telemetry telemetry, Task defaultTask) {
        super(telemetry, defaultTask);
        this.controller = controller;
        this.setpoint = setpoint;
    }

    public PIDFSubsystem(PIDFController controller, double setpoint, Telemetry telemetry) {
        this(controller, setpoint, telemetry, new DefaultTask());
    }

    /**
     * Instantiates a PIDFSubsystem with an initial setpoint of 0.
     *
     * @param controller the PIDFController to use
     * @param telemetry {@link Telemetry} from an {@link com.qualcomm.robotcore.eventloop.opmode.OpMode}
     *                                   for the task to be able to send information to a Driver Station.
     */
    public PIDFSubsystem(PIDFController controller, Telemetry telemetry) {
        this(controller, 0, telemetry);
    }

    @Override
    public void runPeriodic() {
        if (enabled)
            useOutput(controller.control(getFeedback(), setpoint));
    }

    @Override
    public void initPeriodic() {
        if (enabled)
            useOutput(controller.control(getFeedback(), setpoint));
    }

    /**
     * Uses the controller output in the Subsystem.
     *
     * @param output the controller output.
     */
    protected abstract void useOutput(double output);

    /**
     * Returns the Subsystem feedback.
     *
     * @return the feedback to return
     */
    protected abstract double getFeedback();

    /**
     * Enables PIDF control for the Subsystem.
     */
    public void enable() {
        enabled = true;
        controller.resetController();
    }

    /**
     * Disables PIDF control for the Subsystem.
     */
    public void disable() {
        enabled = false;
        useOutput(0);
    }

    public boolean isEnabled() { return enabled; }
}
