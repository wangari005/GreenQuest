// app/src/main/java/com/greenquest/app/ui/viewmodel/ReportIssueViewModel.kt
package com.greenquest.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenquest.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportIssueViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    fun submitIssue(
        title: String,
        location: String,
        description: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // TODO: Implement issue submission
                // Example:
                // val success = userRepository.submitIssue(title, location, description)
                // if (success) {
                //     onSuccess()
                // } else {
                //     onError(Exception("Failed to submit issue"))
                // }
                onSuccess() // For now, just call success
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}