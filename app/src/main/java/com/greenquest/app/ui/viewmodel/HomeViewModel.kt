package com.greenquest.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenquest.app.data.model.ProgressSummary
import com.greenquest.app.data.repo.ActionRepository
import com.greenquest.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: ActionRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    data class HomeUiState(
        val progress: ProgressSummary = ProgressSummary(
            totalPoints = 0,
            level = 1,
            nextLevelAt = 100,
            progressToNext = 0f
        ),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val defaultProgress = ProgressSummary(
        totalPoints = 0,
        level = 1,
        nextLevelAt = 100,
        progressToNext = 0f
    )

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadUserProgress()
        viewModelScope.launch {
            repo.seedIfEmpty()
        }
    }

    private fun loadUserProgress() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val userId = userRepository.getCurrentUserId()
                if (userId != null) {
                    userRepository.observeUserData(userId).collect { user ->
                        val totalPoints = user?.totalPoints ?: 0
                        val level = user?.level ?: 1
                        val nextLevelAt = calculateNextLevelPoints(level)
                        val progress = if (nextLevelAt > 0) {
                            (totalPoints - calculateLevelPoints(level)).toFloat() /
                                    (nextLevelAt - calculateLevelPoints(level))
                        } else 1f

                        _uiState.value = _uiState.value.copy(
                            progress = ProgressSummary(
                                totalPoints = totalPoints,
                                level = level,
                                nextLevelAt = nextLevelAt,
                                progressToNext = progress.coerceIn(0f, 1f)
                            ),
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        progress = defaultProgress,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    private fun calculateNextLevelPoints(currentLevel: Int): Int {
        return (100 * currentLevel * 1.5).toInt()
    }

    private fun calculateLevelPoints(level: Int): Int {
        if (level <= 1) return 0
        return (100 * (level - 1) * 1.5).toInt()
    }
}