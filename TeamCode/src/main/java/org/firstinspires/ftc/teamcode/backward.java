package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Winston on 11/23/16.
 */

@Autonomous(name = "Backward", group = "Backward")
public class backward extends LinearOpMode{

    final static float PERCENT_MAX_POWER = 0.20f;
    final static float LEFT_FIX = 1.0f;
    DcMotor motorRight;
    DcMotor motorLeft;

    public backward(){

    }

    public void runOpMode() throws InterruptedException{


        motorRight = hardwareMap.dcMotor.get("right");
        motorLeft = hardwareMap.dcMotor.get("left");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        float speedStart=1;
        motorRight.setPower(-1*PERCENT_MAX_POWER*speedStart);
        motorLeft.setPower(-1*PERCENT_MAX_POWER * speedStart * LEFT_FIX);

        Thread.sleep(1500);

    }

}
