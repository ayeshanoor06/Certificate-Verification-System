package com.ayesha.certificateverificationsystem

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()

    // Save a new certificate to Firestore
    suspend fun saveCertificate(
        certificate: Certificate
    ): Boolean {

        return try {

            firestore
                .collection("certificates")
                .document(certificate.certificateId)
                .set(certificate)
                .await()

            true

        } catch (e: Exception) {

            Log.e(
                "FirestoreRepository",
                "Failed to save certificate",
                e
            )

            false
        }
    }

    // Get a certificate using its Certificate ID
    suspend fun getCertificate(
        certificateId: String
    ): Certificate? {

        return try {

            val document = firestore
                .collection("certificates")
                .document(certificateId)
                .get()
                .await()

            if (document.exists()) {

                document.toObject(
                    Certificate::class.java
                )

            } else {

                null
            }

        } catch (e: Exception) {

            Log.e(
                "FirestoreRepository",
                "Failed to verify certificate",
                e
            )

            null
        }
    }
}