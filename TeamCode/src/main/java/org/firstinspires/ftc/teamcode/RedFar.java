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
public class RedFar extends LinearOpMode {
    VisionPortal propVision;
    PropProcessor pipeline = new PropProcessor(telemetry);



    @Override

    public void runOpMode() {
        propVision = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam"), pipeline);

        Pose2d beginPose = new Pose2d(-32, -62, Math.PI);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Claw claw = new Claw(hardwareMap);

        Servo Airplane = hardwareMap.get(Servo.class, "planeshooter");
        Airplane.setPosition(0);


        waitForStart();
        Actions.runBlocking(claw.moveWrist(.65));


        int propPosition = pipeline.GetPropPosition();
//        int propPosition = 2;
        if(opModeIsActive()) {
            switch (propPosition) {
                case 1:
                    Actions.runBlocking(
                            new ParallelAction(claw.ApplyArmMotors(),
                            drive.actionBuilder(beginPose)
                                    .waitSeconds(5)
                                    .setTangent(Math.PI)
                                    .splineToLinearHeading(new Pose2d(-35, -32, 0), 0)

                                    // Place Purple Pixel


                        .stopAndAdd(claw.closeRightClaw(false))
                        .stopAndAdd(claw.moveWrist(.71))
                                    .setReversed(true)
                                    .lineToX(-46)
                                    .setTangent(Math.PI/2)
                                    .splineToLinearHeading(new Pose2d(57.0, -43.0, 0), -Math.PI/3)
                                    .afterDisp(55, claw.moveArm(300))


                                    // Place yellow pixel
                                    .stopAndAdd(new SleepAction(.5))
                        .stopAndAdd(claw.closeLeftClaw(false))
                                    .stopAndAdd(new SleepAction(.5))
//


                                    .setTangent(Math.PI)
                                    .lineToX(38)
                                    .splineTo(new Vector2d(52.0, -12.0), 0.0)
                                    .stopAndAdd(claw.moveArm(10))

                                    .build()));
                    break;

                case 2:
                    Actions.runBlocking(
                            new ParallelAction(claw.ApplyArmMotors(),
                            drive.actionBuilder(beginPose)
                                    .waitSeconds(5)
                                    .setTangent(Math.PI)
                                    .splineToLinearHeading(new Pose2d(-43, -22, 0), Math.PI/2)


                                    // Place Purple Pixel

                                    .stopAndAdd(claw.closeRightClaw(false))
                                    .stopAndAdd(claw.moveWrist(.71))


                                    .setTangent(Math.PI / 2)
                                    .splineToSplineHeading(new Pose2d(-30, -12, 0), 0)

                                    .setTangent(0)
                                    .lineToX(30)


                                    .setTangent(0)
                                    .splineToSplineHeading(new Pose2d(53, -33 , 0 ), -Math.PI / 2)
                                    .stopAndAdd(new SleepAction(.5))
                                    .stopAndAdd(claw.closeLeftClaw(false))
                                    .stopAndAdd(new SleepAction(.5))
                                    .lineToX(48)

                                    .stopAndAdd(claw.moveArm(10))

                                    .setTangent(Math.PI)

                                    .splineTo(new Vector2d(52.0, -10.0), 0)
                                    .build()));
                    break;

                case 3:
                Actions.runBlocking(
                        new ParallelAction(claw.ApplyArmMotors(),
                                drive.actionBuilder(beginPose)
                                        .waitSeconds(5)
                                        .setTangent(Math.PI)
                                        .splineToLinearHeading(new Pose2d(-40, -21, 3*Math.PI/2), 0)

                                        // Place Purple Pixel



                                        .stopAndAdd(claw.closeRightClaw(false))
                                        .stopAndAdd(claw.moveWrist(.71))
                                        .setReversed(true)
                                        .afterDisp(20, claw.moveArm(350))
                                        .setTangent(Math.PI/2)
                                        .lineToY(-10)
                                        .setTangent(0)
                                        .lineToX(49)
                                        .setTangent(Math.PI/2)

                                        .lineToY(-36)



//                                        .setTangent(3 *Math.PI / 5)
//                                        .splineToLinearHeading(new Pose2d(49.0, -36.0, 0), -Math.PI/3)

                                        // Place yellow pixel
                                        .stopAndAdd(new SleepAction(.5))
                                        .stopAndAdd(claw.closeLeftClaw(false))
                                        .stopAndAdd(new SleepAction(.5))
                                        .strafeTo(new Vector2d(40.0, -31.0))
                                        .stopAndAdd(claw.moveArm(10))


                                        .setTangent(Math.PI)
                                        .splineToLinearHeading(new Pose2d(52.0, -12.0, Math.PI), 0.0)
                                        .build()));
                    break;
            }
        }
    }
}
