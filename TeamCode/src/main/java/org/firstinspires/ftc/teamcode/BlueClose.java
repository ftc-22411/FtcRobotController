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
public class BlueClose extends LinearOpMode {
    VisionPortal propVision;
    PropProcessor pipeline = new PropProcessor(telemetry);

    @Override
    public void runOpMode() {
        propVision = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam"), pipeline);
        Pose2d beginPose = new Pose2d(15, 62, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Claw claw = new Claw(hardwareMap);

        Servo Airplane = hardwareMap.get(Servo.class, "planeshooter");
        Airplane.setPosition(0);

        waitForStart();
        Actions.runBlocking(claw.moveWrist(.65));


        // Actions.runBlocking(claw.moveWrist(.7));

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
                                            .splineToLinearHeading(new Pose2d(20.0, 32.0, -Math.PI), -Math.PI)

                                            // Place purple pixel
                                            .stopAndAdd(claw.closeLeftClaw(false))
                                            .setTangent(0)
                                            .stopAndAdd(claw.moveWrist(.71))
                                            .stopAndAdd(new SleepAction(.5))
                                            .stopAndAdd(claw.closeLeftClaw(true))
                                            .afterDisp(3, claw.moveArm(280))
                                            .splineToLinearHeading(new Pose2d(55.0, 31.0, 0), 0)


                                            // Place yellow pixel
                                            .stopAndAdd(new SleepAction(.5))
                                            .stopAndAdd(claw.closeRightClaw(false))
                                            .stopAndAdd(new SleepAction(.5))
                                            .lineToX(40)
                                            .stopAndAdd(claw.moveArm(30))


                                            .setTangent(Math.PI / 2)
                                            .splineToLinearHeading(new Pose2d(52.0, 12.0, Math.PI), 0.0)
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
                                            .setTangent(Math.PI/2)
                                            .strafeToLinearHeading(new Vector2d(34, 26), -Math.PI)

                                            // Place purple pixel
                                             .stopAndAdd(claw.closeLeftClaw(false))
                                            .waitSeconds(.5)

                                            .setTangent(0)
                                            .stopAndAdd(claw.moveWrist(.73))
                                            .stopAndAdd(new SleepAction(.5))
                                            .stopAndAdd(claw.closeLeftClaw(true))
                                            .afterDisp(3, claw.moveArm(220))
                                            .splineToLinearHeading(new Pose2d(55, 38, 0), 0)



                                            // Place yellow pixel
                                            .waitSeconds(.5)
                                            .stopAndAdd(claw.closeRightClaw(false))
                                            .stopAndAdd(new SleepAction(.5))

                                            // .stopAndAdd(claw.moveArm(0))
                                            .setTangent(0)
                                            .lineToX(40)



                                            .setTangent(Math.PI )
                                            .splineTo(new Vector2d(52.0, 12.0), 0.0)
                                            .build()
                            )
                    );
                    break;

                case 3:
                    Actions.runBlocking(
                            new ParallelAction(
                                    claw.ApplyArmMotors(),
                                    drive.actionBuilder(beginPose)
                                            .strafeToLinearHeading(new Vector2d(41, 32), -Math.PI)

                                            // Place purple pixel
                                            .stopAndAdd(claw.closeLeftClaw(false))
                                            .stopAndAdd(claw.moveWrist(.71))
                                            .stopAndAdd(new SleepAction(.5))
                                            .stopAndAdd(claw.closeLeftClaw(true))
                                            .afterDisp(3, claw.moveArm(250))


                                            .splineToLinearHeading(new Pose2d(53, 45, 0), 0)

                                            // Place yellow pixel

                                            .stopAndAdd(new SleepAction(.5))
                                            .stopAndAdd(claw.closeRightClaw(false))
                                            .stopAndAdd(new SleepAction(1))
                                            .lineToX(45)
                                            .stopAndAdd(claw.moveWrist(.8))
                                            .stopAndAdd(claw.moveArm(100))

                                            .setTangent(Math.PI)
                                            .splineTo(new Vector2d(52.0, 12.0), 0)
                                            .build()
                            )
                    );
                    break;
            }
        }
    }
}
