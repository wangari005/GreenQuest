package com.greenquest.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenquest.app.data.prefs.ProfileDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val prefs: ProfileDataStore
) : ViewModel() {

    fun completeOnboarding(name: String) {
        viewModelScope.launch {
            prefs.setProfileName(name)
            prefs.setOnboardingComplete(true)
        }
    }
}
