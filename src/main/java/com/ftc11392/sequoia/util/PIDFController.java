package com.ftc11392.sequoia.util;

public class PIDFController {

    Clock clock;
    double kP, kI, kD, kF, bias;
    double lastIntegral;
    long lastTime = 0;
    double lastError = 0.0;

    public PIDFController(double kP, double kI, double kD, double kF, double bias) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.bias = bias;
    }

    public PIDFController(double kP, double kI, double kD, double kF) {
        this(kP,kI,kD,kF,0.0);
    }

    public PIDFController(double kP, double kI, double kD) {
        this(kP,kI,kD,0.0);
    }

    public void resetController() {
        clock.startTiming();
        lastTime = 0;
        lastIntegral = 0.0;
        lastError = 0.0;
    }

    public double control(double setpoint, double feedback) {
        long time = clock.getNanoseconds();
        double timeSec = clock.getSeconds();
        double timeInterval = time - lastTime;

        double error = setpoint - feedback;
        double integral = lastIntegral + error * timeInterval;
        double derivative = (error - lastError) / timeInterval;
        double feedforward = (1/timeSec) * setpoint;

        double output = kP*error + kI*integral + kD*derivative + kF*feedforward + bias;

        lastError = error;
        lastIntegral = integral;
        lastTime = time;

        return output;
    }
}
