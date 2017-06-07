package org.firstinspires.ftc.team9450.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.team9450.util.DriveSignal;

/**
 * Created by plato2000 on 6/7/17.
 */
public class Drivetrain extends Subsystem {

    private DcMotor left;
    private DcMotor right;

    private DriveControlState controlState;

    public enum DriveControlState {
        OPEN_LOOP
    }

    public Drivetrain(DcMotor left, DcMotor right) {
        this.left = left;
        this.right = right;


    }

    public void setOpenLoop(DriveSignal signal) {
        controlState = DriveControlState.OPEN_LOOP;
        left.setPower(-signal.leftMotor);
        right.setPower(signal.rightMotor);
    }

    @Override
    public synchronized void stop() {
        setOpenLoop(DriveSignal.NEUTRAL);
    }

    @Override
    public synchronized void zeroSensors() {

    }

    public void loop() {
        switch(controlState) {
            case OPEN_LOOP:
            default:
                setOpenLoop(DriveSignal.NEUTRAL);
                break;
        }
    }
}
