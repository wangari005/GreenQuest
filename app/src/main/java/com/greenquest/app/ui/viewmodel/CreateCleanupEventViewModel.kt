package com.greenquest.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenquest.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateCleanupEventUiState(
    val isSubmitting: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class CreateCleanupEventViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateCleanupEventUiState())
    val uiState: StateFlow<CreateCleanupEventUiState> = _uiState.asStateFlow()

    fun submitEvent(
        title: String,
        description: String,
        location: String,
        dateTime: Long,
        maxParticipants: Int,
        organizerPhone: String
    ) {
        when {
            title.isBlank() || description.isBlank() || location.isBlank() || organizerPhone.isBlank() -> {
                updateErrorState("Please fill in all required fields")
                return
            }
            maxParticipants < 1 -> {
                updateErrorState("Maximum participants must be at least 1")
                return
            }
            dateTime < System.currentTimeMillis() -> {
                updateErrorState("Event date must be in the future")
                return
            }
        }

        viewModelScope.launch {
            updateLoadingState(isLoading = true)

            try {
                val success = userRepository.submitCleanupEvent(
                    title = title.trim(),
                    description = description.trim(),
                    location = location.trim(),
                    dateTime = dateTime,
                    maxParticipants = maxParticipants,
                    organizerPhone = organizerPhone.trim()
                )

                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        isLoading = false,
                        isSuccess = success,
                        errorMessage = if (!success) "Failed to create event" else null
                    )
                }
            } catch (e: Exception) {
                updateErrorState(e.message ?: "An unexpected error occurred")
            }
        }
    }

    // For backward compatibility
    fun submitEvent(
        title: String,
        description: String,
        location: String,
        dateTime: Long,
        maxParticipants: Int
    ) {
        submitEvent(title, description, location, dateTime, maxParticipants, "")
    }

    fun resetState() {
        _uiState.value = CreateCleanupEventUiState()
    }

    private fun updateLoadingState(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading, isSubmitting = isLoading) }
    }

    private fun updateErrorState(message: String) {
        _uiState.update {
            it.copy(
                errorMessage = message,
                isSubmitting = false,
                isLoading = false
            )
        }
    }
}