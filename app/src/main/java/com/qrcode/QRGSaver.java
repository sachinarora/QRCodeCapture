package com.qrcode;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class QRGSaver {

    public static boolean save(String saveLocation, String imageName, Bitmap bitmap, int imageFormat) {
        boolean success = false;
        String imageDetail = saveLocation + imageName + imgFormat(imageFormat);
        FileOutputStream outStream;
        File file = new File(saveLocation);
        if (!file.exists()) {
            file.mkdir();
        } else {
            Log.v("QRGSaver", "Folder Exists");
        }
        try {
            outStream = new FileOutputStream(imageDetail);
            bitmap.compress((Bitmap.CompressFormat) compressFormat(imageFormat), 100, outStream);
            outStream.flush();
            outStream.close();
            success = true;
        } catch (IOException e) {
            Log.d("QRGSaver", e.toString());
        }

        return success;
    }

    private static String imgFormat(int imageFormat) {
        return imageFormat == QRGContents.ImageType.IMAGE_PNG ? ".png" : ".jpg";
    }

    private static Comparable<? extends Comparable<? extends Comparable<?>>> compressFormat(int imageFormat) {
        return imageFormat == QRGContents.ImageType.IMAGE_PNG ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;
    }

}
