package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.util.Range;
@TeleOp(name="ServoZipTest", group="ServoZipTest")
public class ServoTest extends OpMode {
    
    final static double ZIP_IN_L = 0.0;
    
    final static double ZIP_OUT_L = 1.0;
        // position of the zip servo.
    double zipPosL;
    
    Servo zipL;
    

    public ServoTest() {}

    

    public void init() {
        zipL = hardwareMap.servo.get("slider");         // assign the starting position of the wrist and dump
        zipPosL = ZIP_IN_L;
    }

    @Override

    public void loop() {
        if (gamepad2.dpad_down) {
            zipPosL = ZIP_IN_L;
        } else if (gamepad2.dpad_left) {
            zipPosL = ZIP_OUT_L;
        } else if (gamepad2.dpad_right) {
            zipPosL = ZIP_IN_L;
        }
        zipL.setPosition(zipPosL);
        telemetry.addData("slider", "slider:  " + String.format("%.2f", zipPosL));
    }
}