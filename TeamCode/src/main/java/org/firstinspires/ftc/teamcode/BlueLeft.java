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

@Autonomous(preselectTeleOp = "Drive")
public class BlueLeft extends LinearOpMode {
    VisionPortal propVision;
    PropProcessor pipeline = new PropProcessor(telemetry);

    @Override
    public void runOpMode() {
        propVision = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam"), pipeline);
        Pose2d beginPose = new Pose2d(15, 62, -Math.PI / 2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Claw claw = new Claw(hardwareMap);

        Servo Airplane = hardwareMap.get(Servo.class, "planeshooter");
        Airplane.setPosition(.5);

        waitForStart();

        Actions.runBlocking(claw.moveWrist(.7));

        int propPosition = pipeline.GetPropPosition();
        if (opModeIsActive()) {
            switch (propPosition) {
                case 1:
                    Actions.runBlocking(
                            new ParallelAction(
                                    claw.ApplyArmMotors(),
                                    drive.actionBuilder(beginPose)
                                            .stopAndAdd(claw.moveArm(20))
                                            .setTangent(0)
                                            .splineToLinearHeading(new Pose2d(8.0, 32.0, Math.PI), -Math.PI)

                                            // Place purple pixel
                                            .stopAndAdd(claw.closeLeftClaw(false))

                                            .strafeTo(new Vector2d(49.0, 28.0))

                                            // Place yellow pixel
                                            .stopAndAdd(claw.moveWrist(0))
                                            .stopAndAdd(claw.moveArm(2400))
                                            .stopAndAdd(new SleepAction(.5))
                                            .stopAndAdd(claw.closeRightClaw(false))
                                            .stopAndAdd(new SleepAction(.5))
                                            .stopAndAdd(claw.moveArm(30))
                                            .strafeTo(new Vector2d(40.0, 31.0))


                                            .setTangent(Math.PI / 2)
                                            .splineToConstantHeading(new Vector2d(52.0, 60.0), 0.0)
                                            .build()
                            )
                    );
                    break;

                case 2:
                    Actions.runBlocking(
                            new ParallelAction(
                                    claw.ApplyArmMotors(),
                                    drive.actionBuilder(beginPose)
                                            .stopAndAdd(claw.moveArm(20))
                                            .strafeToLinearHeading(new Vector2d(24, 24), Math.PI)

                                            // Place purple pixel
                                            .stopAndAdd(claw.closeLeftClaw(false))

                                            .strafeTo(new Vector2d(49.0, 37.0))

                                            // Place yellow pixel
                                            .stopAndAdd(claw.moveWrist(0))
                                            .stopAndAdd(claw.moveArm(2400))
                                            .stopAndAdd(new SleepAction(.5))
                                            .stopAndAdd(claw.closeRightClaw(false))
                                            .stopAndAdd(new SleepAction(.5))

                                            .strafeTo(new Vector2d(40.0, 31.0))
                                            .stopAndAdd(claw.moveArm(0))


                                            .setTangent(Math.PI / 2)
                                            .splineToConstantHeading(new Vector2d(52.0, 60.0), 0.0)
                                            .build()
                            )
                    );
                    break;

                case 3:
                    Actions.runBlocking(
                            new ParallelAction(
                                    claw.ApplyArmMotors(),
                                    drive.actionBuilder(beginPose)
                                            .stopAndAdd(claw.moveArm(20))
                                            .strafeToLinearHeading(new Vector2d(36, 34), Math.PI)

                                            // Place purple pixel
                                            .stopAndAdd(claw.closeLeftClaw(false))
                                            .stopAndAdd(new SleepAction(.5))


                                            .strafeTo(new Vector2d(48, 42))

                                            // Place yellow pixel
                                            .stopAndAdd(claw.moveWrist(0))
                                            .stopAndAdd(claw.moveArm(2550))
                                            .stopAndAdd(new SleepAction(.5))
                                            .stopAndAdd(claw.closeRightClaw(false))
                                            .stopAndAdd(new SleepAction(1))
                                            .stopAndAdd(claw.moveArm(100))

                                            .setTangent(Math.PI / 2)
                                            .splineToConstantHeading(new Vector2d(52.0, 60.0), 0.0)
                                            .build()
                            )
                    );
                    break;
            }
        }
    }
}
