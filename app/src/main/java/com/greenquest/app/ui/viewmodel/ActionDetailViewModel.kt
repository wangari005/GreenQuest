package com.greenquest.app.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenquest.app.data.model.ActionItem
import com.greenquest.app.data.repo.ActionRepository
import com.greenquest.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActionDetailViewModel @Inject constructor(
    private val repo: ActionRepository,
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    data class UiState(
        val id: String = "",
        val title: String = "Action",
        val description: String = "",
        val points: Int = 0,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val actionId: String = savedStateHandle.get<String>("actionId").orEmpty()

    init {
        loadAction()
    }

    private fun loadAction() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val item = repo.getAction(actionId)
                if (item != null) {
                    _uiState.value = _uiState.value.copy(
                        id = item.id,
                        title = item.title,
                        description = item.description,
                        points = item.points,
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "Action not found",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to load action",
                    isLoading = false
                )
            }
        }
    }

    fun complete(onComplete: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val points = _uiState.value.points
                repo.completeAction(actionId)

                if (points > 0) {
                    userRepository.addPoints(points)
                }

                onComplete()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to complete action",
                    isLoading = false
                )
            }
        }
    }
}