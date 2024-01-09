package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private DcMotorEx arm;
    private Servo leftClaw, rightClaw;

    public Claw(HardwareMap hardwareMap) {
        arm = hardwareMap.get(DcMotorEx.class, "arm");
        leftClaw = hardwareMap.get(Servo.class, "Left Pickup");
        rightClaw = hardwareMap.get(Servo.class, "Right Pickup");
    }

    public Action moveArm(int position) {
        return new Action() {

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                int error = position - arm.getCurrentPosition();
                arm.setPower(error / 300.0);
                return Math.abs(error) > 10;
            }
        };
    }

}
