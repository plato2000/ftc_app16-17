package org.firstinspires.ftc.team9450;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.lasarobotics.vision.opmode.LinearVisionOpMode;

/**
 * Created by Winston on 11/23/16.
 */

@Autonomous(name = "ShootTest1", group = "ShootTest1")
public class shootTest1 extends LinearVisionOpMode{


    float PERCENT_POWER = 0.20f;

    final static float LEFT_FIX = 1.0f;

    final static float INTAKE_POWER = -1.0f;
    final static float OUTPUT_POWER = .8f;

    DcMotor motorFlywheel;
    DcMotor motorLifter;

    float shootPowLevel=0.30f;
    float shootPow=0;
    float intakePow=0;

    boolean yHeld=false;

    boolean aHeld=false;

    long currTime = -1;

    final static float PERCENT_MAX_POWER = 0.60f;
    float speedStart = 0.6f;

    final static int thresh=300;
    final static float turnFix=2;

    DcMotor motorRight;
    DcMotor motorLeft;


    final static int samples = 100;

    final static int threshLR = (int)(samples*.2);


    final static double Go_Left = 0.1;

    final static double Go_Right = 0.9;

    final static long slideTime = 750;

    Servo slider;

    public void runOpMode() throws InterruptedException {
        System.out.println("Into opmode");
        motorRight = hardwareMap.dcMotor.get("right");
        motorLeft = hardwareMap.dcMotor.get("left");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFlywheel = hardwareMap.dcMotor.get("flywheel");
        motorLifter = hardwareMap.dcMotor.get("lifter");
        waitForStart();
        System.out.println("Past start");
        long timer = System.currentTimeMillis();

        while (System.currentTimeMillis() < timer + 5000) {
            motorFlywheel.setPower(shootPowLevel);
        }

        motorLifter.setPower(INTAKE_POWER);
        timer = System.currentTimeMillis();

        while (System.currentTimeMillis() < timer + 3000) {
            System.out.println("Into Loop");
            Thread.sleep(1);


        }
        motorLifter.setPower(0);
        motorFlywheel.setPower(0);


    }


}