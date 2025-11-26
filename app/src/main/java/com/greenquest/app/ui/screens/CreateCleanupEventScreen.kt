package com.greenquest.app.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.greenquest.app.ui.viewmodel.CreateCleanupEventViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCleanupEventScreen(
    onBack: () -> Unit,
    viewModel: CreateCleanupEventViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var maxParticipants by remember { mutableStateOf("10") }
    var organizerPhone by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Date?>(null) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Cleanup Event") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Event Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Event Title *") },
                modifier = Modifier.fillMaxWidth()
            )

            // Event Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description *") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5
            )

            // Location
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location *") },
                modifier = Modifier.fillMaxWidth()
            )

            // Organizer's Phone Number
            OutlinedTextField(
                value = organizerPhone,
                onValueChange = { newValue ->
                    // Allow only numbers and optional leading +
                    if (newValue.isEmpty() || newValue.matches(Regex("^\\+?[0-9]*$"))) {
                        organizerPhone = newValue
                    }
                },
                label = { Text("Your Phone Number *") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = "Phone",
                        modifier = Modifier.size(20.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g., +1234567890") }
            )

            // Date and Time Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Date Picker
                OutlinedButton(
                    onClick = {
                        val datePicker = DatePickerDialog(
                            context,
                            { _: DatePicker, year: Int, month: Int, day: Int ->
                                calendar.set(year, month, day)
                                selectedDate = calendar.time
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
                        datePicker.show()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = "Select date",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(selectedDate?.let { dateFormat.format(it) } ?: "Select Date *")
                }

                // Time Picker
                OutlinedButton(
                    onClick = {
                        val timePicker = TimePickerDialog(
                            context,
                            { _: TimePicker, hour: Int, minute: Int ->
                                calendar.set(Calendar.HOUR_OF_DAY, hour)
                                calendar.set(Calendar.MINUTE, minute)
                                selectedDate = calendar.time
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            false
                        )
                        timePicker.show()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = "Select time",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(selectedDate?.let { timeFormat.format(it) } ?: "Select Time *")
                }
            }

            // Maximum Participants
            OutlinedTextField(
                value = maxParticipants,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || (newValue.toIntOrNull() != null && newValue.toInt() > 0)) {
                        maxParticipants = newValue
                    }
                },
                label = { Text("Maximum Participants *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g., 10") }
            )

            // Create Event Button
            Button(
                onClick = {
                    val participants = maxParticipants.toIntOrNull() ?: 1
                    val eventTime = selectedDate?.time ?: return@Button

                    viewModel.submitEvent(
                        title = title.trim(),
                        description = description.trim(),
                        location = location.trim(),
                        dateTime = eventTime,
                        maxParticipants = participants,
                        organizerPhone = organizerPhone.trim()
                    )
                },
                enabled = title.isNotBlank() &&
                        description.isNotBlank() &&
                        location.isNotBlank() &&
                        organizerPhone.isNotBlank() &&
                        selectedDate != null &&
                        maxParticipants.isNotBlank() &&
                        maxParticipants.toIntOrNull()?.let { it > 0 } == true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                if (uiState.isSubmitting) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Create Event")
                }
            }

            // Error message
            uiState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}