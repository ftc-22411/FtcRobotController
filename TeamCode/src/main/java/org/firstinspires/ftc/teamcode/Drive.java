package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

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

        int pickPos = 1;
        boolean pickPrevValue = false;
        double planePos = .5;
        boolean planePrevValue = false;


        // Put initialization blocks here.
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();
        if (opModeIsActive()) {
            arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            while (opModeIsActive()) {
                // Put loop blocks here.


                vertical = -gamepad1.left_stick_y;
                horizontal = gamepad1.right_bumper ? 0 : gamepad1.left_stick_x;
                angle = gamepad1.right_stick_x;
                frontRight.setPower((-angle + (vertical - horizontal)) * speed);
                backRight.setPower((-angle +  vertical + horizontal) * speed);
                frontLeft.setPower(( angle +  vertical + horizontal) * speed);
                backLeft.setPower(( angle + (vertical - horizontal)) * speed);


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
}
