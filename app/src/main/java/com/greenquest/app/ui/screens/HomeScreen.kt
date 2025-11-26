@file:OptIn(ExperimentalMaterial3Api::class)

package com.greenquest.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.greenquest.app.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onOpenActions: () -> Unit,
    onOpenBadges: () -> Unit,
    onOpenChallenges: () -> Unit,
    onCreateCleanupEvent: () -> Unit,
    onOpenCommunity: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GreenQuest") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // User Progress Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "Your Progress",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Level and Points
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                "Level ${uiState.userLevel}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                "${uiState.userPoints} points",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Next level progress
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                "Next: ${(uiState.userLevel + 1) * 1000} pts",
                                style = MaterialTheme.typography.bodySmall
                            )
                            val progress = (uiState.userPoints % 1000) / 1000f
                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Stats
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        StatItem(
                            value = uiState.completedActions.size.toString(),
                            label = "Actions",
                            icon = Icons.Default.CheckCircle
                        )
                        StatItem(
                            value = "0", // Replace with actual badges count
                            label = "Badges",
                            icon = Icons.Default.EmojiEvents
                        )
                    }
                }
            }

            // Quick Actions
            Text(
                "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )

            // Action Buttons
            ActionButton(
                text = "Log an Eco-Action",
                icon = Icons.Default.Add,
                onClick = onOpenActions,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            ActionButton(
                text = "View Badges",
                icon = Icons.Default.EmojiEvents,
                onClick = onOpenBadges,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            ActionButton(
                text = "Weekly Challenges",
                icon = Icons.Default.Flag,
                onClick = onOpenChallenges,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            ActionButton(
                text = "Organize Cleanup",
                icon = Icons.Default.CleaningServices,
                onClick = onCreateCleanupEvent,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            ActionButton(
                text = "Community",
                icon = Icons.Default.People,
                onClick = onOpenCommunity,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .padding(bottom = 16.dp)
            )

            // Recent Activity
            if (uiState.recentActivity.isNotEmpty()) {
                Text(
                    "Recent Activity",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                )

                // Replace with your recent activity items
                uiState.recentActivity.take(3).forEach { activity ->
                    // Render activity item
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text)
    }
}