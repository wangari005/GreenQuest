package com.greenquest.app.data.local

import com.google.firebase.database.FirebaseDatabase
import com.greenquest.app.data.model.Action
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseInitializer @Inject constructor(
    private val database: FirebaseDatabase
) {
    suspend fun initializeDatabase() {
        val actionsRef = database.getReference("actions")
        
        // Sample actions data
        val sampleActions = listOf(
            Action(
                id = "action1",
                title = "Use a reusable water bottle",
                description = "Avoid single-use plastic bottles by using a reusable one",
                points = 10,
                category = "Waste Reduction",
                difficulty = "Easy",
                iconRes = "ic_water_bottle",
                isActive = true
            ),
            Action(
                id = "action2",
                title = "Take public transport",
                description = "Use buses, trains, or trams instead of driving",
                points = 20,
                category = "Transportation",
                difficulty = "Medium",
                iconRes = "ic_bus",
                isActive = true
            ),
            Action(
                id = "action3",
                title = "Plant a tree",
                description = "Plant a tree in your community or garden",
                points = 50,
                category = "Conservation",
                difficulty = "Medium",
                iconRes = "ic_tree",
                isActive = true
            )
        )

        // Add actions to database
        sampleActions.forEach { action ->
            actionsRef.child(action.id).setValue(action)
        }
    }
}
