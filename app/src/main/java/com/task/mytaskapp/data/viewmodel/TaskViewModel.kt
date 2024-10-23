package com.task.mytaskapp.data.viewmodel

import androidx.lifecycle.ViewModel
import com.task.mytaskapp.data.models.Task
import com.task.mytaskapp.screens.home.uiState.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {
    private val _taskUiState = MutableStateFlow(HomeUiState())
    val taskUiStateFlow: StateFlow<HomeUiState> = _taskUiState.asStateFlow()
    private val taskList = mutableListOf<Task>()

    fun addTask(taskTitle: String) {
        val newTask = createTask(taskTitle)
        addTaskToList(newTask)
        refreshUiState()
    }

    fun completeTask(taskId: Int) {
        setTaskCompletionStatus(taskId, true)
        refreshUiState()
    }

    fun taskNotCompleted(taskId: Int) {
        setTaskCompletionStatus(taskId, false)
        refreshUiState()
    }

    private fun createTask(taskTitle: String): Task {
        return Task(
            id = taskList.size + 1,
            name = taskTitle,
            isComplete = false
        )
    }

    private fun addTaskToList(newTask: Task) {
        taskList.add(newTask)
    }

    private fun setTaskCompletionStatus(taskId: Int, isComplete: Boolean) {
        taskList.replaceAll { task ->
            if (task.id == taskId) task.copy(isComplete = isComplete) else task
        }
    }

    private fun refreshUiState() {
        _taskUiState.value = _taskUiState.value.copy(taskList = taskList.toList())
    }

    companion object {
        const val DETAILS = "DETAILS"
    }
}
