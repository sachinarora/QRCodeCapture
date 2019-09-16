package com.sacvintechno.qrcodecapture;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.snackbar.Snackbar;
import com.qrcode.BaseActivity;
import com.qrcode.CameraSource;
import com.qrcode.CameraSourcePreview;
import com.qrcode.QrcodeTracker;
import com.qrcode.QrcodeTrackerFactory;

import java.io.IOException;


import static android.Manifest.permission;


public final class QrcodeCaptureActivity extends BaseActivity
        implements QrcodeTracker.BarcodeGraphicTrackerCallback {

    public static final String BarcodeObject = "Barcode";
    public static final int SUCCESS = 1;
    private static final String TAG = "Barcode-reader";
    private static final int RC_HANDLE_GMS = 9001;
    public static BarcodeDetector barcodeDetector;
    CameraSourcePreview mPreview;
    private CameraSource mCameraSource;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.barcode_capture);
        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        requestCameraPermission();

    }

    @Override
    public void onDetectedQrCode(Barcode barcode) {
        if (barcode != null) {
            if(barcode.displayValue!=null )
            {
                Log.d("scanned data", barcode.displayValue);
                Intent intent = new Intent();
                intent.putExtra(BarcodeObject, barcode.displayValue);
                intent.putExtra("isSuccess", true );
                setResult(SUCCESS, intent);
                finish();
            }else
            {
               //
               // finish();
                Intent intent = new Intent();
                intent.putExtra("isSuccess", false );
                setResult(SUCCESS, intent);
                finish();

            }

        }
    }

    // Handles the requesting of the camera permission.
    private void requestCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestRunTimePermissions(this, new String[]{permission.CAMERA}, CAMERA_PERMISSION);
        }
    }

    @SuppressLint("InlinedApi")
    private void createCameraSource() {
        Context context = getApplicationContext();
        barcodeDetector = new BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        QrcodeTrackerFactory barcodeFactory = new QrcodeTrackerFactory(this);
        barcodeDetector.setProcessor(new MultiProcessor.Builder<>(barcodeFactory).build());
        if (!barcodeDetector.isOperational()) {
            Log.w(TAG, "Detector dependencies are not yet available.");
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, "low storage",
                        Toast.LENGTH_LONG).show();
                Log.w(TAG, "low storage");
            }
        }
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(metrics.widthPixels, metrics.heightPixels)
                .setRequestedFps(24.0f);

        // make sure that auto focus is an available option
        builder = builder.setFocusMode(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

        mCameraSource = builder
                .setFlashMode(null)
                .build();
    }

    // Restarts the camera
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    // Stops the camera
    @Override
    protected void onPause() {
        super.onPause();
        if (mPreview != null) {
            mPreview.stop();
        }
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull final String[] permissions,
                                           @NonNull int[] grantResults) {

        if (permissions.length == 1) {
            if (requestCode == CAMERA_PERMISSION
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestCameraPermission();

            } else {
                Snackbar.make(findViewById(android.R.id.content), "App needs camera permission to work", Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startAppPermissions();
                            }
                        }).show();

            }
        }
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }
        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }
}