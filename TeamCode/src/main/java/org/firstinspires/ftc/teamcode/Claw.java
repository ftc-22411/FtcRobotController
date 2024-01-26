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
    private Servo leftClaw, rightClaw, wrist;

    private boolean leftClawDown, rightClawDown;

    public Claw(HardwareMap hardwareMap) {
        arm = hardwareMap.get(DcMotorEx.class, "arm");
        leftClaw = hardwareMap.get(Servo.class, "Left Pickup");
        rightClaw = hardwareMap.get(Servo.class, "Right Pickup");
        wrist = hardwareMap.get(Servo.class, "Wrist");

        // Reset motor encoders
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set positions of servos on class initialization
        rightClaw.setPosition(0);
        leftClaw.setPosition(1);
        wrist.setPosition(0);
    }

    public Action moveArm(int position) {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                int error = position - arm.getCurrentPosition();
                arm.setPower(error / 600.0);
                return Math.abs(error) > 20;
            }
        };
    }
    public Action moveWrist(double position) {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                wrist.setPosition(position);
                return false;
            }
        };
    }

    public Action closeLeftClaw(boolean closed) {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                leftClaw.setPosition(closed ? 1 : 0);
                return false;
            }
        };
    }

    public Action closeRightClaw(boolean closed) {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                rightClaw.setPosition(closed ? 0 : 1);
                return false;
            }
        };
    }

}
