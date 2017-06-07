package org.firstinspires.ftc.team9450.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team9450.util.Constants;

/**
 * Created by plato2000 on 6/7/17.
 */
public class Shooter extends Subsystem {

    private DcMotor flywheelMotor;

    public enum ShooterControlState {
        ON, OFF
    }

    private ShooterControlState state;

    public Shooter(DcMotor flywheelMotor) {
        this.flywheelMotor = flywheelMotor;
        state = ShooterControlState.OFF;
    }

    public void setState(ShooterControlState state) {
        this.state = state;
    }

    @Override
    public void stop() {
        flywheelMotor.setPower(0);
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void loop() {
        switch(state) {
            case ON:
                flywheelMotor.setPower(Constants.Shooter.DEFAULT_POWER);
                break;
            case OFF:
            default:
                flywheelMotor.setPower(0);
        }
    }
}
