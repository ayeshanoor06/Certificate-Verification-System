package com.ayesha.certificateverificationsystem

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayesha.certificateverificationsystem.ui.theme.CertificateVerificationSystemTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CertificateVerificationSystemTheme {
                CertificateVerificationApp()
            }
        }
    }
}

@Composable
fun CertificateVerificationApp() {

    // Context used to open the QR scanner
    val context = LocalContext.current

    // Certificate form fields
    var studentName by remember {
        mutableStateOf("")
    }

    var internshipTitle by remember {
        mutableStateOf("")
    }

    var issueDate by remember {
        mutableStateOf("")
    }

    // Generated certificate information
    var certificateId by remember {
        mutableStateOf("")
    }

    var qrBitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    // Loading state
    var isSaving by remember {
        mutableStateOf(false)
    }

    // Status message
    var message by remember {
        mutableStateOf("")
    }

    // Firestore repository
    val repository = remember {
        FirestoreRepository()
    }

    // Coroutine scope
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            // Screen title
            Text(
                text = "Certificate Verification System",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Generate and verify internship certificates",
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Student name
            OutlinedTextField(
                value = studentName,
                onValueChange = {
                    studentName = it
                },
                label = {
                    Text("Student Name")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Internship title
            OutlinedTextField(
                value = internshipTitle,
                onValueChange = {
                    internshipTitle = it
                },
                label = {
                    Text("Internship Title")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Issue date
            OutlinedTextField(
                value = issueDate,
                onValueChange = {
                    issueDate = it
                },
                label = {
                    Text("Issue Date")
                },
                placeholder = {
                    Text("YYYY-MM-DD")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Generate certificate button
            Button(
                onClick = {

                    // Check whether all fields are filled
                    if (
                        studentName.isBlank() ||
                        internshipTitle.isBlank() ||
                        issueDate.isBlank()
                    ) {

                        message = "Please fill all fields."

                        return@Button
                    }

                    // Start loading
                    isSaving = true

                    message = ""

                    // Generate a unique certificate ID
                    val newCertificateId =
                        generateCertificateId()

                    // Create certificate object
                    val certificate = Certificate(
                        certificateId = newCertificateId,
                        studentName = studentName.trim(),
                        internshipTitle = internshipTitle.trim(),
                        issueDate = issueDate.trim(),
                        valid = true
                    )

                    // Save certificate to Firestore
                    scope.launch {

                        val saved =
                            repository.saveCertificate(certificate)

                        isSaving = false

                        if (saved) {

                            // Store generated ID
                            certificateId = newCertificateId

                            // Generate QR using certificate ID
                            qrBitmap =
                                generateQRCode(newCertificateId)

                            message =
                                "Certificate generated successfully."

                        } else {

                            message =
                                "Failed to save certificate."
                        }
                    }
                },
                enabled = !isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {

                if (isSaving) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )

                } else {

                    Text(
                        text = "Generate Certificate"
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Scan QR button
            Button(
                onClick = {

                    val intent = Intent(
                        context,
                        QRScannerActivity::class.java
                    )

                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Scan QR Code"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Status message
            if (message.isNotEmpty()) {

                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }

            // Show certificate ID after generation
            if (certificateId.isNotEmpty()) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Certificate ID",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = certificateId,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }

            // Show QR code
            qrBitmap?.let { bitmap ->

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Certificate QR Code",
                    modifier = Modifier.size(280.dp)
                )
            }
        }
    }
}