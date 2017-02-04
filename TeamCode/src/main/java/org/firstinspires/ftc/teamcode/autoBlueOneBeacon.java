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

@Autonomous(name = "AutoBlueOneBeacon", group = "AutoBlueOneBeacon")
public class autoBlueOneBeacon extends LinearVisionOpMode {

    ColorSensor scolF;
    ColorSensor scolB;


    Servo slider;

    long timer;
    float PERCENT_POWER = 0.20f;


    final static float INTAKE_POWER = -1.0f;
    final static float OUTPUT_POWER = .8f;

    DcMotor motorFlywheel;
    DcMotor motorLifter;

    float shootPowLevel=-.6f;
    float shootPow=0;
    float intakePow=0;

    boolean yHeld=false;

    boolean aHeld=false;

    long currTime = -1;
    final static float PERCENT_MAX_POWER = 0.21f;

    final static float LEFT_FIX = 1.0f;
    float speedStart = 0.6f;

    final static int thresh=300;
    final static float turnFix=-0.5f;

    DcMotor motorRight;
    DcMotor motorLeft;


    final static int samples = 100;

    final static int threshLR = (int)(samples*.5);


    final static double Go_Left = 0.1;

    final static double Go_Right = 0.9;

    final static long slideTime = 750;

    public autoBlueOneBeacon(){

    }

    public boolean sleeping(long a) throws InterruptedException {
        long t = System.currentTimeMillis();
        while (System.currentTimeMillis() - t < a) {
            if (System.currentTimeMillis() - timer > 29800) {
                return false;
            }
            Thread.sleep(1);
        }
        return true;
    }

    public void runOpMode() {
        try {
            slider = hardwareMap.servo.get("slider");
            waitForVisionStart();

            float speedStart = 0.6f;

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
            enableExtension(Extensions.BEACON);         //Beacon detection
            enableExtension(Extensions.ROTATION);       //Automatic screen rotation correction
            enableExtension(Extensions.CAMERA_CONTROL); //Manual camera control

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

            motorRight = hardwareMap.dcMotor.get("right"); //swapped back
            motorLeft = hardwareMap.dcMotor.get("left");


            scolF = hardwareMap.colorSensor.get("colorF");
            scolB = hardwareMap.colorSensor.get("colorB");
            motorLeft.setDirection(DcMotor.Direction.REVERSE); //maybe needs to be swapped
            rotation.setIsUsingSecondaryCamera(false);
            rotation.disableAutoRotate();
            rotation.setActivityOrientationFixed(ScreenOrientation.LANDSCAPE);
            cameraControl.setColorTemperature(CameraControlExtension.ColorTemperature.AUTO);
            cameraControl.setAutoExposureCompensation();
            motorFlywheel = hardwareMap.dcMotor.get("flywheel");
            motorLifter = hardwareMap.dcMotor.get("lifter");
            motorFlywheel.setDirection(DcMotor.Direction.REVERSE);




            waitForStart();


            timer = System.currentTimeMillis();




            timer = System.currentTimeMillis();
            blueLineFollow();

           /* motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX*-1*2);
            motorRight.setPower(PERCENT_MAX_POWER * speedStart * -1*2);

            if(!sleeping(250)){
                return;
            };

            motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX*5);
            motorRight.setPower(PERCENT_MAX_POWER * speedStart * 5);

            if(!sleeping(1000)){
                return;
            };

            */

            motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX*-1*2);
            motorRight.setPower(PERCENT_MAX_POWER * speedStart*-1*2);
            if(!sleeping(800)){
                return;
            };
            motorLeft.setPower(0);
            motorRight.setPower(0);

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



            //For the second beacon

            /*motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            motorLeft.setPower(0);
            motorRight.setPower(0);
            Thread.sleep(10);
            motorLeft.setPower(-1*PERCENT_MAX_POWER * 0.3 * LEFT_FIX);
            motorRight.setPower(-1*PERCENT_MAX_POWER * 0.3);
            Thread.sleep(10);
            motorLeft.setPower(-1*PERCENT_MAX_POWER * speedStart * LEFT_FIX);
            motorRight.setPower(-1*PERCENT_MAX_POWER * speedStart);

            while (motorRight.getCurrentPosition() < 3400) {
            }*/
        } catch (InterruptedException ie) {
            return;
        }

    }

    public void blueLineFollow() {

        try {

            float speedStart = 0.6f;
            motorLeft.setPower(0);
            motorRight.setPower(0);
            if(!sleeping(10)){
                return;
            }
            //Thread.sleep(10);
            motorLeft.setPower(PERCENT_MAX_POWER * 0.3 * LEFT_FIX);
            motorRight.setPower(PERCENT_MAX_POWER * 0.3);
            if(!sleeping(10)){
                return;
            }
            //Thread.sleep(10);
            motorLeft.setPower(PERCENT_MAX_POWER * LEFT_FIX);
            motorRight.setPower(PERCENT_MAX_POWER);

            while (colSumF() < thresh) {
                if(!sleeping(1)){
                    return;
                }
            }

            motorRight.setPower(PERCENT_MAX_POWER * -1);

            while (colSumB() < thresh) {
                if(!sleeping(1)){
                    return;
                }
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
            while (choiceTime == 0 || currTime - choiceTime < 5500) {//for(int i=0;i<100000;i++){
                if (colSumF() < thresh) {
                    if (colSumB() < thresh) {
                        if (measure) {
                            measure2 = true;
                        }
                        if (!hitWhite) {
                            motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX);
                            motorRight.setPower(PERCENT_MAX_POWER * speedStart);
                        } else {
                            motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX );
                            motorRight.setPower(PERCENT_MAX_POWER * speedStart* turnFix);
                        }
                    } else {
                        motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX );
                        motorRight.setPower(PERCENT_MAX_POWER * speedStart* turnFix);
                        hitWhite = true;
                    }
                } else {
                    motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX * turnFix);
                    motorRight.setPower(PERCENT_MAX_POWER * speedStart);
                    hitWhite = true;
                    measure = true;
                }
                analyz = beacon.getAnalysis();
                if (analyz.isBeaconFound() && !hasMoved && currTime - startTime > 1500) {
                    if (analyz.isRightRed() && analyz.isLeftBlue()) {
                        left++;
                        count++;
                    } else if (analyz.isLeftRed() && analyz.isRightBlue()) {
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
                            if(!sleeping(slideTime)){
                                return;
                            }
                            //Thread.sleep(slideTime);
                            slider.setPosition(0.5);
                        } else {
                            slider.setPosition(Go_Right);
                            if(!sleeping(slideTime)){
                                return;
                            }
                            //Thread.sleep(slideTime);
                            slider.setPosition(0.5);
                        }
                        hasMoved = true;
                        System.out.println("print_hasmoved");
                        choiceTime = System.currentTimeMillis();

                    }
                }
                if(!sleeping(1)){
                    return;
                }
                //Thread.sleep(1);
                currTime = System.currentTimeMillis();
                System.out.println("print_" + currTime);
                System.out.println("print_" + (choiceTime == 0 || currTime - choiceTime < 2000));
            }
            motorLeft.setPower(0);
            //motorRight.setPower(PERCENT_MAX_POWER * 5);
            /*if(!sleeping(500)){
                return;
            }*/
            //Thread.sleep(500);

        } catch(InterruptedException e){
            return;
        }
    }

    public int colSumF() {
        int colTot = scolF.blue() + scolF.red() + scolF.green();
        //telemetry.addData("colF",colTot);
        return colTot;
    }

    public int colSumB() {
        int colTot = scolB.blue() + scolB.red() + scolB.green();
        //telemetry.addData("colB",colTot);
        return colTot;
    }

}