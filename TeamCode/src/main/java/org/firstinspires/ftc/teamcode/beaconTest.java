package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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

/**
 * Created by Sreya Vangara on 11/26/2016.
 */
@TeleOp(name = "beaconTest", group = "AutoRed")
public class beaconTest extends LinearVisionOpMode {
    final static float PERCENT_MAX_POWER = 0.20f;

    final static int thresh=75;

    final static double OneWay = 0.0;

    final static double OtherWay = 1.0;

    Servo zipL;

    //DcMotor motorRight;
    //DcMotor motorLeft;

    //ensor scolF;
    //ColorSensor scolB;
    UltrasonicSensor dist;

    public beaconTest(){
        zipL = hardwareMap.servo.get("zipL");
    }

    public void runOpMode() throws InterruptedException {
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

        analyz.getLeftButton();

        int i=1;
        while (i<200){
            //Thread.sleep(100);
            analyz = beacon.getAnalysis();
            //telemetry.addData("hi", "Sreya");

            boolean b = analyz.isBeaconFound();
            //telemetry.addData("beac found", b);

            if (b) {

                System.out.println("hey its right here");
                System.out.println(analyz.getLeftButton());
                //System.out.println(analyz.getLeftButton().center());
                //System.out.println(analyz.getLeftButton().center().x);

                try {
                    double x = analyz.getLeftButton().center().x;
                    //telemetry.addData("leftButtonY", analyz.getLeftButton().center().y);
                    //telemetry.addData("rightButtonX", analyz.getRightButton().center().x);
                    //telemetry.addData("rightButtonY", analyz.getRightButton().center().y);

                    if (x < 250) {
                        zipL.setPosition(0.5);
                    } else {
                        zipL.setPosition(OneWay);
                    }
                } catch (NullPointerException e) {

                }

            }
        i++;
        }
    }
}

