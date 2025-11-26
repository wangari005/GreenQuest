package com.greenquest.app.data.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var id: String = "",
    var displayName: String = "Eco Warrior",
    var email: String? = null,
    var photoUrl: String? = null,
    var totalPoints: Int = 0,
    var level: Int = 1,
    var completedActions: Map<String, Long> = emptyMap(),
    var weeklyChallenges: List<String> = emptyList(),
    var badges: List<String> = emptyList()
) {
    // Required empty constructor for Firebase
    constructor() : this("", "Eco Warrior", null, null, 0, 1, emptyMap(), emptyList(), emptyList())

    @get:Exclude
    val experiencePoints: Int
        get() = totalPoints % 1000

    @get:Exclude
    val levelProgress: Float
        get() = (totalPoints % 1000) / 1000f
}