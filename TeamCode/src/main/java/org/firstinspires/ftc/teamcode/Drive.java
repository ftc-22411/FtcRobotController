package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Drive")
public class Drive extends LinearOpMode {

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        float angle;
        float vertical;
        float horizontal;

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontleft");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backleft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontright");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backright");


        // Put initialization blocks here.
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.

                vertical = -gamepad1.left_stick_y;
                horizontal = gamepad1.left_stick_x;
                angle = gamepad1.right_stick_x;
                frontRight.setPower(-angle + (vertical - horizontal));
                backRight.setPower(-angle + vertical + horizontal);
                frontLeft.setPower(angle + vertical + horizontal);
                backLeft.setPower(angle + (vertical - horizontal));

                TelemetryPacket packet = new TelemetryPacket();
                packet.put("x", gamepad1.left_stick_x);
                packet.put("y", gamepad1.left_stick_y);
                packet.put("z", gamepad1.right_stick_x);
                packet.put("t", gamepad1.right_stick_y);

                FtcDashboard dashboard = FtcDashboard.getInstance();
                dashboard.sendTelemetryPacket(packet);
            }
        }
    }
}
