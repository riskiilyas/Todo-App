package com.keecoding.todoapp.data.db

import androidx.room.*
import com.keecoding.todoapp.data.models.TodoTask
import com.keecoding.todoapp.data.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: TodoTask)

    @Query("SELECT * FROM ${Constants.DATABASE_TABLE} ORDER BY id ASC")
    fun getAllTasks(): Flow<List<TodoTask>>

    @Query("SELECT * FROM ${Constants.DATABASE_TABLE} WHERE id = :id")
    fun getSelectedTask(id: Int): Flow<TodoTask>

    @Update
    suspend fun updateTask(task: TodoTask)

    @Delete
    suspend fun deleteTask(task: TodoTask)

    @Query("DELETE FROM ${Constants.DATABASE_TABLE}")
    suspend fun deleteAllTask()

    @Query("SELECT * FROM ${Constants.DATABASE_TABLE} WHERE title LIKE :searchQuery OR `desc` LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<TodoTask>>

    @Query("SELECT * FROM ${Constants.DATABASE_TABLE} ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): Flow<List<TodoTask>>

    @Query("SELECT * FROM ${Constants.DATABASE_TABLE} ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): Flow<List<TodoTask>>
}