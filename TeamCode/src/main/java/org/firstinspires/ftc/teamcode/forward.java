package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.lasarobotics.vision.opmode.LinearVisionOpMode;

/**
 * Created by Winston on 11/23/16.
 */

@Autonomous(name = "Forward", group = "Forward")
public class forward extends LinearVisionOpMode {

    final static float PERCENT_MAX_POWER = 0.80f;
    final static float LEFT_FIX = 1.00f;

    long timer;
    DcMotor motorRight;
    DcMotor motorLeft;

    public forward(){

    }

    public boolean sleeping(long a) throws InterruptedException {
        System.out.println("A Value:" + a);
        long t = System.currentTimeMillis();
        while (System.currentTimeMillis() - t < a) {
            System.out.println("LABEL TIMER: "+(System.currentTimeMillis() - timer));
            if (System.currentTimeMillis() - timer > 10000){
                return false;
            }
            Thread.sleep(1);
        }
        return true;
    }

    public void runOpMode() throws InterruptedException{

        //try {


            motorRight = hardwareMap.dcMotor.get("right");
            motorLeft = hardwareMap.dcMotor.get("left");
            motorLeft.setDirection(DcMotor.Direction.REVERSE);

            //motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //motorLeft.setMaxSpeed(800);
            //motorRight.setMaxSpeed(800);

            waitForStart();

            timer = System.currentTimeMillis();

            System.out.println("Ji");

            if(!(sleeping(50000L))){
                System.out.println("FALSEISH");
                return;
            }

            System.out.println("ended loop");

        /*

            float speedStart = 1;
            //motorRight.setTargetPosition(150);
            //motorLeft.setTargetPosition(150);

            motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            motorRight.setPower(PERCENT_MAX_POWER * speedStart);
            motorLeft.setPower(PERCENT_MAX_POWER * speedStart * LEFT_FIX);

            //Thread.sleep(2000);
            /*
            for(int i=0; i<20;i++){
                System.out.println("Right Encoder Ticks: "+motorRight.getCurrentPosition());
                System.out.println("Left Encoder Ticks: "+motorLeft.getCurrentPosition());
                System.out.println("Difference Encoder Ticks: "+(Math.abs(motorRight.getCurrentPosition())-Math.abs(motorLeft.getCurrentPosition())));
                Thread.sleep(100);
            }
            */


            /*

            while (motorRight.getCurrentPosition() > -5000) {

            }

            motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        /*} catch (InterruptedException e) {
            return;
        }*/

    }



}
