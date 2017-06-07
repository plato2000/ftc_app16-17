package org.firstinspires.ftc.team9450;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Winston on 1/30/16.
 */
@TeleOp(name="FalconNew", group="FalconNew")
public class FalconTeleOpNew extends OpMode {

    /*
     * Note: the configuration of the servos is such that
     * as the zip servo approaches 0, the zip position moves up (away from the floor).
     * Also, as the dump servo approaches 0, the dump opens up (drops the game element).
     */
    // TETRIX VALUES.

    float PERCENT_POWER = 0.30f;

    final static float LEFT_FIX = 1.0f;

    final static float INTAKE_POWER = -1.0f;
    final static float OUTPUT_POWER = 1.0f;
    boolean swapIntake=false;

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorFlywheel;
    DcMotor motorLifter;

    float shootPowLevel=0.6f;
    float shootPow=0;
    float intakePow=0;

    boolean yHeld=false;

    boolean aHeld=false;

    final static int samples = 1;

    final static int threshLR = (int)(samples*.2);


    final static double Go_Left = 0.1;

    final static double Go_Right = 0.9;

    long currTime = -1;
    int swapped=1;
    boolean swapbutton=false;

    //Servo slider;

    /**
     * Constructor
     */
    public FalconTeleOpNew() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */

		/*
		 * For the demo Tetrix K9 bot we assume the following,
		 *   There are two motors "motor_1" and "motor_2"
		 *   "motor_1" is on the right side of the bot.
		 *   "motor_2" is on the left side of the bot.
		 *
		 * We also assume that there are two servos "servo_1" and "servo_6"
		 *    "servo_1" controls the zip joint of the manipulator.
		 *    "servo_6" controls the dump joint of the manipulator.
		 */
        motorRight = hardwareMap.dcMotor.get("right");
        motorLeft = hardwareMap.dcMotor.get("left");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        //slider = hardwareMap.servo.get("slider");
        motorFlywheel = hardwareMap.dcMotor.get("flywheel");
        motorLifter = hardwareMap.dcMotor.get("lifter");
        //waitForVisionStart();


        //try{
        //    Thread.sleep(5000L);
        //} catch (InterruptedException e) {

        //}

        //float speedStart = 0.6f;

        /**
         * Set the camera used for detection
         * PRIMARY = Front-facing, larger camera
         * SECONDARY = Screen-facing, "selfie" camera :D
         **/
        //this.setCamera(Cameras.PRIMARY);

        /**
         * Set the frame size
         * Larger = sometimes more accurate, but also much slower
         * After this method runs, it will set the "width" and "height" of the frame
         **/
        //this.setFrameSize(new Size(900, 900));

        /**
         * Enable extensions. Use what you need.
         * If you turn on the BEACON extension, it's best to turn on ROTATION too.
         */
        //enableExtension(Extensions.BEACON);         //Beacon detection
        //enableExtension(Extensions.ROTATION);       //Automatic screen rotation correction
        //enableExtension(Extensions.CAMERA_CONTROL); //Manual camera control

        /**
         * Set the beacon analysis method
         * Try them all and see what works!
         */
        //beacon.setAnalysisMethod(Beacon.AnalysisMethod.FAST);

        /**
         * Set color tolerances
         * 0 is default, -1 is minimum and 1 is maximum tolerance
         */
        //beacon.setColorToleranceRed(0);
        //beacon.setColorToleranceBlue(0);

        //motorRight = hardwareMap.dcMotor.get("right"); //swapped back
        //motorLeft = hardwareMap.dcMotor.get("left");


        //scolF = hardwareMap.colorSensor.get("colorF");
        //scolB = hardwareMap.colorSensor.get("colorB");
        //motorRight.setDirection(DcMotor.Direction.REVERSE); //maybe needs to be swapped
        //rotation.setIsUsingSecondaryCamera(false);
        //rotation.disableAutoRotate();
        //rotation.setActivityOrientationFixed(ScreenOrientation.LANDSCAPE);
        //cameraControl.setColorTemperature(CameraControlExtension.ColorTemperature.AUTO);
        //cameraControl.setAutoExposureCompensation();

        //slider.setPosition(0.5);


    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {



		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/dump via the a,b, x, y buttons
		 */

        // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
        // 1 is full down
        // direction: left_stick_x ranges from -1 to 1, where -1 is full left
        // and 1 is full right
        float throttle = gamepad1.left_stick_y;
        float direction = gamepad1.left_stick_x;
        throttle=throttle*swapped;
        direction= direction*swapped;

        /*if (!swapbutton && gamepad1.left_bumper){
            swapped*=-1;
            swapbutton=true;
        }else{
            swapbutton=false;
        }*/

        float right = throttle - direction;
        float left = throttle + direction;



        double slidePow=0.5;



        if(gamepad1.left_bumper){
            PERCENT_POWER=1.0f;
        }else{
            PERCENT_POWER=0.3f;
        }



        if(gamepad1.right_trigger>0.5f){
            intakePow=INTAKE_POWER;
            System.out.println("hi1");
        }else{
            intakePow=0;
            System.out.println("hi2");
        }

        if(gamepad1.left_trigger>0.5f){
            intakePow=-1*INTAKE_POWER;
            System.out.println("hi1");
        }else{
            intakePow=0;
            System.out.println("hi2");
        }


        if(gamepad1.right_trigger>0.5f){
            intakePow=INTAKE_POWER;
            System.out.println("hi1");
        }else{
            intakePow=0;
            System.out.println("hi2");
        }

        /*if(gamepad1.y && !yHeld && shootPowLevel>-.50f){
            shootPowLevel-=0.1f;
            yHeld=true;
        }else{
            yHeld=false;
        }

        if(gamepad1.a && !aHeld && shootPowLevel<0.0f){
            shootPowLevel+=0.1f;
            aHeld=true;
        }else{
            aHeld=false;
        }*/

        /*

        if(gamepad1.left_bumper){
            Beacon.BeaconAnalysis analyz = beacon.getAnalysis();
            int leftc = 0;
            int rightc = 0;
            int count = 0;
            if (analyz.isBeaconFound()) {
                if (analyz.isRightRed() && analyz.isLeftBlue()) {
                    leftc++;
                    count++;
                } else if (analyz.isLeftRed() && analyz.isRightBlue()) {
                    rightc++;
                    count++;
                }

                telemetry.addData("left: ", left);
                telemetry.addData("right: ", right);
                System.out.println("left data " + left);
                System.out.println("right data " + right);
                if (count == samples) {
                    currTime = System.currentTimeMillis();
                    if (leftc > rightc) {
                        slider.setPosition(Go_Left);
                        //Thread.sleep(slideTime);
                        //slider.setPosition(0.5);
                    } else {
                        slider.setPosition(Go_Right);
                        //Thread.sleep(slideTime);
                        //slider.setPosition(0.5);
                    }

                }
            }
        }

        if (currTime != -1 && currTime - System.currentTimeMillis() > 5000){
            slider.setPosition(0.5);
        }

        */

        if(gamepad1.b){
            shootPow=0;
        }

        if(gamepad1.x){
            shootPow=shootPowLevel;
        }

        if(gamepad1.dpad_left){
            slidePow=Go_Left;
        }else{
            slidePow=0;
        }

        if(gamepad1.dpad_right){
            slidePow=Go_Right;
        }else{}



        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);

        left = Range.clip(left, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float)scaleInput(right) * PERCENT_POWER;
        left =  (float)scaleInput(left)  * PERCENT_POWER * LEFT_FIX;

        motorRight.setPower(right);
        motorLeft.setPower(left);
        motorLifter.setPower(intakePow);
        motorFlywheel.setPower(shootPow);
        //slider.setPosition(slidePow);



		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));

    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}
