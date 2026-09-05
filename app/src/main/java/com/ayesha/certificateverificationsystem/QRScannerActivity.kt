package com.ayesha.certificateverificationsystem

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QRScannerActivity : ComponentActivity() {

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result ->

        if (result.contents != null) {

            val certificateId = result.contents.trim()

            verifyCertificate(certificateId)

        } else {

            Toast.makeText(
                this,
                "No QR code scanned",
                Toast.LENGTH_SHORT
            ).show()

            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startScanner()
    }

    private fun startScanner() {

        val options = ScanOptions().apply {

            setPrompt(
                "Point the camera at the certificate QR code"
            )

            setBeepEnabled(true)

            setOrientationLocked(false)

            setDesiredBarcodeFormats(
                ScanOptions.QR_CODE
            )

            setCameraId(0)

            setTimeout(30000)
        }

        barcodeLauncher.launch(options)
    }

    private fun verifyCertificate(
        certificateId: String
    ) {

        Toast.makeText(
            this,
            "Checking certificate...",
            Toast.LENGTH_SHORT
        ).show()

        CoroutineScope(Dispatchers.IO).launch {

            val repository = FirestoreRepository()

            val certificate =
                repository.getCertificate(certificateId)

            withContext(Dispatchers.Main) {

                if (certificate != null) {

                    val intent = Intent(
                        this@QRScannerActivity,
                        VerificationResultActivity::class.java
                    )

                    intent.putExtra(
                        "certificateId",
                        certificateId
                    )

                    intent.putExtra(
                        "studentName",
                        certificate.studentName
                    )

                    intent.putExtra(
                        "internshipTitle",
                        certificate.internshipTitle
                    )

                    intent.putExtra(
                        "issueDate",
                        certificate.issueDate
                    )

                    intent.putExtra(
                        "valid",
                        certificate.valid
                    )

                    startActivity(intent)

                    finish()

                } else {

                    val intent = Intent(
                        this@QRScannerActivity,
                        VerificationResultActivity::class.java
                    )

                    intent.putExtra(
                        "certificateId",
                        certificateId
                    )

                    intent.putExtra(
                        "valid",
                        false
                    )

                    startActivity(intent)

                    finish()
                }
            }
        }
    }
}