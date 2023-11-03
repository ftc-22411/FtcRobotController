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

        DcMotor arm = hardwareMap.get(DcMotor.class, "arm");


        // Put initialization blocks here.
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        if (opModeIsActive()) {
            arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            while (opModeIsActive()) {
                // Put loop blocks here.


                vertical = -gamepad1.left_stick_y;
                horizontal = gamepad1.left_stick_x;
                angle = gamepad1.right_stick_x;
                frontRight.setPower(-angle + (vertical - horizontal));
                backRight.setPower(-angle + vertical + horizontal);
                frontLeft.setPower(angle + vertical + horizontal);
                backLeft.setPower(angle + (vertical - horizontal));

                arm.setPower((gamepad1.dpad_up ? 1 : 0) -
                        (gamepad1.dpad_down && arm.getCurrentPosition() >= 0 ? 1 : 0));

                TelemetryPacket packet = new TelemetryPacket();
                packet.put("Left Stick X", gamepad1.left_stick_x);
                packet.put("Left Stick Y", gamepad1.left_stick_y);
                packet.put("Right Stick X", gamepad1.right_stick_x);
                packet.put("Right Stick Y", gamepad1.right_stick_y);
                packet.put("Arm Pos", arm.getCurrentPosition());

                FtcDashboard dashboard = FtcDashboard.getInstance();
                dashboard.sendTelemetryPacket(packet);
            }
        }
    }
}
