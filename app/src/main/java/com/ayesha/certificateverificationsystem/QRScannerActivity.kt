package com.ayesha.certificateverificationsystem

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class QRScannerActivity : ComponentActivity() {

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result ->

        if (result.contents != null) {

            // Show what was scanned
            Toast.makeText(
                this,
                "Scanned: ${result.contents}",
                Toast.LENGTH_LONG
            ).show()

            // Send the certificate ID back
            val intent = Intent().apply {
                putExtra(
                    "certificateId",
                    result.contents
                )
            }

            setResult(
                Activity.RESULT_OK,
                intent
            )

            finish()

        } else {

            Toast.makeText(
                this,
                "No QR code scanned",
                Toast.LENGTH_SHORT
            ).show()

            setResult(
                Activity.RESULT_CANCELED
            )

            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startScanner()
    }

    private fun startScanner() {

        val options = ScanOptions().apply {

            // Message shown below scanner
            setPrompt(
                "Point the camera at the certificate QR code"
            )

            // Sound when QR is detected
            setBeepEnabled(true)

            // Allow portrait/landscape
            setOrientationLocked(false)

            // Only scan QR codes
            setDesiredBarcodeFormats(
                ScanOptions.QR_CODE
            )

            // Use rear camera
            setCameraId(0)


            setTimeout(30000)
        }

        barcodeLauncher.launch(options)
    }
}