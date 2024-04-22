package org.firstinspires.ftc.teamcode;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.ArrayList;
import java.util.List;


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
        float speed = .8f;

        GamepadEx driveGamepad = new GamepadEx(gamepad1);
        GamepadEx armGamepad = new GamepadEx(gamepad2);

        List<LynxModule> hubs = hardwareMap.getAll(LynxModule.class);

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


        double wristMin = .66;
        double wristMax = .83;


        ToggleButtonReader pickRightToggle = new ToggleButtonReader(armGamepad, GamepadKeys.Button.RIGHT_BUMPER);
        ToggleButtonReader pickLeftToggle = new ToggleButtonReader(armGamepad, GamepadKeys.Button.LEFT_BUMPER);
        ToggleButtonReader hookToggle = new ToggleButtonReader(driveGamepad, GamepadKeys.Button.LEFT_BUMPER);
        ToggleButtonReader planeToggle = new ToggleButtonReader(armGamepad, GamepadKeys.Button.A);

        // Initialization
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        arm.setDirection(DcMotorSimple.Direction.REVERSE);

        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        int armPos = arm.getCurrentPosition();

        LED rightLight = hardwareMap.get(LED.class, "Right Light");
        LED leftLight = hardwareMap.get(LED.class, "Left Light");

        DistanceSensor leftSensor = (DistanceSensor)hardwareMap.get(ColorRangeSensor.class, "color");
        DistanceSensor rightSensor = (DistanceSensor)hardwareMap.get(ColorRangeSensor.class, "color2");

//        rightLight.setMode(DigitalChannel.Mode.OUTPUT);
//        leftLight.setMode(DigitalChannel.Mode.OUTPUT);

        waitForStart();
        while (opModeIsActive()) {

            pickRightToggle.readValue();
            pickLeftToggle.readValue();
            planeToggle.readValue();
            hookToggle.readValue();

            vertical = -gamepad1.left_stick_y;
            horizontal = gamepad1.right_bumper ? 0 : gamepad1.left_stick_x;
            angle = gamepad1.right_stick_x;
            frontRight.setPower((-angle + (vertical - horizontal)) * speed);
            backRight.setPower((-angle + vertical + horizontal) * speed);
            frontLeft.setPower((angle + vertical + horizontal) * speed);
            backLeft.setPower((angle + (vertical - horizontal)) * speed);

//            if (gamepad1.a) speed = .85f;
//            else if (gamepad1.b) speed = .33f;
//            else if (gamepad1.x) speed = 1f;
//            ArrayList<Integer> buttons = new ArrayList<Integer>();
//            if (gamepad1.a) buttons.add(0x00FF00);
//            if (gamepad1.b) buttons.add(0xFFFFFF);
//            if (gamepad1.x) buttons.add(0xBB00FF);
//            if (gamepad1.y) buttons.add(0xFFFF00);
//
//            if (gamepad1.back) {
//                if (buttons.size() > 1) {
//                    hubs.get(0).setConstant(buttons.get(0));
//                    hubs.get(1).setConstant(buttons.get(1));
//                } else if (buttons.size() == 1) {
//                    hubs.get(0).setConstant(buttons.get(0));
//                    hubs.get(1).setConstant(buttons.get(0));
//                }
//            }
            for (LynxModule hub:hubs) {
                if (gamepad1.a) hub.setConstant(0x00FF00);
                if (gamepad1.b) hub.setConstant(0xFFFFFF);
                if (gamepad1.x) hub.setConstant(0xBB00FF);
                if (gamepad1.y) hub.setConstant(0xFFFF00);
            }


            //Hang and plane toggle
            planeShooter.setPosition(planeToggle.getState() ? 1 : 0);
            hookArm.setPosition(hookToggle.getState() ? .1 : .5);
            // Claw starts open
            //Right
            leftPickup.setPosition(pickLeftToggle.getState() ? 0 : .2);
            //Left
            rightPickup.setPosition(pickRightToggle.getState() ? 1: .8);

            armPos += ((gamepad2.dpad_up ? 25 : 0) -
                    (gamepad2.dpad_down && armPos > 75 ? 25 : 0));

            hook.setPower((gamepad1.dpad_up ? 1 : 0) -
                    (gamepad1.dpad_down ? 1 : 0));
            wrist.setPosition(wristMin + (wristMax - wristMin) * gamepad2.left_trigger);

            rightLight.enableLight(rightSensor.getDistance(DistanceUnit.CM) < 1);
            leftLight.enableLight(leftSensor.getDistance(DistanceUnit.CM) < 1);

            telemetry.addData("rightSensor?", rightSensor.getDistance(DistanceUnit.CM));
            telemetry.addData("leftSensor?", leftSensor.getDistance(DistanceUnit.CM));

            moveArmToPos(armPos);
            telemetry.addData("Wrist Pos", wrist.getPosition());
            telemetry.addData("armPos", arm.getCurrentPosition());
            TelemetryPacket packet = new TelemetryPacket();
            packet.put("Arm Pos", arm.getCurrentPosition());
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
