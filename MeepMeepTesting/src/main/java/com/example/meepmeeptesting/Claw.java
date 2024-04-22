package com.example.meepmeeptesting;


import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

public class Claw {
    public Claw() {

    }

    public Action moveArm(int position) {
        return telemetryPacket -> false;
    }
    public Action moveWrist(double position) {
        return telemetryPacket -> false;
    }

    public Action closeLeftClaw(boolean closed) {
        return telemetryPacket -> false;
    }

    public Action closeRightClaw(boolean closed) {
        return telemetryPacket -> false;
    }

}
