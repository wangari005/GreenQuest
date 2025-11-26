// app/src/main/java/com/greenquest/app/data/model/CleanupEvent.kt
package com.greenquest.app.data.model

data class CleanupEvent(
    val id: String = "",
    val organizerUid: String = "",
    val organizerEmail: String? = null,
    val organizerPhone: String = "",
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val dateTime: Long = 0,
    val maxParticipants: Int = 0,
    val currentParticipants: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
)