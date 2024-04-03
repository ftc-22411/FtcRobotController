package com.example.meepmeeptesting;

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

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(
                myBot.getDrive().actionBuilder(new Pose2d(-32, -62, Math.PI / 2))
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


        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}