package com.task.mytaskapp.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.mytaskapp.data.bd.TaskDao
import com.task.mytaskapp.data.models.Task
import com.task.mytaskapp.screens.home.uiState.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskDao: TaskDao
): ViewModel() {
    private val _taskUiState = MutableStateFlow(HomeUiState())
    val taskUiStateFlow: StateFlow<HomeUiState> = _taskUiState.asStateFlow()
    private val taskList = mutableListOf<Task>()

    init {
        viewModelScope.launch {
            taskList.clear()
            taskList.addAll(taskDao.getAllTasks())
            _taskUiState.value = _taskUiState.value.copy(taskList = taskList.toList())
        }
    }

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
    fun taskDelete(taskId: Int) {
        val taskToDelete = taskList.find { it.id == taskId }
        if (taskToDelete != null) {
            taskList.remove(taskToDelete)
            deleteMyTask(taskToDelete)
            refreshUiState()
        } else {
            Log.e("TaskViewModel", "Task with ID $taskId not found")
        }
    }


    private fun createTask(taskTitle: String): Task {
        return Task(
            name = taskTitle,
            isComplete = false
        )
    }

    private fun addTaskToList(newTask: Task) {
        taskList.add(newTask)
        saveMyTask(newTask)
    }

    private fun setTaskCompletionStatus(taskId: Int, isComplete: Boolean) {
        taskList.replaceAll { task ->
            if (task.id == taskId) task.copy(isComplete = isComplete) else task
        }
        updateMyTask(taskList.find { it.id == taskId }!!)
    }

    private fun refreshUiState() {
        _taskUiState.value = _taskUiState.value.copy(taskList = taskList.toList())
    }
     private fun saveMyTask(task: Task){
         viewModelScope.launch {
             taskDao.insertTask(task)
         }
     }
    private fun deleteMyTask(task: Task){
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }
    private fun updateMyTask(task: Task){
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
    }

}
