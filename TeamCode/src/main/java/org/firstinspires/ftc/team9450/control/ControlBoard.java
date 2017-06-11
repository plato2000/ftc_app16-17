package org.firstinspires.ftc.team9450.control;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.team9450.subsystems.Intake;

/**
 * Created by plato2000 on 6/11/17.
 */
public class ControlBoard {
    private Gamepad driverController;

    public ControlBoard(Gamepad controller) {
        driverController = controller;
    }

    public boolean moveSliderLeft() {
        return driverController.dpad_left;
    }

    public boolean moveSliderRight() {
        return driverController.dpad_right;
    }

    public Intake.IntakeControlState intakeControl() {
        if(driverController.right_trigger > 0.5) {
            return Intake.IntakeControlState.ON;
        } else if(driverController.left_trigger > 0.5){
            return Intake.IntakeControlState.REVERSE;
        } else {
            return Intake.IntakeControlState.OFF;
        }
    }

    public boolean reduceSpeed() {
        return driverController.left_bumper;
    }

    public float throttle() {
        return driverController.left_stick_y;
    }

    public float turn() {
        return driverController.left_stick_x;
    }

    public boolean toggleShooterOn() {
        return driverController.x;
    }

    public boolean toggleShooterOff() {
        return driverController.b;
    }
}
