package com.greenquest.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenquest.app.data.model.BadgeStatus
import com.greenquest.app.data.repo.ActionRepository
import com.greenquest.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BadgesViewModel @Inject constructor(
    private val repo: ActionRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    data class BadgesUiState(
        val badges: List<BadgeStatus> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(BadgesUiState())
    val uiState: StateFlow<BadgesUiState> = _uiState

    init {
        loadBadges()
    }

    private fun loadBadges() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val userId = userRepository.getCurrentUserId()
                if (userId != null) {
                    userRepository.observeUserData(userId)
                        .collect { user ->
                            val userPoints = user?.totalPoints ?: 0
                            val badges = repo.observeBadges().first()

                            val updatedBadges = badges.map { status ->
                                if (status.badge.code == "POINTS_100") {
                                    status.copy(earned = userPoints >= 100)
                                } else {
                                    status
                                }
                            }

                            _uiState.value = _uiState.value.copy(
                                badges = updatedBadges,
                                isLoading = false
                            )
                        }
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "User not authenticated",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to load badges",
                    isLoading = false
                )
            }
        }
    }
}