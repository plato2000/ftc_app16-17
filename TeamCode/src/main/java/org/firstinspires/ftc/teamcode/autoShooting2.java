package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.lasarobotics.vision.android.Cameras;
import org.lasarobotics.vision.ftc.resq.Beacon;
import org.lasarobotics.vision.opmode.LinearVisionOpMode;
import org.lasarobotics.vision.opmode.extensions.CameraControlExtension;
import org.lasarobotics.vision.util.ScreenOrientation;
import org.opencv.core.Size;

/**
 * Created by Winston on 11/23/16.
 */

@Autonomous(name = "AutoShooting2", group = "AutoShooting2")
public class autoShooting2 extends LinearVisionOpMode{


    float PERCENT_POWER = 0.20f;

    final static float LEFT_FIX = 1.0f;

    final static float INTAKE_POWER = -1.0f;
    final static float OUTPUT_POWER = .8f;

    DcMotor motorFlywheel;
    DcMotor motorLifter;

    float shootPowLevel=-.48f;
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
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFlywheel = hardwareMap.dcMotor.get("flywheel");
        motorLifter = hardwareMap.dcMotor.get("lifter");
        waitForStart();

        System.out.println("Past start");
        long timer = System.currentTimeMillis();

        motorFlywheel.setPower(shootPowLevel);
        if(!sleeping(5000)){
            return;
        }

        motorLifter.setPower(INTAKE_POWER);
        timer = System.currentTimeMillis();

        if(!sleeping(10000)){
            return;
        };

        motorLifter.setPower(0);
        motorFlywheel.setPower(0);


        motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX);
        motorRight.setPower(PERCENT_MAX_POWER * speedStart);
        if(!sleeping(3000)){
            return;
        };
        motorLeft.setPower(0);
        motorRight.setPower(0);

    }


}