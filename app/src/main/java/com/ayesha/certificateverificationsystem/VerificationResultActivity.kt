package com.ayesha.certificateverificationsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ayesha.certificateverificationsystem.ui.theme.CertificateVerificationSystemTheme

class VerificationResultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val certificateId =
            intent.getStringExtra("certificateId") ?: ""

        val studentName =
            intent.getStringExtra("studentName") ?: ""

        val internshipTitle =
            intent.getStringExtra("internshipTitle") ?: ""

        val issueDate =
            intent.getStringExtra("issueDate") ?: ""

        val valid =
            intent.getBooleanExtra("valid", false)

        val certificate =
            if (studentName.isNotEmpty()) {

                Certificate(
                    certificateId = certificateId,
                    studentName = studentName,
                    internshipTitle = internshipTitle,
                    issueDate = issueDate,
                    valid = valid
                )

            } else {

                null
            }

        setContent {

            CertificateVerificationSystemTheme {

                CertificateVerificationScreen(
                    certificate = certificate,
                    certificateId = certificateId,
                    onBack = {
                        finish()
                    }
                )
            }
        }
    }
}