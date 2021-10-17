package com.ftc11392.sequoia.subsystem;

import com.ftc11392.sequoia.task.DefaultTask;
import com.ftc11392.sequoia.task.Task;
import com.ftc11392.sequoia.util.PIDFController;

/**
 * A {@link Subsystem} that uses a {@link PIDFController} to control itself.
 */
public abstract class PIDFSubsystem extends Subsystem {
	protected final PIDFController controller;
	protected double setpoint;
	protected boolean enabled = false;

	/**
	 * Instantiates a PIDFSubsystem.
	 *
	 * @param controller  the PIDFController to use
	 * @param setpoint    the initial setpoint
	 * @param defaultTask the default Task of the Subsystem
	 */
	public PIDFSubsystem(PIDFController controller, double setpoint, Task defaultTask) {
		super(defaultTask);
		this.controller = controller;
		this.setpoint = setpoint;
	}

	public PIDFSubsystem(PIDFController controller, double setpoint) {
		this(controller, setpoint, new DefaultTask());
	}

	public double getSetpoint() {
		return setpoint;
	}

	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	/**
	 * Instantiates a PIDFSubsystem with an initial setpoint of 0.
	 *
	 * @param controller the PIDFController to use
	 */
	public PIDFSubsystem(PIDFController controller) {
		this(controller, 0);
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

	public boolean isEnabled() {
		return enabled;
	}
}
