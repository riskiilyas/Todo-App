package com.keecoding.todoapp.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keecoding.todoapp.data.models.TodoTask
import com.keecoding.todoapp.data.repo.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: RepositoryImpl
): ViewModel() {

    private val _allTasks = MutableStateFlow<List<TodoTask>>(emptyList())
    val allTasks = _allTasks as StateFlow<List<TodoTask>>

    fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collect {
                _allTasks.value = it
            }
        }
    }

}