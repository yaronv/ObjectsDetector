package yv.recipe.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.KeyPoint;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import yv.recipe.R;
import yv.recipe.utils.ImageProccessingService;

public class TabLive extends Fragment implements  CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "TabLive";

    private int COUNT_DOWN = 5;

    private ImageButton FAB;
    private boolean isProcess = false;
    private Mat mRgba;
    private Mat mGray;
    private Mat mByte;
    private Scalar CONTOUR_COLOR;
    private CameraBridgeViewBase mOpenCvCameraView;
    private CallbackManager callbackManager;
    private TextView counter;
    private ProgressWheel progress;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(getActivity()) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    mOpenCvCameraView.enableView();
                    Log.i(TAG, "OpenCV loaded successfully");
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_live,container,false);

        mOpenCvCameraView = (CameraBridgeViewBase) v.findViewById(R.id.live_camera_frame);
        mOpenCvCameraView.setCvCameraViewListener(this);

        callbackManager = CallbackManager.Factory.create();

        progress = (ProgressWheel)v.findViewById(R.id.analyze_progress);
        counter = (TextView)v.findViewById(R.id.counter);
        counter.setText("5");

        FAB = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isProcess = !isProcess;

                if(isProcess) {
                        FAB.setVisibility(View.GONE);
//                    Animation scale = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.scale_down);
//                    FAB.startAnimation(scale);

                    counter.setText(String.valueOf(COUNT_DOWN));
                    counter.setVisibility(View.VISIBLE);

                    final Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            counter.setText(String.valueOf(COUNT_DOWN));
                            COUNT_DOWN--;
                            if(COUNT_DOWN >= 0) {
                                handler.postDelayed(this, 1800);
                            }
                            else {
                                COUNT_DOWN = 5;
                                counter.setVisibility(View.GONE);
                                progress.setVisibility(View.VISIBLE);
                                mOpenCvCameraView.disableView();
                                isProcess = false;
                            }
                        }
                    });
                }
                else {
                    COUNT_DOWN = 5;
                    counter.setVisibility(View.GONE);
                }



            }
        });

        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_11, getActivity(), mLoaderCallback);

        return v;
    }



    @Override
    public void onPause() {
        super.onPause();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_11, getActivity(), mLoaderCallback);
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void analyzePhoto(ImageView viewImage) {
        Drawable imgDrawable = viewImage.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();

//                Bitmap bitmap = BitmapFactory.decodeResource(viewImage.getResources(), R.id.image_placeholder);
        ImageProccessingService.getInstance().detectObjects(bitmap);
    }


    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC3);
        mByte = new Mat(height, width, CvType.CV_8UC1);
    }

    @Override
    public void onCameraViewStopped() {
        // Explicitly deallocate Mats
        mRgba.release();
    }


    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();


        CONTOUR_COLOR = new Scalar(255);
        MatOfKeyPoint keypoint = new MatOfKeyPoint();
        List<KeyPoint> listpoint = new ArrayList<KeyPoint>();
        KeyPoint kpoint = new KeyPoint();
        Mat mask = Mat.zeros(mGray.size(), CvType.CV_8UC1);
        int rectanx1;
        int rectany1;
        int rectanx2;
        int rectany2;

        //
        Scalar zeos = new Scalar(0, 0, 0);
        List<MatOfPoint> contour1 = new ArrayList<MatOfPoint>();
        List<MatOfPoint> contour2 = new ArrayList<MatOfPoint>();
        Mat kernel = new Mat(1, 50, CvType.CV_8UC1, Scalar.all(255));
        Mat morbyte = new Mat();
        Mat hierarchy = new Mat();

        Rect rectan2 = new Rect();//
        Rect rectan3 = new Rect();//
        int imgsize = mRgba.height() * mRgba.width();
        //
        if (isProcess) {
            FeatureDetector detector = FeatureDetector
                    .create(FeatureDetector.MSER);
            detector.detect(mGray, keypoint);
            listpoint = keypoint.toList();
            //
            for (int ind = 0; ind < listpoint.size(); ind++) {
                kpoint = listpoint.get(ind);
                rectanx1 = (int) (kpoint.pt.x - 0.5 * kpoint.size);
                rectany1 = (int) (kpoint.pt.y - 0.5 * kpoint.size);
                // rectanx2 = (int) (kpoint.pt.x + 0.5 * kpoint.size);
                // rectany2 = (int) (kpoint.pt.y + 0.5 * kpoint.size);
                rectanx2 = (int) (kpoint.size);
                rectany2 = (int) (kpoint.size);
                if (rectanx1 <= 0)
                    rectanx1 = 1;
                if (rectany1 <= 0)
                    rectany1 = 1;
                if ((rectanx1 + rectanx2) > mGray.width())
                    rectanx2 = mGray.width() - rectanx1;
                if ((rectany1 + rectany2) > mGray.height())
                    rectany2 = mGray.height() - rectany1;
                Rect rectant = new Rect(rectanx1, rectany1, rectanx2, rectany2);
                Mat roi = new Mat(mask, rectant);
                roi.setTo(CONTOUR_COLOR);

            }

            Imgproc.morphologyEx(mask, morbyte, Imgproc.MORPH_DILATE, kernel);
            Imgproc.findContours(morbyte, contour2, hierarchy,
                    Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
            for (int ind = 0; ind < contour2.size(); ind++) {
                rectan3 = Imgproc.boundingRect(contour2.get(ind));
                if (rectan3.area() > 0.5 * imgsize || rectan3.area() < 100
                        || rectan3.width / rectan3.height < 2) {
                    Mat roi = new Mat(morbyte, rectan3);
                    roi.setTo(zeos);

                } else
                    Core.rectangle(mRgba, rectan3.br(), rectan3.tl(),
                            CONTOUR_COLOR);
            }

            return mRgba;
        }

        return mRgba;
    }
}