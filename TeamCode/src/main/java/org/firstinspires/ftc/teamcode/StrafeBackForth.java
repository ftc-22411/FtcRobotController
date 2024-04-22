package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class StrafeBackForth extends LinearOpMode {
    @Override

    public void runOpMode() {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        while(opModeIsActive()) {
//                Actions.runBlocking(drive.actionBuilder(beginPose)
//                        .strafeTo(new Vector2d(24, 0))
//                        .waitSeconds(1)
//                        .strafeTo(new Vector2d(0, 0))
//                        .build());

                Actions.runBlocking(drive.actionBuilder(beginPose)
                        .lineToX(35)
                        .waitSeconds(1)
                        .lineToX(0)
                        .build());

        }
    }
}
