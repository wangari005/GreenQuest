@file:OptIn(ExperimentalMaterial3Api::class)

package com.greenquest.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.greenquest.app.ui.viewmodel.ActionsViewModel
import androidx.compose.material3.Divider
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults

@Composable
fun ActionsScreen(onOpenDetail: (String) -> Unit) {
    val vm: ActionsViewModel = hiltViewModel()
    val actions = vm.actions.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text("Eco Actions") }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            actions.value.forEachIndexed { index, item ->
                ListItem(
                    headlineContent = { Text(item.title) },
                    supportingContent = {
                        Column {
                            Text("Earn ${item.points} points")
                            AssistChip(
                                onClick = {},
                                label = { Text(item.category) },
                                enabled = false,
                                colors = AssistChipDefaults.assistChipColors()
                            )
                        }
                    },
                    modifier = Modifier.clickable { onOpenDetail(item.id) }
                )
                if (index < actions.value.lastIndex) Divider()
            }
        }
    }
}
