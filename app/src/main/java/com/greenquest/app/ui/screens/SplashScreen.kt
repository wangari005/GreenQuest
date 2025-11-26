package com.greenquest.app.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.greenquest.app.ui.viewmodel.SplashViewModel

@Composable
fun SplashScreen(onNavigate: (Boolean) -> Unit) {
    val vm: SplashViewModel = hiltViewModel()
    val done = vm.onboardingDone.collectAsState()

    LaunchedEffect(done.value) {
        // Navigate: true = onboarding done → Home, false → Onboarding
        onNavigate(done.value)
    }
}
