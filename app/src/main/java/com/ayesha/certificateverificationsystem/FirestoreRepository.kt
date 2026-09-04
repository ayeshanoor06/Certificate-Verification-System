package com.ayesha.certificateverificationsystem

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun saveCertificate(certificate: Certificate): Boolean {
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
}