package org.firstinspires.ftc.team9450;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.team9450.control.CheesyDriveHelper;
import org.firstinspires.ftc.team9450.control.ControlBoard;
import org.firstinspires.ftc.team9450.subsystems.Drivetrain;
import org.firstinspires.ftc.team9450.subsystems.Intake;
import org.firstinspires.ftc.team9450.subsystems.Slider;
import org.firstinspires.ftc.team9450.subsystems.Shooter;
import org.firstinspires.ftc.team9450.subsystems.SubsystemManager;
import org.firstinspires.ftc.team9450.util.DriveSignal;
import org.firstinspires.ftc.team9450.util.LatchedBoolean;

/**
 * Created by plato2000 on 6/7/17.
 */
public class Teleop extends OpMode {

    private Drivetrain drivetrain;
    private Intake intake;
    private Slider slider;
    private Shooter shooter;
    private ControlBoard controlBoard;
    private CheesyDriveHelper driveHelper;

    private SubsystemManager subsystemManager;

    private LatchedBoolean shooterOn;

    @Override
    public void init() {
        subsystemManager = new SubsystemManager();
        drivetrain = new Drivetrain(hardwareMap.dcMotor.get("left"), hardwareMap.dcMotor.get("right"));
        intake = new Intake(hardwareMap.dcMotor.get("lifter"));

        controlBoard = new ControlBoard(gamepad1);
        driveHelper = new CheesyDriveHelper();

        subsystemManager.add(drivetrain);
        subsystemManager.add(intake);
    }

    @Override
    public void loop() {
        intake.setState(controlBoard.intakeControl());

        DriveSignal driveSignal = driveHelper.cheesyDrive(controlBoard.throttle(), controlBoard.turn(), false);
        if(controlBoard.reduceSpeed()) {
            drivetrain.setMaxPower(0.3f);
        } else {
            drivetrain.setMaxPower(0.7f);
        }
        drivetrain.setOpenLoop(driveSignal);

        if(controlBoard.toggleShooterOn()) {
            shooter.setState(Shooter.ShooterControlState.ON);
        } else if(controlBoard.toggleShooterOff()) {
            shooter.setState(Shooter.ShooterControlState.OFF);
        }

        if(controlBoard.moveSliderLeft()) {
            slider.setState(Slider.SliderControlState.GO_LEFT);
        } else if(controlBoard.moveSliderRight()) {
            slider.setState(Slider.SliderControlState.GO_RIGHT);
        }

        subsystemManager.loopSystems();
    }
}
