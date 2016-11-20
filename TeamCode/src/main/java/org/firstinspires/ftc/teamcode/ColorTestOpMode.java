package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Winston on 11/12/16.
 */

@TeleOp(name="ColorTest", group="ColorTest")
public class ColorTestOpMode extends OpMode{
    ColorSensor sensorColor;
    static final int LED_CHANNEL = 5;


    @Override
    public void init() {

        sensorColor = hardwareMap.colorSensor.get("colorADA");
    }


    @Override
    public void start() {

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

        int b1 = sensorColor.blue();
        try {
            Thread.sleep(18);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int b2 = sensorColor.blue();

        telemetry.addData("blue1",b1 );
        telemetry.addData("blue2", b2);
        telemetry.addData("equal?", b1==b2);

    }

    /*
    * Code to run when the op mode is first disabled goes here
    * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#sto p()
    */
    @Override
    public void stop() {

    }
    public ColorTestOpMode(){

    }

}
