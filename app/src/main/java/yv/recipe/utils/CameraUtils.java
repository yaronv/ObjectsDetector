package yv.recipe.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraUtils {

    private static CameraUtils instance = null;

    public static CameraUtils getInstance() {
        if(instance == null) {
            instance = new CameraUtils();
        }
        return instance;
    }

    private CameraUtils() {

    }

    public boolean isCameraSupported(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false){
            Toast.makeText(context, "This device does not have a camera.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    public void convertToGrayScale(Bitmap bitmap, ImageView viewImage) {
        bitmap = ImageProccessingService.getInstance().convertToGrayScle(bitmap);
        viewImage.setImageBitmap(bitmap);
    }
}
