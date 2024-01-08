package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous
public class BlueRight extends LinearOpMode {
    VisionPortal propVision;
    PropProcessor pipeline = new PropProcessor(telemetry);


    @Override
    public void runOpMode() {
        propVision = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam"), pipeline);

        Pose2d beginPose = new Pose2d(-36, 60, -Math.PI / 2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        waitForStart();
//        int propPosition = pipeline.GetPropPosition();
        int propPosition = 1;
        if(opModeIsActive()) {
            switch (propPosition) {
                case 1:
                    Actions.runBlocking(
                            drive.actionBuilder(beginPose)
                                    .splineTo(new Vector2d(-40, 31), -Math.PI / 2)
                                    .turnTo(Math.PI)
                                    .lineToX(36)
                                    .splineToConstantHeading(new Vector2d( 60, 60), Math.PI)
                                    .build());
                    break;

                case 2:
                    Actions.runBlocking(
                            drive.actionBuilder(beginPose)
                                    .splineTo(new Vector2d(-38, 34), -Math.PI / 2)
                                    .turnTo(Math.PI)
                                    .waitSeconds(1)
                                    .lineToX(36)
                                   // .lineToY(60)
                                    //.lineToX(48)
                                    .build());
                    break;

                case 3:
                    Actions.runBlocking(
                            drive.actionBuilder(beginPose)
                                    .splineTo(new Vector2d(-40, 31), -Math.PI / 2)
                                    .turnTo(Math.PI)
                                    .lineToX(-24)
                                    .waitSeconds(1)
                                    .lineToX(36)
                                    //.lineToY(60)
                                    //.lineToX(48)
                                    .build());
                    break;
            }
        }
    }
}