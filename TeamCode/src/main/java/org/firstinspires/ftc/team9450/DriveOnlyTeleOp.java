package org.firstinspires.ftc.team9450;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Winston on 1/30/16.
 */
@TeleOp(name="DriveOnly", group="DriveOnly")
public class DriveOnlyTeleOp extends OpMode {

    final static float PERCENT_POWER=0.20f;

    /*
     * Note: the configuration of the servos is such that
     * as the zip servo approaches 0, the zip position moves up (away from the floor).
     * Also, as the dump servo approaches 0, the dump opens up (drops the game element).
     */
    // TETRIX VALUES.
    final static double ZIP_IN_R = 0.40;
    final static double ZIP_OUT_R = 0.90;
    final static double ZIP_IN_L = 0.15;
    final static double ZIP_OUT_L = 0.60;

    final static double COLLECT_MIN_RANGE = 0.00;
    final static double COLLECT_MAX_RANGE = 0.75;

    final static double CLIMBER_MIN_RANGE = 0.50;
    final static double CLIMBER_MAX_RANGE = 0.50;

    final static double DUMP_L_LEFT = 0.00;
    final static double DUMP_L_CLOSE = 0.50;
    final static double DUMP_L_RIGHT = 1.00;

    final static double DUMP_R_LEFT = 0.00;
    final static double DUMP_R_CLOSE = 0.50;
    final static double DUMP_R_RIGHT = 1.00;

    final static double DUMP_FRONT_CLOSED = 0.30;
    final static double DUMP_FRONT_MID = 0.25;
    final static double DUMP_FRONT_OPEN = 0.5;

    // position of the zip servo.
    double zipPosL;
    double zipPosR;
    double climberPos;

    // position of the dump servos
    double dumpLeftPos;
    double dumpRightPos;
    double dumpFrontPos;

    double collectPower=0.0;

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor ballCollect;
    DcMotor extension;
    Servo dumpLeft;
    Servo dumpRight;
    Servo dumpFront;
    Servo zipR;
    Servo zipL;
    Servo climberArm;

    /**
     * Constructor
     */
    public DriveOnlyTeleOp() {

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
        float throttle = -gamepad1.left_stick_y;
        float direction = gamepad1.left_stick_x;

        float right = throttle - direction;
        float left = throttle + direction;

        if(gamepad1.left_trigger>0.5f){
            right = -gamepad1.right_stick_y;
            left = -gamepad1.left_stick_y;
        }


        if(gamepad1.left_bumper){
            right *=0.2;
            left *=0.2;
        }

        // clip the right/left values so that the values never exceed +/- 1
        right = PERCENT_POWER*Range.clip(right, -1, 1);

        left = PERCENT_POWER*Range.clip(left, -1, 1);



        motorRight.setPower(right);
        motorLeft.setPower(left);



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
