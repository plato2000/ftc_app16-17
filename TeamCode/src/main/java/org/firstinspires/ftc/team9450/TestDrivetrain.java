package org.firstinspires.ftc.team9450;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team9450.control.CheesyDriveHelper;
import org.firstinspires.ftc.team9450.control.ControlBoard;
import org.firstinspires.ftc.team9450.subsystems.Drivetrain;
import org.firstinspires.ftc.team9450.subsystems.Intake;
import org.firstinspires.ftc.team9450.subsystems.Shooter;
import org.firstinspires.ftc.team9450.subsystems.Slider;
import org.firstinspires.ftc.team9450.subsystems.SubsystemManager;
import org.firstinspires.ftc.team9450.util.Constants;
import org.firstinspires.ftc.team9450.util.DriveSignal;

/**
 * Created by plato2000 on 6/7/17.
 */

@TeleOp(name = "SemiGarbage", group = "FalconNew")
public class TestDrivetrain extends OpMode {

    private DcMotor left;
//    private DcMotor right;


    @Override
    public void init() {
        left = hardwareMap.dcMotor.get("left");
//        right = hardwareMap.dcMotor.get("t2");
    }

    @Override
    public void loop() {
        left.setPower(1);
        telemetry.addData("encoder", left.getCurrentPosition());
//        right.setPower(1);
    }
}
