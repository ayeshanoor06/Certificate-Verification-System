package com.ayesha.certificateverificationsystem

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

fun generateQRCode(
    certificateId: String,
    size: Int = 700
): Bitmap? {

    return try {
        val barcodeEncoder = BarcodeEncoder()

        barcodeEncoder.encodeBitmap(
            certificateId,
            BarcodeFormat.QR_CODE,
            size,
            size
        )
    } catch (e: Exception) {
        null
    }
}