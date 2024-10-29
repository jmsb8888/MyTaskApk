package com.task.mytaskapp.data.di

import android.content.Context
import androidx.room.Room
import com.task.mytaskapp.data.bd.TaskDB
import com.task.mytaskapp.data.bd.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TaskModule {
    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): TaskDB =
        Room.databaseBuilder(
            context,
            TaskDB::class.java,
            "task_database"
        ).build()

    @Provides
    @Singleton
    fun providesTaskDao(taskDB: TaskDB): TaskDao = taskDB.taskDao()

}