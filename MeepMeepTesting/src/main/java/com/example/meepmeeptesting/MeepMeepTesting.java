package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(
                myBot.getDrive().actionBuilder(new Pose2d(-32, 62, -Math.PI / 2))
                        .setTangent(Math.PI)
                        .splineToLinearHeading(new Pose2d(-38, 32, Math.PI), 0)

                        // Place Purple Pixel
                        .stopAndAdd(new SleepAction(.5))

                        .setTangent(Math.PI / 8)
                        .splineToConstantHeading(new Vector2d(-10, 36.0), 0)
                        .splineToConstantHeading(new Vector2d(36.0, 31.0), 0)

                        // Place yellow pixel
                        .stopAndAdd(new SleepAction(.5))

                        .setTangent(Math.PI / 2)
                        .splineToConstantHeading(new Vector2d(52.0, 60.0), 0.0)
                        .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}