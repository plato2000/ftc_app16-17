package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.GyroSensor;
/**
 * Created by Winston on 11/12/16.
 */

@TeleOp(name="ColorTest", group="ColorTest")
public class ColorTestOpMode extends OpMode{
    //AdafruitIMU boschBNO055;
    //GyroSensor sensorGyro;
    //ColorSensor sensorRGB;
    ColorSensor sensorRGBADA;
    //DeviceInterfaceModule cdim;
    static final int LED_CHANNEL = 5;

    //The following arrays contain both the Euler angles reported by the IMU (indices = 0) AND the
// Tait-Bryan angles calculated from the 4 components of the quaternion vector (indices = 1)
    volatile double[] rollAngle = new double[2], pitchAngle = new double[2], yawAngle = new double[2];

    long systemTime;//Relevant values of System.nanoTime

    /************************************************** **********************************************
     * The following method was introduced in the 3 August 2015 FTC SDK beta release and it runs
     * before "start" runs.
     */
    @Override
    public void init() {
        systemTime = System.nanoTime();
        //try {
           // boschBNO055 = new AdafruitIMU(hardwareMap, "bno055", (byte)(AdafruitIMU.BNO055_ADDRESS_A * 2),
            //        (byte)AdafruitIMU.OPERATION_MODE_IMU);
        //} catch (RobotCoreException e){
        //    Log.i("FtcRobotController", "Exception: " + e.getMessage());
        //}
//ADDRESS_B is the "standard" I2C bus address for the Bosch BNO055 (IMU data sheet, p. 90).
//BUT DAVID PIERCE, MENTOR OF TEAM 8886, HAS EXAMINED THE SCHEMATIC FOR THE ADAFRUIT BOARD ON
//WHICH THE IMU CHIP IS MOUNTED. SINCE THE SCHEMATIC SHOWS THAT THE COM3 PIN IS PULLED LOW,
//ADDRESS_A IS THE IMU'S OPERATIVE I2C BUS ADDRESS
//IMU is an appropriate operational mode for FTC competitions. (See the IMU datasheet, Table
// 3-3, p.20 and Table 3-5, p.21.)
        //sensorGyro = hardwareMap.gyroSensor.get("gyro");
        //sensorRGB = hardwareMap.colorSensor.get("mr");
        sensorRGBADA = hardwareMap.colorSensor.get("colorADA");
        //cdim = hardwareMap.deviceInterfaceModule.get("dim");

        //cdim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);
    }

    /************************************************** **********************************************
     * Code to run when the op mode is first enabled goes here
     * @see OpMode#start()
     */
    @Override
    public void start() {
/*
* Use the hardwareMap to get the dc motors, servos and other sensors by name. Note
* that the names of the devices must match the names used when you
* configured your robot and created the configuration file. The hardware map
* for this OpMode is not initialized until the OpModeManager's "startActiveOpMode" method
* runs.
*/
        systemTime = System.nanoTime();
        //boschBNO055.startIMU();//Set up the IMU as needed for a continual stream of I2C reads.
        Log.i("FtcRobotController", "IMU Start method finished in: "
                + (-(systemTime - (systemTime = System.nanoTime()))) + " ns.");

        //sensorGyro.calibrate();
        //sensorRGB.enableLed(true);
        //cdim.setDigitalChannelState(LED_CHANNEL, true);
    }

    /************************************************** *********************************************
     * This method will be called repeatedly in a loop
     * @see OpMode#loop()
     * NOTE: BECAUSE THIS "loop" METHOD IS PART OF THE OVERALL OpMode/EventLoop/ReadWriteRunnable
     * MECHANISM, ALL THAT THIS METHOD WILL BE USED FOR, IN AUTONOMOUS MODE, IS TO:
     * 1. READ SENSORS AND ENCODERS AND STORE THEIR VALUES IN SHARED VARIABLES
     * 2. WRITE MOTOR POWER AND CONTROL VALUES STORED IN SHARED VARIABLES BY "WORKER" THREADS, AND
     * 3. SEND TELELMETRY DATA TO THE DRIVER STATION
     * THIS "loop" METHOD IS THE ONLY ONE THAT "TOUCHES" ANY SENSOR OR MOTOR HARDWARE.
     */
    @Override
    public void loop() {
//Log.i("FtcRobotController", "Loop method starting at: " +
// -(systemTime - (systemTime = System.nanoTime())) + " since last loop start.");

// write the values computed by the "worker" threads to the motors (if any)

//Read the encoder values that the "worker" threads will use in their computations
        //boschBNO055.getIMUGyroAngles(rollAngle, pitchAngle, yawAngle);
/*
* Send whatever telemetry data you want back to driver station.
*/
//telemetry.addData("Text", "*** Robot Data***");
        //telemetry.addData("Headings(yaw): ",
        //        String.format("Eul= %4.5f, Quat= %4.5f", yawAngle[0], yawAngle[1]));
        //telemetry.addData("z MR H:", String.format("%03d", sensorGyro.getHeading()));

        /*telemetry.addData("vClear", sensorRGB.alpha());
        telemetry.addData("wRed ", sensorRGB.red());
        telemetry.addData("xGreen", sensorRGB.green());
        telemetry.addData("yBlue ", sensorRGB.blue());*/

        telemetry.addData("mClear", sensorRGBADA.alpha());
        telemetry.addData("nRed ", sensorRGBADA.red());
        telemetry.addData("oGreen", sensorRGBADA.green());
        telemetry.addData("pBlue ", sensorRGBADA.blue());

    }

    /*
    * Code to run when the op mode is first disabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#sto p()
    */
    @Override
    public void stop() {
//When the FTC Driver Station's "Start with Timer" button commands autonomous mode to start,
//then stop after 30 seconds, stop the motors immediately!
//Following this method, the underlying FTC system will call a "stop" routine of its own
        systemTime = System.nanoTime();
        Log.i("FtcRobotController", "IMU Stop method finished in: "
                + (-(systemTime - (systemTime = System.nanoTime()))) + " ns.");
    }
    public ColorTestOpMode(){

    }

}
