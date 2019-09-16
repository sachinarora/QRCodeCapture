package com.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.sacvintechno.qrcodecapture.R;


public class BaseActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {


    protected static final int EXTERNAL_STORAGE_PERMISSION = 10;
    protected static final int CAMERA_PERMISSION = 20;



    protected void requestRunTimePermissions(final Activity activity, final String[] permissions,
                                             final int customPermissionConstant) {
        if (permissions.length == 1) {

            if (customPermissionConstant == EXTERNAL_STORAGE_PERMISSION) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {

                    Snackbar.make(findViewById(android.R.id.content),
                            "App needs permission to work", Snackbar.LENGTH_INDEFINITE).
                            setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(activity, permissions, customPermissionConstant);
                                }
                            }).show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{permissions[0]}, customPermissionConstant);
                }
            }

            if (customPermissionConstant == CAMERA_PERMISSION) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {

                    Snackbar.make(findViewById(android.R.id.content), "App needs permission to work", Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(activity, permissions, customPermissionConstant);
                                }
                            }).show();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{permissions[0]}, customPermissionConstant);
                }
            }
        }
    }

    protected void startAppPermissions() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}