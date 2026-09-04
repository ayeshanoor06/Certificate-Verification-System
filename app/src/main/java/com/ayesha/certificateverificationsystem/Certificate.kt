package com.ayesha.certificateverificationsystem

data class Certificate(
    val certificateId: String = "",
    val studentName: String = "",
    val internshipTitle: String = "",
    val issueDate: String = "",
    val valid: Boolean = true
)