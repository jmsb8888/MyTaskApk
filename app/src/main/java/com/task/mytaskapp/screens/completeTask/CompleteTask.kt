package com.task.mytaskapp.screens.completeTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.mytaskapp.screens.completeTask.rv.RVCompleteTaskAdapter
import com.task.mytaskapp.R
import com.task.mytaskapp.data.models.Task
import com.task.mytaskapp.data.viewmodel.TaskViewModel
import com.task.mytaskapp.data.viewmodel.TaskViewModel.Companion.DETAILS
import kotlinx.coroutines.launch

class CompleteTask : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var completedTaskAdapter: RVCompleteTaskAdapter
    private lateinit var tasksRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_complete_task, container, false)
        initializeViewModel()
        setupRecyclerView(fragmentView)

        observeCompletedTasks()

        return fragmentView
    }

    private fun initializeViewModel() {
        taskViewModel = ViewModelProvider(requireActivity()).get(TaskViewModel::class.java)
    }

    private fun setupRecyclerView(view: View) {
        tasksRecyclerView = view.findViewById(R.id.recyclerViewCompletedTasks)
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun observeCompletedTasks() {
        lifecycleScope.launch {
            taskViewModel.taskUiStateFlow.collect { uiState ->
                val completedTasks = uiState.taskList.filter { it.isComplete }
                updateRecyclerView(completedTasks)
            }
        }
    }

    private fun updateRecyclerView(completedTasks: List<Task>) {
        if (completedTasks.isNotEmpty()) {
            completedTaskAdapter = RVCompleteTaskAdapter(
                completedTasks,
                { taskId -> taskViewModel.taskNotCompleted(taskId) },
                { task -> navigateToTaskDetails(task) }
            )
            tasksRecyclerView.adapter = completedTaskAdapter
        } else {
            tasksRecyclerView.adapter = null
        }
    }

    private fun navigateToTaskDetails(task: Task) {
        val taskBundle = Bundle().apply {
            putParcelable(DETAILS, task)
        }
        findNavController().navigate(R.id.action_completeTask_to_seeTask, taskBundle)
    }
}
