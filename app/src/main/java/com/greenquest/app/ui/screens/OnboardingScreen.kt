package com.greenquest.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.greenquest.app.ui.viewmodel.OnboardingViewModel

@Composable
fun OnboardingScreen(onContinue: () -> Unit) {
    val vm: OnboardingViewModel = hiltViewModel()
    var name by remember { mutableStateOf("") }
    val valid = name.trim().length in 2..20

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to GreenQuest", style = MaterialTheme.typography.headlineLarge)
        Text(
            "Rebuild a green planet by completing eco‑friendly actions.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            singleLine = true,
            label = { Text("Your name") }
        )
        Button(onClick = onContinue) {
            Text("Continue")
        }
        Button(
            onClick = {
                if (valid) {
                    vm.completeOnboarding(name.trim())
                    onContinue()
                }
            },
            enabled = valid,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Save name and start")
        }
    }
}
