package org.firstinspires.ftc.team9450;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.team9450.subsystems.Drivetrain;
import org.firstinspires.ftc.team9450.subsystems.Intake;
import org.firstinspires.ftc.team9450.subsystems.Slider;
import org.firstinspires.ftc.team9450.subsystems.Shooter;
import org.firstinspires.ftc.team9450.subsystems.SubsystemManager;

/**
 * Created by plato2000 on 6/7/17.
 */
public class Teleop extends OpMode {

    private Drivetrain drivetrain;
    private Intake intake;
    private Slider slider;
    private Shooter shooter;

    private SubsystemManager subsystemManager;

    @Override
    public void init() {
        subsystemManager = new SubsystemManager();
        drivetrain = new Drivetrain(hardwareMap.dcMotor.get("left"), hardwareMap.dcMotor.get("right"));
        intake = new Intake(hardwareMap.dcMotor.get("lifter"));

        subsystemManager.add(drivetrain);
        subsystemManager.add(intake);
    }

    @Override
    public void loop() {
        subsystemManager.loopSystems();
    }
}
