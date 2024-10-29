package com.task.mytaskapp.screens.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.mytaskapp.R
import com.task.mytaskapp.data.models.Task
import com.task.mytaskapp.data.viewmodel.TaskViewModel
import com.task.mytaskapp.screens.home.rv.RVHomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeTask : Fragment() {
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: RVHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_task, container, false)
        setupUiComponents(view)
        initializeViewModel()

        setupRecyclerView(view)
        observeUiState()
        return view
    }

    private fun initializeViewModel() {
        taskViewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)
    }

    private fun setupUiComponents(view: View) {
        val editTextTaskInput: EditText = view.findViewById(R.id.etTaskInput)
        val buttonSaveTask: Button = view.findViewById(R.id.btnSave)
        val buttonCompleteTasks: Button = view.findViewById(R.id.btnTaskCompletes)

        buttonSaveTask.setOnClickListener {
            handleTaskCreation(editTextTaskInput)
        }

        buttonCompleteTasks.setOnClickListener {
            navigateToCompletedTasks()
        }
    }

    private fun setupRecyclerView(view: View) {
        val recyclerViewTasks: RecyclerView = view.findViewById(R.id.rvTask)
        taskAdapter = RVHomeAdapter(
            mutableListOf(),
            { task -> taskViewModel.completeTask(task.id) },
            { task -> taskViewModel.taskDelete(task.id) },
            { task -> navigateToTaskDetails(task) }
        )
        recyclerViewTasks.adapter = taskAdapter
        recyclerViewTasks.layoutManager = LinearLayoutManager(context)
    }

    private fun handleTaskCreation(editText: EditText) {
        val taskTitle = editText.text.toString()
        if (taskTitle.isNotEmpty()) {
            taskViewModel.addTask(taskTitle)
            editText.text.clear()
        }
    }

    private fun navigateToCompletedTasks() {
        findNavController().navigate(R.id.action_homeTask_to_completeTask)
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            taskViewModel.taskUiStateFlow.collect { uiState ->
                updateTaskList(uiState.taskList)
            }
        }
    }

    private fun updateTaskList(tasks: List<Task>) {
        val incompleteTasks = tasks.filter { !it.isComplete }
        taskAdapter.refreshTasks(incompleteTasks)
    }

    private fun navigateToTaskDetails(task: Task) {
        val action = HomeTaskDirections.actionHomeTaskToSeeTask(
            task.id,
            task.name,
            task.isComplete
        )
        findNavController().navigate(action)
    }

}