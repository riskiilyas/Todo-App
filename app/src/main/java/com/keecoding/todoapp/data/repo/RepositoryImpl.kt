package com.keecoding.todoapp.data.repo

import com.keecoding.todoapp.data.db.TodoDao
import com.keecoding.todoapp.data.models.TodoTask
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) {

     fun getAllTasks() = todoDao.getAllTasks()
     fun sortByLowPriority() = todoDao.sortByLowPriority()
     fun sortByHighPriority() = todoDao.sortByHighPriority()
     fun getSelectedTask(id: Int) = todoDao.getSelectedTask(id)
      suspend fun addTask(todoTask: TodoTask) {
        todoDao.insertTask(todoTask)
    }
     suspend fun updateTask(todoTask: TodoTask) {
        todoDao.updateTask(todoTask)
    }
     suspend fun deleteTask(todoTask: TodoTask) {
        todoDao.deleteTask(todoTask)
    }
     suspend fun deleteAllTask() {
        todoDao.deleteAllTask()
    }
     fun searchDatabase(query: String) = todoDao.searchDatabase(query)

}