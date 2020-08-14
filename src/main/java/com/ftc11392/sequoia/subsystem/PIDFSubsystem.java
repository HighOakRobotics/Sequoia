package com.ftc11392.sequoia.subsystem;

import com.ftc11392.sequoia.util.PIDFController;

public abstract class PIDFSubsystem extends Subsystem{
    protected final PIDFController controller;

    protected boolean enabled = false;
    private double setpoint;

    public PIDFSubsystem(PIDFController controller, double setpoint) {
        this.controller = controller;
        this.setpoint = setpoint;
    }

    public PIDFSubsystem(PIDFController controller) {
        this(controller, 0);
    }

    @Override
    public void loop() {
        super.loop();
        if (enabled)
            useOutput(controller.control(getFeedback(), setpoint));
    }

    @Override
    public void initLoop() {
        super.initLoop();
        if (enabled)
            useOutput(controller.control(getFeedback(), setpoint));
    }

    protected abstract void useOutput(double output);

    protected abstract double getFeedback();

    public void enable() {
        enabled = true;
        controller.resetController();
    }

    public void disable() {
        enabled = false;
        useOutput(0);
    }

    public boolean isEnabled() { return enabled; }
}
