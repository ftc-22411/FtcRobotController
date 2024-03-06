package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ParallelAction;
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

        Claw claw = new Claw(hardwareMap);

        waitForStart();

        Actions.runBlocking(claw.moveWrist(.7));

        int propPosition = pipeline.GetPropPosition();
        if(opModeIsActive()) {
            switch (propPosition) {
                case 1:
                    Actions.runBlocking( new ParallelAction(claw.ApplyArmMotors(),
                            drive.actionBuilder(beginPose)
                                    .setTangent(Math.PI)
                                    .splineToLinearHeading(new Pose2d(-38, 32, Math.PI), 0)

                                    // Place Purple Pixel
                                    .stopAndAdd(claw.closeLeftClaw(false))

                                    .setTangent(Math.PI / 11)
                                    .splineToConstantHeading(new Vector2d(-38, 10.0), 0)
                                    .splineToConstantHeading(new Vector2d(47.0, 28.0), Math.PI / 5)

                                    // Place yellow pixel
                                    .stopAndAdd(claw.moveWrist(0))
                                    .stopAndAdd(claw.moveArm(2250))
                                    .stopAndAdd(new SleepAction(.5))
                                    .stopAndAdd(claw.closeRightClaw(false))
                                    .stopAndAdd(new SleepAction(.5))
                                    .strafeTo(new Vector2d(40.0, 31.0))

                                    .setTangent(Math.PI )
                                    .splineToConstantHeading(new Vector2d(52.0, 10.0), 0.0)
                                    .build()));
                    break;

                case 2:
                    Actions.runBlocking( new ParallelAction( claw.ApplyArmMotors(),
                            drive.actionBuilder(beginPose)
                                    .setTangent(-Math.PI * .6)
                                    .splineToLinearHeading(new Pose2d(-45, 24, 0), -Math.PI * .5)

                                    // Place Purple Pixel
                                    .stopAndAdd(claw.closeLeftClaw(false))


                                    .setReversed(true)

                                    .splineToConstantHeading(new Vector2d(-30.0, 10), 0)
                                    .setTangent(Math.PI / 11)
                                    .splineToConstantHeading(new Vector2d(20, 10), 0)
                                    .splineToSplineHeading(new Pose2d(47.0, 31.0, Math.PI), 0)

                                    // Place yellow pixel
                                    .stopAndAdd(claw.moveWrist(0))
                                    .stopAndAdd(claw.moveArm(3000))
                                    .stopAndAdd(new SleepAction(.5))
                                    .stopAndAdd(claw.closeRightClaw(false))
                                    .stopAndAdd(new SleepAction(.5))
                                    .strafeTo(new Vector2d(40.0, 31.0))
                                    .stopAndAdd(claw.moveArm(10))

                                    .setTangent(Math.PI )
                                    .splineToConstantHeading(new Vector2d(52.0, 10.0), 0.0)
                                    .build()));
                    break;

                case 3:
                    Actions.runBlocking( new ParallelAction( claw.ApplyArmMotors(),
                            drive.actionBuilder(beginPose)
                                    .setTangent(-Math.PI / 1)
                                    .splineToLinearHeading(new Pose2d(-30, 36, 0), -Math.PI / 5)

                                    // Place Purple Pixel
                                    .stopAndAdd(claw.closeLeftClaw(false))
                                    .splineToConstantHeading(new Vector2d(-40.0, 36.0), 0)

                                    .setTangent(Math.PI )
                                    .splineToConstantHeading(new Vector2d(-40.0, 10.0), 0)
                                    .setTangent(-Math.PI / 11)
                                    .splineToSplineHeading(new Pose2d(47.0, 31.0, Math.PI), Math.PI / 4)

                                    // Place yellow pixel
                                    .stopAndAdd(claw.moveWrist(0))
                                    .stopAndAdd(claw.moveArm(2250))
                                    .stopAndAdd(new SleepAction(.5))
                                    .stopAndAdd(claw.closeRightClaw(false))
                                    .stopAndAdd(new SleepAction(.5))
                                    .strafeTo(new Vector2d(40.0, 31.0))
                                    .setTangent(Math.PI / 1)
                                    .splineToConstantHeading(new Vector2d(52.0, 10.0), 0.0)
                                    .build()));
                    break;
            }
        }
    }
}
