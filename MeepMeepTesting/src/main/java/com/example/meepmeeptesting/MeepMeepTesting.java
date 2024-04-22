package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import kotlin.math.UMathKt;


public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);
        Claw claw = new Claw();

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(
                myBot.getDrive().actionBuilder(new Pose2d(-34, 62, Math.PI)).setTangent(Math.PI)
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
                        .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}