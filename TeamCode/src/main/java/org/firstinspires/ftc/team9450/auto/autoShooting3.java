package org.firstinspires.ftc.team9450.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.lasarobotics.vision.opmode.LinearVisionOpMode;

/**
 * Created by Winston on 11/23/16.
 */

/**TL;DR-Shoot and move forwards
 * -Grace
 */

@Autonomous(name = "AutoShooting3", group = "AutoShooting3")
public class autoShooting3 extends LinearVisionOpMode{
    float PERCENT_POWER = 0.20f;
    final static float LEFT_FIX = 1.0f;//Fix drifting
    final static float INTAKE_POWER = -1.0f;
    final static float OUTPUT_POWER = .8f;
    DcMotor motorFlywheel;
    DcMotor motorLifter;
    float shootPowLevel=-.6f;
    float shootPow=0;//delete?
    float intakePow=0;//delete?
    boolean yHeld=false;//delete?
    boolean aHeld=false;//delete?
    long currTime = -1;
    final static float PERCENT_MAX_POWER = 0.60f;
    float speedStart = 0.6f;
    final static int thresh=300;
    final static float turnFix=2;
    DcMotor motorRight;
    DcMotor motorLeft;
    long timer;
    final static int samples = 100;
    final static int threshLR = (int)(samples*.2);
    final static double Go_Left = 0.1;
    final static double Go_Right = 0.9;
    final static long slideTime = 750;
    Servo slider;

    public boolean sleeping(long a) throws InterruptedException {
        long t = System.currentTimeMillis();
        while (System.currentTimeMillis() - t < a) {
            if (System.currentTimeMillis() - timer > 29800){
                return false;
            }
            Thread.sleep(1);
        }
        return true;
    }

    public void runOpMode() throws InterruptedException {
        System.out.println("Into opmode");
        motorRight = hardwareMap.dcMotor.get("right");
        motorLeft = hardwareMap.dcMotor.get("left");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);//Reverse so bot moves forwards
        motorFlywheel = hardwareMap.dcMotor.get("flywheel");
        motorLifter = hardwareMap.dcMotor.get("lifter");
        motorFlywheel.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();

        System.out.println("Past start");
        long timer = System.currentTimeMillis();//delete?
        motorFlywheel.setPower(0);
        motorLifter.setPower(0);
        if(!sleeping(10000)){
            return;
        };
        motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX);//go forwards
        motorRight.setPower(PERCENT_MAX_POWER * speedStart);//go forwards
        if(!sleeping(1000)){
            return;
        };
        motorLeft.setPower(0);
        motorRight.setPower(0);

        motorFlywheel.setPower(shootPowLevel);//get up to shooting speed
        if(!sleeping(5000)){
            return;
        }

        motorLifter.setPower(INTAKE_POWER);//pull ball up to be shot
        timer = System.currentTimeMillis();//delete?
        if(!sleeping(10000)){
            return;
        };

        motorLifter.setPower(0);//stop and reset
        motorFlywheel.setPower(0);


        motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX);//go forwards
        motorRight.setPower(PERCENT_MAX_POWER * speedStart);//go forwards
        if(!sleeping(2000)){
            return;
        };
        motorLeft.setPower(0);//stop
        motorRight.setPower(0);

    }


}