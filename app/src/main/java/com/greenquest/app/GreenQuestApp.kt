package com.greenquest.app

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.greenquest.app.data.local.DatabaseInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class GreenQuestApp : Application() {

    @Inject
    lateinit var databaseInitializer: DatabaseInitializer

    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Enable disk persistence for offline support
        Firebase.database.setPersistenceEnabled(true)

        // Initialize local database on app start
        initializeDatabase()
    }

    private fun initializeDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                databaseInitializer.initializeDatabase()
                Log.d(TAG, "Database initialized successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error initializing database", e)
            }
        }
    }

    companion object {
        private const val TAG = "GreenQuestApp"
    }
}