package org.firstinspires.ftc.teamcode;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

@TeleOp(name = "Drive")
public class Drive extends LinearOpMode {

    private DcMotor arm;

    private PIDCoefficients coeff = new PIDCoefficients(0.002, 0, 0);
    private BasicPID armController = new BasicPID(coeff);


    @Override
    public void runOpMode() {
        float angle;
        float vertical;
        float horizontal;
        float speed = .75f;

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontleft");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backleft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontright");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backright");
        DcMotor par1 =  hardwareMap.get(DcMotor.class, "par1");
        DcMotor hook = hardwareMap.get(DcMotor.class, "hook");

        arm = hardwareMap.get(DcMotor.class, "arm");

        Servo leftPickup = hardwareMap.get(Servo.class, "Left Pickup");
        Servo rightPickup = hardwareMap.get(Servo.class, "Right Pickup");
        Servo planeShooter = hardwareMap.get(Servo.class, "planeshooter");

        Servo hookArm = hardwareMap.get(Servo.class, "Hook Arm");

        Servo wrist = hardwareMap.get(Servo.class, "Wrist");
        ColorSensor Color1 = hardwareMap.get(ColorSensor.class, "color");
        ColorSensor Color2 = hardwareMap.get(ColorSensor.class, "color2");

        double pickPosRight = .74;
        double hookPOS = 1;
        double pickPosLeft = .30;
        int armPos = arm.getCurrentPosition();
        double wristMin = .65;
        double wristMax = .83;

        boolean pickPrevValueRight = false;
        boolean pickPrevValueLeft = false;
        boolean hookPrevValue = true;

        double planePos = 1;
        boolean planePrevValue = false;


        // Initialization
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
//        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        waitForStart();
        while (opModeIsActive()) {
            vertical = -gamepad1.left_stick_y;
            horizontal = gamepad1.right_bumper ? 0 : gamepad1.left_stick_x;
            angle = gamepad1.right_stick_x;
            frontRight.setPower((-angle + (vertical - horizontal)) * speed);
            backRight.setPower((-angle + vertical + horizontal) * speed);
            frontLeft.setPower((angle + vertical + horizontal) * speed);
            backLeft.setPower((angle + (vertical - horizontal)) * speed);

            if (gamepad1.a) speed = .75f;
            else if (gamepad1.b) speed = .33f;
            else if (gamepad1.x) speed = 1f;

            if (!pickPrevValueRight && gamepad2.right_bumper) {
                pickPosRight = pickPosRight == .74 ? .4 : .74;
            }

            if (!pickPrevValueLeft && gamepad2.left_bumper) {
                pickPosLeft = pickPosLeft == .55 ? .30 : .55;
            }
//            if (((OpticalDistanceSensor) Color2).getLightDetected() >= .6 && !gamepad2.right_bumper){
//                pickPosRight = .3;
//            }
//            if (((OpticalDistanceSensor) Color1).getLightDetected() >= .6 && !gamepad2.left_bumper){
//                pickPosLeft = .7;
//            }
            if (!hookPrevValue && gamepad1.left_bumper) {
                hookPOS = hookPOS == .63 ? 1 : .63;
            }

            if (!planePrevValue && gamepad2.a) {
                planePos = planePos == 0 ? 1 : 0;
            }
            planeShooter.setPosition(planePos);
            hookArm.setPosition(hookPOS);
            planePrevValue = gamepad2.a;
            hookPrevValue = gamepad1.left_bumper;
            leftPickup.setPosition(pickPosLeft);
            rightPickup.setPosition(pickPosRight);

            pickPrevValueRight = gamepad2.right_bumper;
            pickPrevValueLeft = gamepad2.left_bumper;
            armPos += ((gamepad2.dpad_up ? 10 : 0) -
                    (gamepad2.dpad_down && armPos > 75 ? 10 : 0));

            hook.setPower((gamepad1.dpad_up ? 1 : 0) -
                    (gamepad1.dpad_down ? 1 : 0));
            wrist.setPosition(wristMin + (wristMax - wristMin) * gamepad2.left_trigger);


            moveArmToPos(armPos);
            telemetry.addData("Wrist Pos", wrist.getPosition());
            telemetry.addData("armPos", arm.getCurrentPosition());
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Arm Pos", arm.getCurrentPosition());
            packet.put("Pick Pos", pickPosRight);
            packet.put("Plane Pos", planePos);
            packet.put("Wrist Pos", wrist.getPosition());
            packet.put("par0", backLeft.getCurrentPosition());
            packet.put("par1", par1.getCurrentPosition());

            FtcDashboard dashboard = FtcDashboard.getInstance();
            dashboard.sendTelemetryPacket(packet);
            telemetry.update();
        }
    }
    void moveArmToPos(int targetPos) {
        double output = armController.calculate(targetPos, arm.getCurrentPosition());

        arm.setPower(output);
    }
}
