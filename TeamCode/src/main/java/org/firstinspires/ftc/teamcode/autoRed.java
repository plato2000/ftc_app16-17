package org.firstinspires.ftc.teamcode;

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

/**
 * Created by Winston on 11/23/16.
 */

@Autonomous(name = "AutoRed", group = "AutoRed")
public class autoRed extends LinearVisionOpMode{

    final static float PERCENT_MAX_POWER = 0.20f;

    final static float LEFT_FIX = 0.90f;

    final static int thresh=300;

    DcMotor motorRight;
    DcMotor motorLeft;

    ColorSensor scolF;
    ColorSensor scolB;
    //UltrasonicSensor dist;

    public autoRed(){

    }

    public void runOpMode() throws InterruptedException{
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

        float speedStart=1;
        motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX);
        motorRight.setPower(PERCENT_MAX_POWER * speedStart);

        while(colSumF()>thresh){
        }

        motorLeft.setPower(0);

        while(colSumB()>thresh){
        }

        Beacon.BeaconAnalysis analyz = beacon.getAnalysis();

        boolean t = true;


        while(t){
            if(colSumF()>thresh){
                if(colSumB()>thresh){
                    motorRight.setPower(0);
                }else{
                    motorLeft.setPower(0);
                }
            }else{
                motorLeft.setPower(PERCENT_MAX_POWER*speedStart*LEFT_FIX);
                motorRight.setPower(PERCENT_MAX_POWER * speedStart);
            }
            //analyz = beacon.getAnalysis();
            if (analyz.isBeaconFound() ){
                if (analyz.isRightRed()) {

                } else {

                }
            }
        }
        //motorLeft.setPower(0);
        //motorRight.setPower(0);


    }

    public int colSumF(){
        int colTot=scolF.blue() + scolF.red() + scolF.green();
        telemetry.addData("colF",colTot);
        return colTot;
    }

    public int colSumB(){
        int colTot=scolB.blue() + scolB.red() + scolB.green();
        telemetry.addData("colB",colTot);
        return colTot;
    }

}
