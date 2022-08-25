package com.keecoding.todoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.keecoding.todoapp.data.models.TodoTask

@Database(entities = [TodoTask::class], version = 1, exportSchema = false)
abstract class AppDB: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}