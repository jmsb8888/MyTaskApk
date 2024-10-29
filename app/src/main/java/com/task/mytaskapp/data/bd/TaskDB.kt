package com.task.mytaskapp.data.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.mytaskapp.data.models.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDB: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}