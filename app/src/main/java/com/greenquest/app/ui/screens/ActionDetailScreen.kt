@file:OptIn(ExperimentalMaterial3Api::class)

package com.greenquest.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.greenquest.app.ui.viewmodel.ActionDetailViewModel

@Composable
fun ActionDetailScreen(actionId: String, onBack: () -> Unit, onCompleted: () -> Unit) {
    val vm: ActionDetailViewModel = hiltViewModel()
    val ui = vm.uiState.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text(ui.value.title) }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text(ui.value.description)
            Button(onClick = {
                vm.complete { onCompleted() }
            }, modifier = Modifier.padding(top = 24.dp)) {
                Text("Mark as completed • +${ui.value.points} pts")
            }
        }
    }
}
