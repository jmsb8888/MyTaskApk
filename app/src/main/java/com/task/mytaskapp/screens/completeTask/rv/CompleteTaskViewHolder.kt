package com.task.mytaskapp.screens.completeTask.rv

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.mytaskapp.R
import com.task.mytaskapp.data.models.Task

class CompleteTaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val nameTextView: TextView = itemView.findViewById(R.id.tvTaskComplete)
    private val taskSelectCheckBox: CheckBox = itemView.findViewById(R.id.cbSelect)

    fun bind(task: Task, onTaskRevert: (Int) -> Unit, onTaskSelect: (Task) -> Unit) {
        nameTextView.text = task.name
        nameTextView.paintFlags = nameTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        taskSelectCheckBox.isChecked = true

        taskSelectCheckBox.setOnCheckedChangeListener(null) // Remove previous listener
        taskSelectCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                onTaskRevert(task.id)
            }
        }

        nameTextView.setOnClickListener {
            onTaskSelect(task)
        }
    }
}
