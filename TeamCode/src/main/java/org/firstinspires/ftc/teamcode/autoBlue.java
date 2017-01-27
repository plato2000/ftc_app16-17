package org.firstinspires.ftc.teamcode;

import android.content.SyncStatusObserver;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.lasarobotics.vision.android.Cameras;
import org.lasarobotics.vision.ftc.resq.Beacon;
import org.lasarobotics.vision.opmode.LinearVisionOpMode;
import org.lasarobotics.vision.opmode.VisionOpMode;
import org.lasarobotics.vision.opmode.extensions.CameraControlExtension;
import org.lasarobotics.vision.util.ScreenOrientation;
import org.lasarobotics.vision.ftc.resq.Beacon;
import org.opencv.core.Size;

import java.util.Timer;

/**
 * Created by Winston on 11/23/16.
 */

@Autonomous(name = "AutoBlue", group = "AutoBlue")
public class autoBlue extends LinearVisionOpMode{

    final static float PERCENT_MAX_POWER = 0.20f;

    final static float LEFT_FIX = 1.0f;

    final static int thresh=300;

    DcMotor motorRight;
    DcMotor motorLeft;

    ColorSensor scolF;
    ColorSensor scolB;

    final static int samples = 100;

    final static int threshLR = (int)(samples*.2);


    final static double Go_Left = 0.1;

    final static double Go_Right = 0.9;

    final static long slideTime = 750;

    Servo slider;

    public autoBlue(){

    }

    public void runOpMode() throws InterruptedException{
        slider = hardwareMap.servo.get("slider");
        waitForVisionStart();

        float speedStart=0.6f;

        /**
         * Set the camera used for detection
         * PRIMARY = Front-facing, larger camera
         * SECONDARY = Screen-facing, "selfie" camera :D
         **/
        this.setCamera(Cameras.PRIMARY);

        /**
         * Set the frame size
         * Larger = sometimes more accurate, but also much slower
         * After this method runs, it will set the "width" and "height" of the frame
         **/
        this.setFrameSize(new Size(900, 900));

        /**
         * Enable extensions. Use what you need.
         * If you turn on the BEACON extension, it's best to turn on ROTATION too.
         */
        enableExtension(VisionOpMode.Extensions.BEACON);         //Beacon detection
        enableExtension(VisionOpMode.Extensions.ROTATION);       //Automatic screen rotation correction
        enableExtension(VisionOpMode.Extensions.CAMERA_CONTROL); //Manual camera control

        /**
         * Set the beacon analysis method
         * Try them all and see what works!
         */
        beacon.setAnalysisMethod(Beacon.AnalysisMethod.FAST);

        /**
         * Set color tolerances
         * 0 is default, -1 is minimum and 1 is maximum tolerance
         */
        beacon.setColorToleranceRed(0);
        beacon.setColorToleranceBlue(0);

        motorRight = hardwareMap.dcMotor.get("right");
        motorLeft = hardwareMap.dcMotor.get("left");
        scolF = hardwareMap.colorSensor.get("colorF");
        scolB = hardwareMap.colorSensor.get("colorB");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        rotation.setIsUsingSecondaryCamera(false);
        rotation.disableAutoRotate();
        rotation.setActivityOrientationFixed(ScreenOrientation.LANDSCAPE);
        cameraControl.setColorTemperature(CameraControlExtension.ColorTemperature.AUTO);
        cameraControl.setAutoExposureCompensation();

        waitForStart();
        blueLineFollow();

        //For the second beacon
        motorLeft.setPower(-1 * PERCENT_MAX_POWER * speedStart * LEFT_FIX);
        motorRight.setPower(-1 * PERCENT_MAX_POWER * speedStart);
        Thread.sleep(3800);
        motorRight.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX);
        Thread.sleep(800); //time to go 90 degrees
        //Copy over all the code above
        motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX);
        motorRight.setPower(PERCENT_MAX_POWER * speedStart);
        Thread.sleep(500);


        blueLineFollow();

    }

    public void blueLineFollow() throws InterruptedException {
        try{
            float speedStart = 0.6f;
            motorLeft.setPower(0);
            motorRight.setPower(0);
            Thread.sleep(10);
            motorLeft.setPower(PERCENT_MAX_POWER * 0.3 * LEFT_FIX);
            motorRight.setPower(PERCENT_MAX_POWER * 0.3);
            Thread.sleep(10);
            motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX);
            motorRight.setPower(PERCENT_MAX_POWER * speedStart);

            while (colSumF() < thresh) {
            }

            motorRight.setPower(0);

            while (colSumB() < thresh) {
            }

            Beacon.BeaconAnalysis analyz = beacon.getAnalysis();

            int left = 0;
            int right = 0;
            int count = 0;
            boolean hasMoved = false;
            boolean hitWhite = true;
            boolean measure = false;
            boolean measure2 = false;
            long choiceTime = 0;
            long currTime = 0;
            long startTime = System.currentTimeMillis();
            while (choiceTime == 0 || currTime - choiceTime < 5000) {//for(int i=0;i<100000;i++){
                if (colSumF() < thresh) {
                    if (colSumB() < thresh) {
                        if (measure) {
                            measure2 = true;
                        }
                        if (!hitWhite) {
                            motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX);
                            motorRight.setPower(PERCENT_MAX_POWER * speedStart);
                        } else {
                            motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX);
                            motorRight.setPower(0);
                        }
                    } else {
                        motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX);
                        motorRight.setPower(0);
                        hitWhite = true;
                    }
                } else {
                    motorLeft.setPower(0);
                    motorRight.setPower(PERCENT_MAX_POWER * speedStart);
                    hitWhite = true;
                    measure = true;
                }
                analyz = beacon.getAnalysis();
                if (analyz.isBeaconFound() && !hasMoved && currTime - startTime > 2000) {
                    if (analyz.isLeftBlue() && analyz.isRightRed()) {
                        left++;
                        count++;
                    } else if (analyz.isRightBlue() && analyz.isLeftRed()) {
                        right++;
                        count++;
                    }

                    telemetry.addData("left: ", left);
                    telemetry.addData("right: ", right);
                    System.out.println("left data " + left);
                    System.out.println("right data " + right);
                    if (count == samples) {
                        motorLeft.setPower(0);
                        motorRight.setPower(0);
                        if (right - left < threshLR) {
                            slider.setPosition(Go_Left);
                            Thread.sleep(slideTime);
                            slider.setPosition(0.5);
                        } else {
                            slider.setPosition(Go_Right);
                            Thread.sleep(slideTime);
                            slider.setPosition(0.5);
                        }
                        hasMoved = true;
                        choiceTime = System.currentTimeMillis();

                    }
                }
                Thread.sleep(1);
                currTime = System.currentTimeMillis();
            }
            motorLeft.setPower(0);
            motorRight.setPower(0);
            Thread.sleep(1000);
            if (right - left > threshLR) {
                slider.setPosition(Go_Left);
                Thread.sleep(slideTime);
                slider.setPosition(0.5);
            } else {
                slider.setPosition(Go_Right);
                Thread.sleep(slideTime);
                slider.setPosition(0.5);
            }
        }catch(InterruptedException ie){

        }
    }

    public int colSumF(){
        int colTot=scolF.blue() + scolF.red() + scolF.green();
        //telemetry.addData("colF",colTot);
        return colTot;
    }

    public int colSumB(){
        int colTot=scolB.blue() + scolB.red() + scolB.green();
        //telemetry.addData("colB",colTot);
        return colTot;
    }


}
