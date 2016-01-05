package yv.recipe.utils;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgproc.Imgproc;

/**
 * Created by yaron on 31/12/15.
 */
public class ImageProccessingService {

    private final String TAG = "ImageProccessingService";

    private static ImageProccessingService instance;

    public static ImageProccessingService getInstance() {
        if(instance == null) {
            instance = new ImageProccessingService();
        }
        return instance;
    }

    private ImageProccessingService() {

    }

    public Bitmap convertToGrayScle(Bitmap bitmap) {
        Mat mat = Mat.zeros(bitmap.getHeight(), bitmap.getWidth(), CvType.CV_8UC1);
        Utils.bitmapToMat(bitmap, mat);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY);
        Utils.matToBitmap(mat, bitmap);
        return bitmap;
    }

    public void detectObjects(Bitmap bitmap) {
        Mat img = Mat.zeros(bitmap.getHeight(), bitmap.getWidth(), CvType.CV_8UC1);
        Utils.bitmapToMat(bitmap, img);
        MatOfKeyPoint mokp = new MatOfKeyPoint();
        FeatureDetector fd = FeatureDetector.create(FeatureDetector.MSER);
        fd.detect(img, mokp);

        Log.i(TAG, "Mat of key points = " + mokp.rows() + "x" + mokp.cols());
    }
}
