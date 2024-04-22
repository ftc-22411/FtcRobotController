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
public class RedClose extends LinearOpMode {
    VisionPortal propVision;
    PropProcessor pipeline = new PropProcessor(telemetry);

    @Override
    public void runOpMode() {
        propVision = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam"), pipeline);

        Pose2d beginPose = new Pose2d(15, -62, Math.PI );
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Servo Airplane = hardwareMap.get(Servo.class, "planeshooter");

        Claw claw = new Claw(hardwareMap);
        Airplane.setPosition(0);


        waitForStart();

        Actions.runBlocking(claw.moveWrist(.655));

        int propPosition = pipeline.GetPropPosition();
        if(opModeIsActive()) {
            switch (propPosition) {
                case 3:
                    Actions.runBlocking(
                            new ParallelAction(
                                    claw.ApplyArmMotors(), drive.actionBuilder(beginPose)
//                        drive.actionBuilder(new Pose2d(15, -62, Math.PI / 2))
                                .setTangent(0)
                                .lineToX(34)
//                                .strafeToLinearHeading(new Vector2d(34, -34), -Math.PI)
                                .setTangent(Math.PI/2)
                                .lineToY(-34)

                                // Place purple pixel
                                .setTangent(0)
                                .lineToX(16)
                                .stopAndAdd(claw.closeRightClaw(false))
                                .stopAndAdd(claw.moveWrist(.71))
                                .stopAndAdd(new SleepAction(.5))
                                .stopAndAdd(claw.closeLeftClaw(true))
                                .afterDisp(3, claw.moveArm(280))
                                .setTangent(0)


                                .splineToLinearHeading(new Pose2d(53, -29, 0), 0)

                                // Place yellow pixel

                                .stopAndAdd(new SleepAction(.5))
                                .stopAndAdd(claw.closeLeftClaw(false))
                                .stopAndAdd(new SleepAction(1))
                                .lineToX(45)
                                .stopAndAdd(claw.moveWrist(.8))
                                .stopAndAdd(claw.moveArm(100))

                                .setTangent(Math.PI)
                                .splineTo(new Vector2d(51.0, -60.0), 0)
                                .build()));
                    break;

                case 2:
                    Actions.runBlocking(
                            new ParallelAction(
                                    claw.ApplyArmMotors(),
                            drive.actionBuilder(beginPose)
                                    .stopAndAdd(claw.moveArm(40))
                                    .stopAndAdd(claw.moveWrist(.7))
//                                    .stopAndAdd(claw.moveArm(20))
                                    .lineToX(32)
                                    .stopAndAdd(claw.moveWrist(.655))
                                    .setTangent(Math.PI/2)
                                    .strafeToLinearHeading(new Vector2d(32, -27), -Math.PI)

                                    // Place purple pixel
                                    .stopAndAdd(claw.closeRightClaw(false))
                                    .waitSeconds(.5)

                                    .setTangent(0)
                                    .stopAndAdd(claw.moveWrist(.73))
                                    .stopAndAdd(new SleepAction(.5))
                                    .stopAndAdd(claw.closeRightClaw(true))
                                    .afterDisp(3, claw.moveArm(280))
                                    .splineToLinearHeading(new Pose2d(53, -35, 0), 0)

                                    // Place yellow pixel

                                    .stopAndAdd(claw.closeLeftClaw(false))
                                    .stopAndAdd(new SleepAction(1))

                                    // .stopAndAdd(claw.moveArm(0))
                                    .afterDisp(1, claw.moveArm(40))

                                    .setTangent(0)
                                    .lineToX(40)

                                    .setTangent(Math.PI )
                                    .splineTo(new Vector2d(52.0, -60.0), 0.0)
                                    .build()));
                    break;

                case 1:
                    Actions.runBlocking(
                            new ParallelAction(
                                    claw.ApplyArmMotors(),
                                    drive.actionBuilder(beginPose)
                                            .strafeToLinearHeading(new Vector2d(40, -35), -Math.PI)

                                            // Place purple pixel
                                            .stopAndAdd(claw.closeRightClaw(false))
                                            .stopAndAdd(claw.moveWrist(.71))
                                            .stopAndAdd(new SleepAction(.5))
                                            .stopAndAdd(claw.closeRightClaw(true))
                                            .afterDisp(3, claw.moveArm(280))


                                            .splineToLinearHeading(new Pose2d(55, -46, 0), 0)

                                            // Place yellow pixel

                                            .stopAndAdd(new SleepAction(.5))
                                            .stopAndAdd(claw.closeLeftClaw(false))
                                            .stopAndAdd(new SleepAction(1))
                                            .lineToX(45)
                                            .stopAndAdd(claw.moveWrist(.8))
                                            .stopAndAdd(claw.moveArm(100))

                                            .setTangent(Math.PI)
                                            .splineTo(new Vector2d(52.0, -60.0), 0)
                                            .build()
                            )
                    );
                    break;
            }
        }
    }
}
