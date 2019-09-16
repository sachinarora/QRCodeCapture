package com.sacvintechno.qrcodecapture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_start_scan.setOnClickListener {
            startActivityForResult(Intent(this, QrcodeCaptureActivity::class.java), 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == 1) {
            Log.d("scanned boolean", data!!.getBooleanExtra("isSuccess", false).toString() + "")
            Log.d("scanned value", data.getStringExtra("Barcode"))
            Toast.makeText(this,data.getStringExtra("Barcode"), Toast.LENGTH_LONG).show()
        }
    }

}
