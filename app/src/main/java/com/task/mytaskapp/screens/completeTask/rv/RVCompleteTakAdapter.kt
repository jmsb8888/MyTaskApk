package com.task.mytaskapp.screens.completeTask.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.mytaskapp.R
import com.task.mytaskapp.data.models.Task

class RVCompleteTaskAdapter(
    private val completedTasks: List<Task>,
    private val onTaskNotComplete: (Int) -> Unit,
    private val deleteTask: (Int) -> Unit,
    private val onTaskSelect: (Task) -> Unit
) : RecyclerView.Adapter<CompleteTaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompleteTaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list, parent, false)
        return CompleteTaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CompleteTaskViewHolder, position: Int) {
        holder.bind(completedTasks[position], onTaskNotComplete, onTaskSelect, deleteTask)
    }

    override fun getItemCount(): Int = completedTasks.size
}
