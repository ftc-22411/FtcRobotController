package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.opencv.core.Mat;

@Autonomous
public class BlueLeft extends LinearOpMode {
    VisionPortal propVision;
    PropProcessor pipeline = new PropProcessor(telemetry);



    @Override
    public void runOpMode() {
        propVision = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam"), pipeline);

        Pose2d beginPose = new Pose2d(15, 62, -Math.PI / 2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Servo hookArm = hardwareMap.get(Servo.class, "Hook Arm");

        waitForStart();
        hookArm.setPosition(.55);
        int propPosition = 1;// pipeline.GetPropPosition();
        if(opModeIsActive()) {
            switch (propPosition) {
                case 1:
                    Actions.runBlocking(
                            drive.actionBuilder(beginPose)
                                    .setTangent(0)
                                    .splineToLinearHeading(new Pose2d(8.0, 35.0, Math.PI), -Math.PI)

                                    // Place purple pixel
                                    .stopAndAdd(new SleepAction(.5))

                                    .strafeTo(new Vector2d(36.0, 31.0))

                                    // Place yellow pixel
                                    .stopAndAdd(new SleepAction(.5))

                                    .setTangent(Math.PI / 2)
                                    .splineToConstantHeading(new Vector2d(48.0, 60.0), 0.0)
                                    .build());
                    break;

                case 2:
                    Actions.runBlocking(
                            drive.actionBuilder(beginPose)
                                    .splineTo(new Vector2d(12, 34), -Math.PI / 2)
                                    .turnTo(Math.PI)
                                    .waitSeconds(1)
                                    .lineToX(36)
                                    .waitSeconds(.5)
                                    .lineToY(60)
                                    .lineToX(48)

                                    .build());
                    break;

                case 3:
                    Actions.runBlocking(
                            drive.actionBuilder(beginPose)
                                    .splineTo(new Vector2d(12, 31), -Math.PI / 2)
                                    .turnTo(Math.PI)
                                    .lineToX(28)
                                    .waitSeconds(1)
                                    .lineToX(36)
                                    .lineToY(60)
                                    .lineToX(48)
                                    .build());
                    break;
            }
        }
    }
}
