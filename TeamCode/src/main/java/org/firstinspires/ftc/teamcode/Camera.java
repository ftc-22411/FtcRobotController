package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous
public class Camera extends LinearOpMode {
    VisionPortal testVision;
    PropProcessor pipeline = new PropProcessor(telemetry);




    @Override
    public void runOpMode() {
        testVision = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam"), pipeline);
        waitForStart();
        if(opModeIsActive()) {
        }
    }
}
