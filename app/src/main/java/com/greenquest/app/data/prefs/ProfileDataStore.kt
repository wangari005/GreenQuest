package com.greenquest.app.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "profile_prefs")

class ProfileDataStore(private val context: Context) {
    private object Keys {
        val NAME = stringPreferencesKey("profile_name")
        val ONBOARDING_DONE = booleanPreferencesKey("onboarding_complete")
    }

    val profileName: Flow<String> = context.dataStore.data.map { it[Keys.NAME] ?: "" }
    val onboardingComplete: Flow<Boolean> = context.dataStore.data.map { it[Keys.ONBOARDING_DONE] ?: false }

    suspend fun setProfileName(name: String) {
        context.dataStore.edit { it[Keys.NAME] = name }
    }

    suspend fun setOnboardingComplete(done: Boolean) {
        context.dataStore.edit { it[Keys.ONBOARDING_DONE] = done }
    }
}
