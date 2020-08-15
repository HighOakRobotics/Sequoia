package com.ftc11392.sequoia.util;

import java.util.function.BiFunction;

/**
 * A PIDF (Proportional, Integral, Derivative, Feed-forward) controller
 * intended to control processes. It is recommended to disable feed-forward
 * when controlling position.
 */
public class PIDFController {

    protected Clock clock;
    protected BiFunction<Double, Double, Double> feedforward;
    protected double kP, kI, kD, kF, bias;
    private double lastIntegral;
    private long lastTime = 0;
    private double lastError = 0.0;

    /**
     * Instantiates a PIDFController
     *
     * @param kP the proportional term
     * @param kI the integral term
     * @param kD the derivative term
     * @param kF the feed-forward term
     * @param feedforward a function that takes the time in seconds, and a setpoint as arguments
     * @param bias a constant added to the output of the controller
     */
    public PIDFController(double kP, double kI, double kD,
                          double kF, BiFunction<Double, Double, Double> feedforward, double bias) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.feedforward = feedforward;
        this.bias = bias;
    }

    /**
     * Instantiates a PIDFController, using the default feed-forward function
     * of <code>(time, setpoint) -> setpoint/(time*2)</code>.
     *
     * @param kP the proportional term
     * @param kI the integral term
     * @param kD the derivative term
     * @param kF the feed-forward term
     */
    public PIDFController(double kP, double kI, double kD, double kF) {
        this(kP,kI,kD,kF,(time, setpoint) -> setpoint/(time*2),0.0);
    }

    /**
     * Instantiates a PIDFController as a PID controller.
     *
     * @param kP the proportional term
     * @param kI the integral term
     * @param kD the derivative term
     */
    public PIDFController(double kP, double kI, double kD) {
        this(kP,kI,kD,0.0);
    }

    /**
     * Resets the controller to its state after initialization. Immediately
     * restarts the clock.
     */
    public void resetController() {
        clock.startTiming();
        lastTime = 0;
        lastIntegral = 0.0;
        lastError = 0.0;
    }

    /**
     * Returns a value to control a process to move to a desired setpoint.
     * This is a PIDF (Proportional, Integral, Derivative, Feed-forward)
     * controller.
     *
     * @param setpoint the desired setpoint for the process
     * @param feedback current output of the process
     * @return         the output of the controller, to control the process
     */
    public double control(double setpoint, double feedback) {
        long time = clock.getNanoseconds();
        double timeSec = clock.getSeconds();
        double timeInterval = time - lastTime;

        double error = setpoint - feedback;
        double integral = lastIntegral + error * timeInterval;
        double derivative = (error - lastError) / timeInterval;
        double feedforward = this.feedforward.apply(timeSec, setpoint);

        double output = kP*error + kI*integral + kD*derivative + kF*feedforward + bias;

        lastError = error;
        lastIntegral = integral;
        lastTime = time;

        return output;
    }
}
