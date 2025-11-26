
package com.greenquest.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenquest.app.data.model.Challenge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChallengesUiState(
    val challenges: List<Challenge> = emptyList(),
    val completedTasks: Int = 0,
    val totalTasks: Int = 0,
    val rewardPoints: Int = 100,
    val canClaimReward: Boolean = false,
    val isClaiming: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ChallengesViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ChallengesUiState())
    val uiState: StateFlow<ChallengesUiState> = _uiState

    init {
        loadChallenges()
    }

    private fun loadChallenges() {
        val list = listOf(
            Challenge("1", "Pick 5 pieces of litter", "Clean your neighborhood", 20),
            Challenge("2", "Plant a tree", "Help restore nature", 50),
            Challenge("3", "Recycle plastic", "Sort your plastic waste", 30),
        )

        _uiState.value = _uiState.value.copy(
            challenges = list,
            totalTasks = list.size
        )
    }

    fun completeChallenge(id: String) {
        val updated = _uiState.value.challenges.map {
            if (it.id == id) it.copy(completed = true) else it
        }

        val completedCount = updated.count { it.completed }

        _uiState.value = _uiState.value.copy(
            challenges = updated,
            completedTasks = completedCount,
            canClaimReward = (completedCount == updated.size) // all tasks done
        )
    }

    fun claimReward() {
        if (!_uiState.value.canClaimReward) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isClaiming = true)
            delay(1200)

            _uiState.value = _uiState.value.copy(
                isClaiming = false,
                error = null,
                rewardPoints = 0
            )
        }
    }
}
