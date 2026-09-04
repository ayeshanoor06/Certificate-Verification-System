package com.ayesha.certificateverificationsystem

import java.util.UUID

fun generateCertificateId(): String {
    val year = java.util.Calendar.getInstance()
        .get(java.util.Calendar.YEAR)

    val uniquePart = UUID.randomUUID()
        .toString()
        .replace("-", "")
        .take(8)
        .uppercase()

    return "CERT-$year-$uniquePart"
}