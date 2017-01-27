package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Winston on 11/23/16.
 */

@Autonomous(name = "Forward", group = "Forward")
public class forward extends LinearOpMode{

    final static float PERCENT_MAX_POWER = 0.20f;
    final static float LEFT_FIX = 1.00f;
    DcMotor motorRight;
    DcMotor motorLeft;

    public forward(){

    }

    public void runOpMode() throws InterruptedException{


        motorRight = hardwareMap.dcMotor.get("right");
        motorLeft = hardwareMap.dcMotor.get("left");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setMaxSpeed(800);
        motorRight.setMaxSpeed(800);

        waitForStart();

        float speedStart=1;
        motorRight.setTargetPosition(150);
        motorLeft.setTargetPosition(150);
        motorRight.setPower(PERCENT_MAX_POWER*speedStart);
        motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX);

        //Thread.sleep(2000);

    }

}
