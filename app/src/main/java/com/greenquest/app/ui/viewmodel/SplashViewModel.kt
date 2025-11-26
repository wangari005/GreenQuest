package com.greenquest.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenquest.app.data.prefs.ProfileDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefs: ProfileDataStore
) : ViewModel() {
    val onboardingDone: StateFlow<Boolean> = prefs.onboardingComplete
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)
}
