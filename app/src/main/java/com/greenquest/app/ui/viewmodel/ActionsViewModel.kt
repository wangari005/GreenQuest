package com.greenquest.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenquest.app.data.model.ActionItem
import com.greenquest.app.data.repo.ActionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ActionsViewModel @Inject constructor(
    private val repo: ActionRepository
) : ViewModel() {
    val actions: StateFlow<List<ActionItem>> = repo.observeActions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}
