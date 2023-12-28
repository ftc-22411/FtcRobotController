package org.firstinspires.ftc.teamcode;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.*;


public class PropProcessor implements VisionProcessor {

	// Left: 1, Center 2, Right: 3
	private int propPosition = 0;

	Telemetry telemetry;
	public PropProcessor(Telemetry telemetry) {
		this.telemetry = telemetry;
	}

	@Override
	public void init(int width, int height, CameraCalibration calibration) {
	}

	@Override
	public Object processFrame(Mat frame, long captureTimeNanos) {
		// Directions are from camera perspective
		Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2HSV);
		Mat leftMat = new Mat(frame, new Rect(45, 205, 120, 113));
		Mat centerMat= new Mat(frame, new Rect(281, 262, 94, 85));
		Mat rightMat= new Mat(frame, new Rect(492, 218, 106, 103));

		double leftSat = Core.mean(leftMat).val[1];
		double centerSat = Core.mean(centerMat).val[1];
		double rightSat = Core.mean(rightMat).val[1];

		// Left: 1, Center 2, Right: 3
		if(leftSat > rightSat && leftSat > centerSat) propPosition = 1;
		else if(centerSat > leftSat && centerSat > rightSat) propPosition = 2;
		else propPosition = 3;


		telemetry.addData("Left Mean", leftSat);
		telemetry.addData("Center Mean", centerSat);
		telemetry.addData("Right Mean", rightSat);
		
		telemetry.update();
		Imgproc.cvtColor(frame, frame, Imgproc.COLOR_HSV2RGB);
		return null;

	}

	public int GetPropPosition() {
		return propPosition;
	}

	@Override
	public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

	}
}

