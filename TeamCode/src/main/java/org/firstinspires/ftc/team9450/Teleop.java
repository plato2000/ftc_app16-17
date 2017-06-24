package org.firstinspires.ftc.team9450;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9450.control.CheesyDriveHelper;
import org.firstinspires.ftc.team9450.control.ControlBoard;
import org.firstinspires.ftc.team9450.subsystems.Drivetrain;
import org.firstinspires.ftc.team9450.subsystems.Intake;
import org.firstinspires.ftc.team9450.subsystems.Slider;
import org.firstinspires.ftc.team9450.subsystems.Shooter;
import org.firstinspires.ftc.team9450.subsystems.SubsystemManager;
import org.firstinspires.ftc.team9450.util.Constants;
import org.firstinspires.ftc.team9450.util.DriveSignal;
import org.firstinspires.ftc.team9450.util.LatchedBoolean;

/**
 * Created by plato2000 on 6/7/17.
 */

@TeleOp(name = "NotGarbage", group = "FalconNew")
public class Teleop extends OpMode {

    private Drivetrain drivetrain;
    private Intake intake;
    private Slider slider;
    private Shooter shooter;
    private ControlBoard controlBoard;
    private CheesyDriveHelper driveHelper;

    private SubsystemManager subsystemManager;


    @Override
    public void init() {
        subsystemManager = new SubsystemManager();
        drivetrain = new Drivetrain(hardwareMap.dcMotor.get("left"), hardwareMap.dcMotor.get("right"));
        intake = new Intake(hardwareMap.dcMotor.get("lifter"));
        shooter = new Shooter(hardwareMap.dcMotor.get("flywheel"));
        slider = new Slider(hardwareMap.servo.get("slider"));

        controlBoard = new ControlBoard(gamepad1);
        driveHelper = new CheesyDriveHelper();

        subsystemManager.add(drivetrain);
        subsystemManager.add(intake);
        subsystemManager.add(shooter);
        subsystemManager.add(slider);
    }

    @Override
    public void loop() {
        intake.setState(controlBoard.intakeControl());

        float throttle = controlBoard.throttle();
        float turn = controlBoard.turn();
        boolean quickTurn = false;

        if(Math.abs(throttle) < CheesyDriveHelper.kThrottleDeadband) {
            quickTurn = true;
            turn *= 0.7f;
        }

        DriveSignal driveSignal = driveHelper.cheesyDrive(throttle, turn, quickTurn);
        if(controlBoard.reduceSpeed()) {
            drivetrain.setMaxPower(Constants.Drivetrain.LOW_POWER);
        } else {
            drivetrain.setMaxPower(Constants.Drivetrain.HIGH_POWER);
        }
        drivetrain.setOpenLoop(driveSignal);
        telemetry.addData("drive signal", driveSignal);
        telemetry.addData("max power", drivetrain.getMaxPower());

        if(controlBoard.toggleShooterOn()) {
            shooter.setState(Shooter.ShooterControlState.ON);
        } else if(controlBoard.toggleShooterOff()) {
            shooter.setState(Shooter.ShooterControlState.OFF);
        }

        if(controlBoard.moveSliderLeft()) {
            slider.setState(Slider.SliderControlState.GO_LEFT);
        } else if(controlBoard.moveSliderRight()) {
            slider.setState(Slider.SliderControlState.GO_RIGHT);
        } else if(controlBoard.moveSliderCenter()) {
            slider.setState(Slider.SliderControlState.GO_CENTER);
        }

        subsystemManager.loopSystems();
    }
}
