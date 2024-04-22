package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

@TeleOp
public class Turn90Degrees extends LinearOpMode {
    @Override

    public void runOpMode() {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        while(opModeIsActive()) {
            Actions.runBlocking(drive.actionBuilder(beginPose)
                    .turn(Math.PI/2)
                    .waitSeconds(.5)
                    .turn(Math.PI/2)
                    .waitSeconds(.5)
                    .turn(Math.PI/2)
                    .waitSeconds(.5)
                    .turn(Math.PI/2)
                    .waitSeconds(.5)
                    .build());
        }
    }
}
