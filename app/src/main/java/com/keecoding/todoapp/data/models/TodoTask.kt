package com.keecoding.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.keecoding.todoapp.data.util.Constants

@Entity(tableName = Constants.DATABASE_TABLE)
data class TodoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val desc: String,
    val priority: Priority
)
