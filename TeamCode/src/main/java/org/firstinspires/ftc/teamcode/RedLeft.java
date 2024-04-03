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
public class RedLeft extends LinearOpMode {
    VisionPortal propVision;
    PropProcessor pipeline = new PropProcessor(telemetry);



    @Override

    public void runOpMode() {
        propVision = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam"), pipeline);

        Pose2d beginPose = new Pose2d(-32, -62, Math.PI / 2);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
       // Claw claw = new Claw(hardwareMap);

       // Servo Airplane = hardwareMap.get(Servo.class, "planeshooter");
       // Airplane.setPosition(1);


        waitForStart();
       // Actions.runBlocking(claw.moveWrist(.7));

        int propPosition = 2;
        if(opModeIsActive()) {
            switch (propPosition) {
                case 1:
                    Actions.runBlocking(
                         //   new ParallelAction(claw.ApplyArmMotors(),
                            drive.actionBuilder(beginPose)
                                    .setTangent(Math.PI)
                                    .splineToLinearHeading(new Pose2d(-38, -32, -Math.PI), 0)

                                    // Place Purple Pixel
                                  //  .stopAndAdd(claw.closeLeftClaw(false))

                                    .setReversed(true)
                                    .setTangent(Math.PI / 2)
                                    .splineToLinearHeading(new Pose2d(40.0, -31.0, -Math.PI), -Math.PI * .5)

                                    // Place yellow pixel
                                  //  .stopAndAdd(claw.moveWrist(0))
                                  //  .stopAndAdd(claw.moveArm(2250))
                                    .stopAndAdd(new SleepAction(.5))
                                  //  .stopAndAdd(claw.closeRightClaw(false))
                                    .stopAndAdd(new SleepAction(.5))
                                    .strafeTo(new Vector2d(40.0, -31.0))

                                    .setTangent(Math.PI / 2)
                                    .splineToConstantHeading(new Vector2d(52.0, -60.0), 0.0)
                                    .build());
                    break;

                case 2:
                    Actions.runBlocking(
                           // new ParallelAction(claw.ApplyArmMotors(),
                            drive.actionBuilder(beginPose)
                                    .setTangent(Math.PI)
                                    .splineToLinearHeading(new Pose2d(-44, -6, -Math.PI /2), Math.PI * .5)
                                    .setTangent(-Math.PI / 2)
                                    .splineToConstantHeading(new Vector2d(-38, -12), Math.PI * .5)

                                    // Place Purple Pixel
                                    //  .stopAndAdd(claw.closeLeftClaw(false))

                                    .setReversed(true)
                                    .setTangent(Math.PI / 5)
                                    .strafeTo(new Vector2d(51.0, -12.0))

                                    // Place yellow pixel
                                    //  .stopAndAdd(claw.moveWrist(0))
                                    //  .stopAndAdd(claw.moveArm(3000))
                                    .splineToLinearHeading(new Pose2d(51, -20 , Math.PI ), Math.PI/2)
                                    .stopAndAdd(new SleepAction(.5))
                                    // .stopAndAdd(claw.closeRightClaw(false))
                                    .stopAndAdd(new SleepAction(.5))
                                    .strafeTo(new Vector2d(40.0, -31.0))

                                    //  .stopAndAdd(claw.moveArm(10))

                                    .setTangent(-Math.PI)
                                    .splineToConstantHeading(new Vector2d(52.0, -10.0), 0.0)
                                    .build());
                    break;

                case 3:
                    Actions.runBlocking(
                           // new ParallelAction(claw.ApplyArmMotors(),
                            drive.actionBuilder(beginPose)
                                    .setTangent(Math.PI)
                                    .splineToLinearHeading(new Pose2d(-30, -31, 0), -Math.PI / 5)
                                    .strafeTo(new Vector2d(-16, -36))

                                    // Place Purple Pixel
                                 //   .stopAndAdd(claw.closeLeftClaw(false))
                                    .setReversed(true)
                                    .setTangent(Math.PI / 2)
                                    .splineToLinearHeading(new Pose2d(40.0, -31.0, -Math.PI), -Math.PI * .5)


                                    // Place yellow pixel
                                  //  .stopAndAdd(claw.moveWrist(0))
                                  //  .stopAndAdd(claw.moveArm(2250))
                                   // .stopAndAdd(new SleepAction(.5))
                                  //  .stopAndAdd(claw.closeRightClaw(false))
                                    .stopAndAdd(new SleepAction(.5))
                                    .strafeTo(new Vector2d(40.0, -31.0))

                                    .setTangent(-Math.PI / 2)
                                    .splineToConstantHeading(new Vector2d(48.0, -60.0), 0.0)
                                    .build());
                    break;
            }
        }
    }
}
