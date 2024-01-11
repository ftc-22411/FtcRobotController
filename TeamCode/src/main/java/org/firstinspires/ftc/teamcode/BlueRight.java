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

@Autonomous
public class BlueRight extends LinearOpMode {
    VisionPortal propVision;
    PropProcessor pipeline = new PropProcessor(telemetry);


    @Override
    public void runOpMode() {
        propVision = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam"), pipeline);

        Pose2d beginPose = new Pose2d(-32, 62, -Math.PI / 2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Servo hookArm = hardwareMap.get(Servo.class, "Hook Arm");

        waitForStart();

        hookArm.setPosition(.55);

        int propPosition = pipeline.GetPropPosition();
        if(opModeIsActive()) {
            switch (propPosition) {
                case 1:
                    Actions.runBlocking(
                            drive.actionBuilder(beginPose)
                                    .setTangent(Math.PI)
                                    .splineToLinearHeading(new Pose2d(-38, 32, Math.PI), 0)

                                    // Place Purple Pixel
                                    .stopAndAdd(new SleepAction(.5))

                                    .setTangent(Math.PI / 8)
                                    .splineToConstantHeading(new Vector2d(-10, 36.0), 0)
                                    .splineToConstantHeading(new Vector2d(36.0, 31.0), 0)

                                    // Place yellow pixel
                                    .stopAndAdd(new SleepAction(.5))

                                    .setTangent(Math.PI / 2)
                                    .splineToConstantHeading(new Vector2d(52.0, 60.0), 0.0)
                                    .build());
                    break;

                case 2:
                    Actions.runBlocking(
                            drive.actionBuilder(beginPose)
                                    .setTangent(-Math.PI * .6)
                                    .splineToLinearHeading(new Pose2d(-38, 32, -Math.PI / 2), -Math.PI * .5)

                                    // Place Purple Pixel
                                    .stopAndAdd(new SleepAction(.5))

                                    .setReversed(true)
                                    .splineToConstantHeading(new Vector2d(-30.0, 36), 0)
                                    .splineToConstantHeading(new Vector2d(0, 36), 0)
                                    .splineToSplineHeading(new Pose2d(36.0, 31.0, Math.PI), 0)

                                    // Place yellow pixel
                                    .stopAndAdd(new SleepAction(.5))

                                    .setTangent(Math.PI / 2)
                                    .splineToConstantHeading(new Vector2d(52.0, 60.0), 0.0)
                                    .build());
                    break;

                case 3:
                    Actions.runBlocking(
                            drive.actionBuilder(beginPose)
                                    .setTangent(Math.PI)
                                    .splineToSplineHeading(new Pose2d(-30, 36, Math.PI), 0)
                                    .strafeTo(new Vector2d(-16, 36))

                                    // Place Purple Pixel
                                    .stopAndAdd(new SleepAction(.5))
                                    .setTangent(0)
                                    .splineToConstantHeading(new Vector2d(36.0, 31.0), 0)

                                    // Place yellow pixel
                                    .stopAndAdd(new SleepAction(.5))

                                    .setTangent(Math.PI / 2)
                                    .splineToConstantHeading(new Vector2d(52.0, 60.0), 0.0)
                                    .build());
                    break;
            }
        }
    }
}
