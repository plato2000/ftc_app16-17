package org.firstinspires.ftc.team9450.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.lasarobotics.vision.android.Cameras;
import org.lasarobotics.vision.ftc.resq.Beacon;
import org.lasarobotics.vision.opmode.LinearVisionOpMode;
import org.lasarobotics.vision.opmode.extensions.CameraControlExtension;
import org.lasarobotics.vision.util.ScreenOrientation;
import org.opencv.core.Size;

/**
 * Created by Sreya Vangara on 11/26/2016.
 */
@TeleOp(name = "hardCodeTest", group = "hardCodeTest")
public class hardCodeTest extends LinearVisionOpMode {
    final static float PERCENT_MAX_POWER = 0.20f;

    final static int MIN_POS = 250;
    final static int MAX_POS = 350;


    final static double Go_Left = 0.1;

    final static double Go_Right = 0.9;

    final static long slideTime = 750;

    final static int samples = 100;

    final static int thresh = (int)(samples*0.8);

    Servo slider;

    //DcMotor motorRight;
    //DcMotor motorLeft;

    //ensor scolF;
    //ColorSensor scolB;
    //UltrasonicSensor dist;

    public hardCodeTest(){

    }

    public void runOpMode() throws InterruptedException {
        slider = hardwareMap.servo.get("slider");
        waitForVisionStart();

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

        //motorRight = hardwareMap.dcMotor.get("right");
        //motorLeft = hardwareMap.dcMotor.get("left");
        //scolF = hardwareMap.colorSensor.get("colorSenseF");
        //scolB = hardwareMap.colorSensor.get("colorSenseB");
        //dist = hardwareMap.ultrasonicSensor.get("ultrasonicSensor");
        //motorRight.setDirection(DcMotor.Direction.REVERSE);
        rotation.setIsUsingSecondaryCamera(false);
        rotation.disableAutoRotate();
        rotation.setActivityOrientationFixed(ScreenOrientation.LANDSCAPE);
        cameraControl.setColorTemperature(CameraControlExtension.ColorTemperature.AUTO);
        cameraControl.setAutoExposureCompensation();

        waitForStart();

        Beacon.BeaconAnalysis analyz = beacon.getAnalysis();


       /* boolean b = analyz.isBeaconFound();
        while (!b){
            //Thread.sleep(100);
            analyz = beacon.getAnalysis();

            b = analyz.isBeaconFound();
        }*/

        analyz = beacon.getAnalysis();

        int left=0;
        int right=0;
        while(true){
            telemetry.addData("beaconFound",analyz.isBeaconFound());
            left=0;
            right=0;
            for(int i=0; i<samples;i++){
                analyz = beacon.getAnalysis();
                if(analyz.isLeftRed()){
                    left++;
                }else{
                    right++;
                }
                Thread.sleep(5);
            }

            telemetry.addData("left: ",left);
            telemetry.addData("right: ", right);
            if(right-left < thresh){
                telemetry.addData("left","left");
            }else{
                telemetry.addData("right","right");
            }
            Thread.sleep(1000);
        }

        /*if(analyz.isLeftRed()){
            slider.setPosition(Go_Left);
            Thread.sleep(slideTime);
            slider.setPosition(0.5);
        }else{
            slider.setPosition(Go_Right);
            Thread.sleep(slideTime);
            slider.setPosition(0.5);
        }*/
    }
}

