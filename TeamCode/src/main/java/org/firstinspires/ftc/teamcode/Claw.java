package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private DcMotorEx arm;
    private int armTargetPosition = 100;
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
        arm.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set positions of servos on class initialization
        rightClaw.setPosition(1);
        leftClaw.setPosition(0);
        wrist.setPosition(.8);
    }

    public Action moveArm(int position) {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                armTargetPosition = position;
                int error = armTargetPosition - arm.getCurrentPosition();
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
                leftClaw.setPosition(closed ? 0 : .2);
                return false;
            }
        };
    }

    public Action closeRightClaw(boolean closed) {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                rightClaw.setPosition(closed ? 1 : .8);
                return false;
            }
        };
    }

    public Action ApplyArmMotors() {
        return new Action() {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                int error = armTargetPosition - arm.getCurrentPosition();
                arm.setPower(error / 133.0);
                return true;
            }
        };
    }

}
