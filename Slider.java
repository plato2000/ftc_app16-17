package org.firstinspires.ftc.team9450.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by plato2000 on 6/7/17.
 */
public class Slider extends Subsystem {

    private Servo sliderMotor;

    public enum SliderControlState {
        GO_LEFT, GO_RIGHT, GO_CENTER
    }

    private SliderControlState controlState;

    public Slider(Servo sliderMotor) {
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
        switch (controlState) {
            case GO_LEFT:
                sliderMotor.setPosition(0.1);
                break;
            case GO_RIGHT:
                sliderMotor.setPosition(0.9);
                break;
            case GO_CENTER:
                sliderMotor.setPosition(0.5);
                break;
        }
    }


}

