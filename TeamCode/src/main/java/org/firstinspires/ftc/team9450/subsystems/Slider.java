package org.firstinspires.ftc.team9450.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by plato2000 on 6/7/17.
 */
public class Slider extends Subsystem {

    private DcMotor sliderMotor;

    public enum SliderControlState {
        GO_LEFT, GO_RIGHT, GO_CENTER
    }

    public enum SliderPositionState {
        LEFT, RIGHT, CENTER
    }

    private SliderPositionState positionState;
    private SliderControlState controlState;

    public Slider(DcMotor sliderMotor) {
        this.sliderMotor = sliderMotor;
    }

    public void setState(SliderControlState controlState) {
        this.controlState = controlState;
    }


    @Override
    public void stop() {

    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void loop() {

    }
}
