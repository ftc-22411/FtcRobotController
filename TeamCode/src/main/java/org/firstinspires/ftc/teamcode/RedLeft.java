package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous
public class RedLeft extends LinearOpMode {
    VisionPortal propVision;
    PropProcessor pipeline = new PropProcessor(telemetry);


    @Override
    public void runOpMode() {
        propVision = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam"), pipeline);

        Pose2d beginPose = new Pose2d(12, -60, Math.PI / 2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        waitForStart();
        int propPosition = pipeline.GetPropPosition();
        if(opModeIsActive()) {
            switch (propPosition) {
                case 1:
                    Actions.runBlocking(
                            drive.actionBuilder(beginPose)
                                    .splineTo(new Vector2d(12, -31), Math.PI / 2)
                                    .turnTo(Math.PI)
                                    .lineToX(36)
                                    .splineTo(new Vector2d(30, -60), Math.PI / 2)

                                    .build());
                    break;

                case 2:
                    Actions.runBlocking(
                            drive.actionBuilder(beginPose)
                                    .splineTo(new Vector2d(12, -34), Math.PI / 2)
                                    .turnTo(Math.PI)
                                    .waitSeconds(1)
                                    .lineToX(36)
                                    .splineTo(new Vector2d(48, -60), Math.PI)

                                    .build());
                    break;

                case 3:
                    Actions.runBlocking(
                            drive.actionBuilder(beginPose)
                                    .splineTo(new Vector2d(12, -31), Math.PI / 2)
                                    .turnTo(Math.PI)
                                    .lineToX(28)
                                    .waitSeconds(1)
                                    .lineToX(36)
                                    .splineTo(new Vector2d(30, -60), Math.PI / 2)

                                    .build());
            }
        }
    }
}
