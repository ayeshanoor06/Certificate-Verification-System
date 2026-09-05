package com.ayesha.certificateverificationsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CertificateVerificationScreen(
    certificate: Certificate?,
    certificateId: String,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text(
            text = "Certificate Verification",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        if (certificate != null) {

            Text(
                text = "✓ Certificate Verified",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Student Name",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = certificate.studentName,
                        fontSize = 18.sp
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = "Internship Title",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = certificate.internshipTitle,
                        fontSize = 18.sp
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = "Issue Date",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = certificate.issueDate,
                        fontSize = 18.sp
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = "Certificate ID",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = certificateId,
                        fontSize = 16.sp
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = if (certificate.valid) {
                            "Status: VALID"
                        } else {
                            "Status: INVALID"
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (certificate.valid) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )
                }
            }

        } else {

            Text(
                text = "✕ Certificate Not Found",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "The scanned certificate could not be verified.",
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Certificate ID: $certificateId",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Back")
        }
    }
}