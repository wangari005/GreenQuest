package com.greenquest.app.data.model

data class IssueReport(
    val id: String = "",
    val reporterUid: String = "",
    val reporterEmail: String? = null,
    val title: String = "",
    val locationDescription: String = "",
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
