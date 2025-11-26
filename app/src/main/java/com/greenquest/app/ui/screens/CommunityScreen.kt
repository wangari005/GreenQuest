// app/src/main/java/com/greenquest/app/ui/screens/CommunityScreen.kt
package com.greenquest.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.greenquest.app.data.model.CleanupEvent
import com.greenquest.app.data.model.IssueReport
import com.greenquest.app.ui.viewmodel.CommunityViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun CommunityScreen(
    onBack: () -> Unit,
    onReportIssue: () -> Unit,
    viewModel: CommunityViewModel = hiltViewModel()
) {
    val events by viewModel.cleanupEvents.collectAsState()
    val issues by viewModel.issues.collectAsState()
    val pagerState = rememberPagerState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Community") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onReportIssue) {
                Icon(Icons.Default.Report, contentDescription = "Report Issue")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                Tab(
                    text = { Text("Cleanup Events") },
                    selected = pagerState.currentPage == 0,
                    onClick = { /* Handle tab click */ }
                )
                Tab(
                    text = { Text("Reported Issues") },
                    selected = pagerState.currentPage == 1,
                    onClick = { /* Handle tab click */ }
                )
            }

            HorizontalPager(
                count = 2,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> CleanupEventsList(events = events)
                    1 -> ReportedIssuesList(issues = issues)
                }
            }
        }
    }
}

@Composable
private fun CleanupEventsList(events: List<CleanupEvent>) {
    if (events.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No cleanup events yet. Be the first to organize one!")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(events) { event ->
                CleanupEventCard(event = event)
            }
        }
    }
}

@Composable
private fun ReportedIssuesList(issues: List<IssueReport>) {
    if (issues.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No reported issues yet.")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(issues) { issue ->
                IssueReportCard(issue = issue)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CleanupEventCard(event: CleanupEvent) {
    Card(
        onClick = { /* Handle click */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = event.title, style = MaterialTheme.typography.titleLarge)
            Text(text = event.description)
            Text(text = "📍 ${event.location}")
            Text(text = "📅 ${java.text.SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", java.util.Locale.getDefault()).format(java.util.Date(event.dateTime))}")
            event.organizerPhone?.let { phone ->
                Text(text = "📞 $phone")
            }
            Text(text = "👥 ${event.currentParticipants.size}/${event.maxParticipants} participants")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IssueReportCard(issue: IssueReport) {
    Card(
        onClick = { /* Handle click */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = issue.title, style = MaterialTheme.typography.titleLarge)
            Text(text = issue.description)
            Text(text = "📍 ${issue.locationDescription}")
            Text(text = "📅 ${java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(java.util.Date(issue.createdAt))}")
        }
    }
}