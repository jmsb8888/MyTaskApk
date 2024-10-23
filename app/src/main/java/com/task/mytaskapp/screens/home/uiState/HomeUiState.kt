package com.task.mytaskapp.screens.home.uiState

import com.task.mytaskapp.data.models.Task

data class HomeUiState(
    val taskList: List<Task> = emptyList()
)
