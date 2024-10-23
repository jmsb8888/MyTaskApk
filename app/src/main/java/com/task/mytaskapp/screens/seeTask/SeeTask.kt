package com.task.mytaskapp.screens.seeTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.task.mytaskapp.R
import com.task.mytaskapp.data.models.Task
import com.task.mytaskapp.data.viewmodel.TaskViewModel
import com.task.mytaskapp.data.viewmodel.TaskViewModel.Companion.DETAILS

class SeeTask : Fragment() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var currentTask: Task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_see_task, container, false)
        initializeViewModel()
        retrieveTaskFromArguments()
        setupUi(view)
        return view
    }

    private fun initializeViewModel() {
        taskViewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
    }

    private fun retrieveTaskFromArguments() {
        currentTask = arguments?.let {
            it.getParcelable(DETAILS)
        }!!
    }


    private fun setupUi(view: View) {
        val taskNameTextView: TextView = view.findViewById(R.id.tvName)
        val taskStatusTextView: TextView = view.findViewById(R.id.tvIsComplete)
        val taskCompleteCheckBox: CheckBox = view.findViewById(R.id.cbSelectTask)
        val taskId: TextView = view.findViewById(R.id.tvId)



        displayTaskDetails(taskNameTextView, taskStatusTextView, taskCompleteCheckBox, taskId)

        taskCompleteCheckBox.setOnCheckedChangeListener { _, isChecked ->
            handleTaskCompletionChange(isChecked, taskStatusTextView)
        }
    }

    private fun displayTaskDetails(name: TextView, statusView: TextView, isComplete: CheckBox, id: TextView) {
        name.text = currentTask.name
        isComplete.isChecked = currentTask.isComplete
        isCompleteCheck(statusView, currentTask.isComplete)
        writeId(id)
    }

    private fun isCompleteCheck(tvIsCompleye: TextView, isComplete: Boolean) {
        tvIsCompleye.text = if (isComplete) "Finalizada " else "Pendiente"
    }

    private fun writeId(id: TextView) {
        id.text = getString(R.string.write_id, currentTask.id.toString())
    }

    private fun handleTaskCompletionChange(isChecked: Boolean, isComplete: TextView) {
        if (isChecked) {
            taskViewModel.completeTask(currentTask.id)
        } else {
            taskViewModel.taskNotCompleted(currentTask.id)
        }
        isCompleteCheck(isComplete, isChecked)
    }
}
