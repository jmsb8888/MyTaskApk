package com.task.mytaskapp.screens.home.rv

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.mytaskapp.R
import com.task.mytaskapp.data.models.Task

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val taskCheckBox: CheckBox = itemView.findViewById(R.id.cbSelect)
    private val delete: ImageView = itemView.findViewById(R.id.delete)
    private val taskTextView: TextView = itemView.findViewById(R.id.tvTaskComplete)

    fun bind(task: Task, onTaskChecked: (Task) -> Unit, onTaskSelected: (Task) -> Unit, deleteTask: (Task) -> Unit) {
        taskTextView.text = task.name
        taskCheckBox.isChecked = task.isComplete
        updateTextStrikeThrough(task)

        taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onTaskChecked(task)
            }
        }

        itemView.setOnClickListener {
            onTaskSelected(task)
        }

        delete.setOnClickListener {
            deleteTask(task)
        }
    }

    private fun updateTextStrikeThrough(task: Task) {
        taskTextView.paintFlags = if (task.isComplete) {
            taskTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            taskTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}