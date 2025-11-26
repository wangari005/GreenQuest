package com.greenquest.app.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Action(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var points: Int = 0,
    var category: String = "",
    var difficulty: String = "",
    var iconRes: String = "",
    var isActive: Boolean = true
) {
    // Required empty constructor for Firebase
    constructor() : this("", "", "", 0, "", "", "", true)
}
