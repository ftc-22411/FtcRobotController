package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "Drive (Field Centric)")
public class DriveFieldCentric extends LinearOpMode {

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        float speed = .5f;

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontleft");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backleft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontright");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backright");
        DcMotor hook = hardwareMap.get(DcMotor.class, "hook");

        DcMotor arm = hardwareMap.get(DcMotor.class, "arm");

        Servo leftPickup = hardwareMap.get(Servo.class, "Left Pickup");
        Servo rightPickup = hardwareMap.get(Servo.class, "Right Pickup");
        Servo planeShooter = hardwareMap.get(Servo.class, "planeshooter");

        Servo hookArm = hardwareMap.get(Servo.class, "Hook Arm");

        Servo wrist = hardwareMap.get(Servo.class, "Wrist");

        IMU imu = hardwareMap.get(IMU.class, "imu");

        int pickPos = 1;
        boolean pickPrevValue = false;
        double planePos = .5;
        boolean planePrevValue = false;

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN));

        waitForStart();

        while (opModeIsActive()) {
            // Put loop blocks here.

            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.options) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max((Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx)) * speed, 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator * speed;
            double backLeftPower = (rotY - rotX + rx) / denominator * speed;
            double frontRightPower = (rotY - rotX - rx) / denominator * speed;
            double backRightPower = (rotY + rotX - rx) / denominator * speed;

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);


            if(gamepad1.a) speed = .5f;
            else if (gamepad1.b) speed = .25f;
            else if (gamepad1.x) speed = 1f;

            if (!pickPrevValue && gamepad2.b) {
                pickPos = pickPos == 1 ? 0 : 1;
            }

            if (!planePrevValue && gamepad2.a) {
                planePos = planePos == .5 ? 1 : .5;
            }
            planeShooter.setPosition(planePos);
            planePrevValue = gamepad2.a;

            pickPrevValue = gamepad2.b;
            leftPickup.setPosition(1 - pickPos);
            rightPickup.setPosition((pickPos * .9) + .1);

            arm.setPower((gamepad2.dpad_up ? .7 : 0) -
                    (gamepad2.dpad_down ? .7 : 0));
            hook.setPower((gamepad1.dpad_up ? 1 : 0) -
                    (gamepad1.dpad_down ? 1 : 0));

            wrist.setPosition((1 - (gamepad2.right_trigger)) * .70);

            hookArm.setPosition(gamepad1.left_bumper ? 0 : .55);

            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Arm Pos", arm.getCurrentPosition());
            packet.put("Pick Pos", pickPos);
            packet.put("Plane Pos", planePos);
            packet.put("Wrist Pos", wrist.getPosition());

            FtcDashboard dashboard = FtcDashboard.getInstance();
            dashboard.sendTelemetryPacket(packet);
        }
    }
}
