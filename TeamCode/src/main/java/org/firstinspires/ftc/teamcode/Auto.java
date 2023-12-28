//package org.firstinspires.ftc.teamcode;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.Servo;
//import org.opencv.*;
//
//@Autonomous(name = "Autonomous")
//public class Auto extends LinearOpMode {
//
//    /**
//     * This function is executed when this Op Mode is selected from the Driver Station.
//     */
//    @Override
//    public void runOpMode() {
//
//
//
//        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontleft");
//        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backleft");
//        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontright");
//        DcMotor backRight = hardwareMap.get(DcMotor.class, "backright");
//
//        DcMotor arm = hardwareMap.get(DcMotor.class, "arm");
//
//        Servo leftPickup = hardwareMap.get(Servo.class, "Left Pickup");
//        Servo rightPickup = hardwareMap.get(Servo.class, "Right Pickup");
//        Servo planeShooter = hardwareMap.get(Servo.class, "PlaneShooter");
//
//        Servo wrist = hardwareMap.get(Servo.class, "Wrist");
//
//        int pickPos = 1;
//        boolean pickPrevValue = false;
//
//        // Put initialization blocks here.
//        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
//        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
//        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        waitForStart();
//        if (opModeIsActive()) {
//            arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//
//
//            TelemetryPacket packet = new TelemetryPacket();
//
//            FtcDashboard dashboard = FtcDashboard.getInstance();
//            dashboard.sendTelemetryPacket(packet);
//            }
//        }
//    }
//}
