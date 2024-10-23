package com.task.mytaskapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id: Int,
    val name: String,
    val isComplete: Boolean
) : Parcelable
