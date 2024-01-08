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
	private static final Rect LEFT_RECT = new Rect(45, 205, 120, 113);
	private static final Rect CENTER_RECT = new Rect(281, 262, 94, 85);
	private static final Rect RIGHT_RECT = new Rect(492, 218, 106, 103);

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
		Mat hsvFrame = new Mat();
		Imgproc.cvtColor(frame, hsvFrame, Imgproc.COLOR_RGB2HSV);
		Mat leftMat = new Mat(hsvFrame, LEFT_RECT);
		Mat centerMat= new Mat(hsvFrame, CENTER_RECT);
		Mat rightMat= new Mat(hsvFrame, RIGHT_RECT);

		Imgproc.rectangle(frame, LEFT_RECT, new Scalar(0, 255, 0));
		Imgproc.rectangle(frame, CENTER_RECT, new Scalar(0, 255, 0));
		Imgproc.rectangle(frame, RIGHT_RECT, new Scalar(0, 255, 0));

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
		return null;

	}

	public int GetPropPosition() {
		return propPosition;
	}

	@Override
	public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

	}
}

