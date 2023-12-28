package org.firstinspires.ftc.teamcode;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;


public class PropProcessor implements VisionProcessor {

	private Mat mask = Imgcodecs.imread("mask.png", Imgcodecs.IMREAD_GRAYSCALE);

	Telemetry telemetry;
	public PropProcessor(Telemetry telemetry) {
		this.telemetry = telemetry;
	}

	@Override
	public void init(int width, int height, CameraCalibration calibration) {

	}

	@Override
	public Object processFrame(Mat frame, long captureTimeNanos) {
		telemetry.addData("Frame Height", frame.height());
		telemetry.addData("Frame Width", frame.width());
		telemetry.addData("Mask Height", mask.height());
		telemetry.addData("Mask Width", mask.width());
		telemetry.update();
		return null;

	}

	@Override
	public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

	}
}

