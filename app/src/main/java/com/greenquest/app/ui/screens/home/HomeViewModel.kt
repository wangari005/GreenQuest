package com.greenquest.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenquest.app.data.model.Action
import com.greenquest.app.data.repository.ActionRepository
import com.greenquest.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val actions: List<Action> = emptyList(),
    val completedActionIds: Set<String> = emptySet(),
    val userPoints: Int = 0,
    val userLevel: Int = 1,
    val recentActivity: List<ActivityItem> = emptyList(),
    val totalActions: Int = 0,
    val completedActions: Int = 0
)

data class ActivityItem(
    val id: String,
    val title: String,
    val points: Int,
    val timestamp: Long,
    val type: ActivityType
)

enum class ActivityType {
    ACTION_COMPLETED,
    BADGE_EARNED,
    LEVEL_UP
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val actionRepository: ActionRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                val userId = userRepository.getCurrentUserId()
                if (userId == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "User not authenticated"
                    )
                    return@launch
                }

                // Load data in parallel
                val actions = actionRepository.getActions()
                val user = userRepository.getCurrentUserData()
                val completedActions = user?.completedActions?.keys ?: emptySet()

                // Calculate level based on points
                val points = user?.totalPoints ?: 0
                val level = calculateLevel(points)

                // Generate recent activity (simplified example)
                val recentActivity = buildList {
                    // Add completed actions
                    actions
                        .filter { it.id in completedActions }
                        .take(3)
                        .forEach { action ->
                            add(
                                ActivityItem(
                                    id = action.id,
                                    title = "Completed: ${action.title}",
                                    points = action.points,
                                    timestamp = System.currentTimeMillis() - (0..1000).random(),
                                    type = ActivityType.ACTION_COMPLETED
                                )
                            )
                        }

                    // Add level up if applicable
                    if (level > 1) {
                        add(
                            ActivityItem(
                                id = "level_$level",
                                title = "Reached Level $level!",
                                points = 0,
                                timestamp = System.currentTimeMillis() - (1000..5000).random(),
                                type = ActivityType.LEVEL_UP
                            )
                        )
                    }
                }.sortedByDescending { it.timestamp }

                _uiState.value = HomeUiState(
                    isLoading = false,
                    actions = actions,
                    completedActionIds = completedActions,
                    userPoints = points,
                    userLevel = level,
                    recentActivity = recentActivity.take(3),
                    totalActions = actions.size,
                    completedActions = completedActions.size
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load data"
                )
            }
        }
    }

    fun completeAction(action: Action) {
        viewModelScope.launch {
            try {
                val userId = userRepository.getCurrentUserId() ?: return@launch

                _uiState.value = _uiState.value.copy(isLoading = true)

                val success = actionRepository.completeAction(
                    userId = userId,
                    actionId = action.id,
                    points = action.points
                )

                if (success) {
                    // Update local state
                    val newPoints = _uiState.value.userPoints + action.points
                    val newLevel = calculateLevel(newPoints)
                    val newCompletedActions = _uiState.value.completedActionIds + action.id

                    _uiState.value = _uiState.value.copy(
                        completedActionIds = newCompletedActions,
                        userPoints = newPoints,
                        userLevel = newLevel,
                        completedActions = newCompletedActions.size,
                        recentActivity = listOf(
                            ActivityItem(
                                id = "action_${action.id}",
                                title = "Completed: ${action.title}",
                                points = action.points,
                                timestamp = System.currentTimeMillis(),
                                type = ActivityType.ACTION_COMPLETED
                            )
                        ) + _uiState.value.recentActivity.take(2)
                    )

                    // Check for level up
                    if (newLevel > _uiState.value.userLevel) {
                        _uiState.value = _uiState.value.copy(
                            recentActivity = listOf(
                                ActivityItem(
                                    id = "level_$newLevel",
                                    title = "Level Up! Reached Level $newLevel",
                                    points = 0,
                                    timestamp = System.currentTimeMillis() + 1,
                                    type = ActivityType.LEVEL_UP
                                )
                            ) + _uiState.value.recentActivity
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "Failed to complete action"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "An error occurred"
                )
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun refresh() {
        loadData()
    }

    fun errorShown() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun calculateLevel(points: Int): Int {
        return (points / 1000) + 1 // 1000 points per level
    }
}