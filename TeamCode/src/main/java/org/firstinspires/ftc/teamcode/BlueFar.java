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
public class BlueFar extends LinearOpMode {
    VisionPortal propVision;
    PropProcessor pipeline = new PropProcessor(telemetry);


    @Override
    public void runOpMode() {
        propVision = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam"), pipeline);

        Pose2d beginPose = new Pose2d(-32, 62, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Claw claw = new Claw(hardwareMap);
        Servo Airplane = hardwareMap.get(Servo.class, "planeshooter");

        Airplane.setPosition(0);


        waitForStart();


        Actions.runBlocking(claw.moveWrist(.65));

        int propPosition = pipeline.GetPropPosition();
        if(opModeIsActive()) {
            switch (propPosition) {
                case 3:
                    Actions.runBlocking( new ParallelAction(claw.ApplyArmMotors(),
                            drive.actionBuilder(beginPose)
                                    .setTangent(Math.PI)
                                    .splineToLinearHeading(new Pose2d(-36, 34, 0), 0)

                                    // Place Purple Pixel

                                    .stopAndAdd(claw.closeLeftClaw(false))
                                    .stopAndAdd(claw.moveWrist(.71))
                                    .setReversed(true)
                                    .lineToX(-46)
                                    .setTangent(3*Math.PI/2)
                                    .splineToLinearHeading(new Pose2d(55.0, 53.0, 0), Math.PI/3)

                                    // Place yellow pixel
                                    .stopAndAdd(new SleepAction(.5))
                                    .stopAndAdd(claw.moveArm(280))
                                    .stopAndAdd(claw.closeRightClaw(false))
                                    .stopAndAdd(new SleepAction(.5))
                                    .strafeTo(new Vector2d(40.0, 40.0))
                                    .stopAndAdd(claw.moveArm(10))


                                    .setTangent(Math.PI)
                                    .splineTo(new Vector2d(52.0, 12.0), 0.0)
                                    .build()));
                    break;

                case 2:
                    Actions.runBlocking( new ParallelAction( claw.ApplyArmMotors(),
                            drive.actionBuilder(beginPose)
                                    .stopAndAdd(claw.moveWrist(.7))
                                    .lineToX(-50)
                                    .stopAndAdd(claw.moveWrist(.655))
                                    .setTangent(Math.PI / 2)
                                    .lineToYSplineHeading(30, 0)
                                    .setTangent(3 * Math.PI / 2)
                                    .splineToLinearHeading(new Pose2d(-46, 26, 0), 3 * Math.PI / 2)

                                    // Place Purple Pixel
                                    .stopAndAdd((claw.closeRightClaw(true)))
                                    .stopAndAdd(claw.closeLeftClaw(false))
                                    .stopAndAdd(claw.moveWrist(.73))



                                    .setTangent(3*Math.PI/2)
                                    .splineToSplineHeading(new Pose2d(-32, 12 , 0 ), 0)
                                    .lineToX(28)

                                    .splineToSplineHeading(new Pose2d(57, 49 , 0 ), Math.PI / 4)
                                    .stopAndAdd(new SleepAction(.5))
                                    .stopAndAdd(claw.moveArm(300))
                                    .stopAndAdd(claw.closeRightClaw(false))
                                    .stopAndAdd(new SleepAction(.5))

                                    .lineToX(48)

                                    .stopAndAdd(claw.moveArm(10))

                                    .setTangent(Math.PI)

                                    .splineTo(new Vector2d(55.0, 27.0), 0)
                                    .build()));
                    break;

                case 1:
                    Actions.runBlocking( new ParallelAction( claw.ApplyArmMotors(),
                            drive.actionBuilder(beginPose)
                                    .stopAndAdd(claw.moveWrist(.7))
                                    .setTangent(Math.PI)
                                    .afterDisp(4, claw.moveWrist(.655))
                                    .splineToLinearHeading(new Pose2d(-40, 18, Math.PI/2), 0)

                                    // Place Purple Pixel

                                    .stopAndAdd(claw.closeLeftClaw(false))
                                    .stopAndAdd(claw.moveWrist(.71))
                                    .setReversed(true)
                                    .setTangent(3*Math.PI / 2)
//                                    .afterDisp(30, claw.moveArm(280))

                                    .splineToLinearHeading(new Pose2d(52, 36, 0), Math.PI / 5)

                                    // Place yellow pixel
                                    .stopAndAdd(new SleepAction(.5))
                                    .stopAndAdd(claw.moveArm(300))
                                    .stopAndAdd(claw.closeRightClaw(false))
                                    .stopAndAdd(new SleepAction(.5))
                                    .strafeTo(new Vector2d(40.0, 31.0))
                                    .stopAndAdd(claw.moveArm(10))


                                    .setTangent(Math.PI)
                                    .splineToLinearHeading(new Pose2d(55.0, 28.0, Math.PI), 0.0)
                                    .build()));
                    break;
            }
        }
    }
}
