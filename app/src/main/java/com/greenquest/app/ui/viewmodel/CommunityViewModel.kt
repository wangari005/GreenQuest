package com.greenquest.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.greenquest.app.data.model.CleanupEvent
import com.greenquest.app.data.model.IssueReport
import com.greenquest.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val database: FirebaseDatabase
) : ViewModel() {

    private val _cleanupEvents = MutableStateFlow<List<CleanupEvent>>(emptyList())
    val cleanupEvents: StateFlow<List<CleanupEvent>> = _cleanupEvents

    private val _issues = MutableStateFlow<List<IssueReport>>(emptyList())
    val issues: StateFlow<List<IssueReport>> = _issues

    private val _uiState = MutableStateFlow(CommunityUiState())
    val uiState: StateFlow<CommunityUiState> = _uiState

    init {
        loadCleanupEvents()
        loadReportedIssues()
    }

    private fun loadCleanupEvents() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                database.getReference("cleanupEvents")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val eventsList = snapshot.children.mapNotNull {
                                it.getValue(CleanupEvent::class.java)
                            }
                            _cleanupEvents.value = eventsList.sortedByDescending { it.dateTime }
                            _uiState.update { it.copy(isLoading = false) }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            _uiState.update {
                                it.copy(
                                    error = error.message,
                                    isLoading = false
                                )
                            }
                        }
                    })
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadReportedIssues() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                database.getReference("reports")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val issuesList = snapshot.children.mapNotNull {
                                it.getValue(IssueReport::class.java)
                            }
                            _issues.value = issuesList.sortedByDescending { it.createdAt }
                            _uiState.update { it.copy(isLoading = false) }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            _uiState.update {
                                it.copy(
                                    error = error.message,
                                    isLoading = false
                                )
                            }
                        }
                    })
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun submitIssue(
        title: String,
        location: String,
        description: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val userId = userRepository.getCurrentUserId()
                    ?: return@launch onError("Not authenticated")

                val userEmail = userRepository.getCurrentUserData()
                    .first()?.email ?: ""

                val reportRef = database.getReference("reports").push()

                val report = IssueReport(
                    id = reportRef.key ?: return@launch onError("Failed to create report"),
                    reporterUid = userId,
                    reporterEmail = userEmail,
                    title = title,
                    locationDescription = location,
                    description = description,
                    createdAt = System.currentTimeMillis()
                )

                reportRef.setValue(report)
                    .addOnSuccessListener {
                        _uiState.update { it.copy(isLoading = false) }
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        _uiState.update { it.copy(isLoading = false) }
                        onError(e.message ?: "Failed to submit report")
                    }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                onError(e.message ?: "An error occurred")
            }
        }
    }
}

data class CommunityUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)