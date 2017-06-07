package org.firstinspires.ftc.team9450.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by plato2000 on 6/7/17.
 */
public class Intake extends Subsystem {

    private DcMotor intakeMotor;

    public enum IntakeControlState {
        ON, OFF, REVERSE
    }

    private IntakeControlState state;

    public Intake(DcMotor intakeMotor) {
        this.intakeMotor = intakeMotor;
        state = IntakeControlState.ON;
    }

    public void setState(IntakeControlState state) {
        this.state = state;
    }

    @Override
    public void stop() {
        intakeMotor.setPower(0);
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void loop() {
        switch(state) {
            case OFF:
                intakeMotor.setPower(0);
                break;
            case REVERSE:
                intakeMotor.setPower(1);
                break;
            case ON:
            default:
                intakeMotor.setPower(-1);
                break;
        }
    }
}
