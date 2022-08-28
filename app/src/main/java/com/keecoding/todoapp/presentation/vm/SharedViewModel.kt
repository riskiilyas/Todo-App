package com.keecoding.todoapp.presentation.vm

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keecoding.todoapp.data.local.dataStore
import com.keecoding.todoapp.data.models.Priority
import com.keecoding.todoapp.data.models.TodoTask
import com.keecoding.todoapp.data.repo.RepositoryImpl
import com.keecoding.todoapp.data.util.Action
import com.keecoding.todoapp.data.util.Constants
import com.keecoding.todoapp.data.util.RequestState
import com.keecoding.todoapp.data.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    private val app: Application
): ViewModel() {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val desc: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searcTextState: MutableState<String> = mutableStateOf("")

    val darkThemeState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _allTasks = MutableStateFlow<RequestState<List<TodoTask>>>(RequestState.Idle)
    val allTasks = _allTasks as StateFlow<RequestState<List<TodoTask>>>

    init {
        viewModelScope.launch {
            val DARK_THEME = booleanPreferencesKey("DARK_THEME")
            val exampleCounterFlow: Flow<Boolean> = app.dataStore.data
                .map { preferences ->
                    // No type safety.
                    preferences[DARK_THEME] ?: false
                }

            exampleCounterFlow.collect{
                darkThemeState.value = it
            }
        }
    }

    fun getAllTasks() {
        viewModelScope.launch {
            _allTasks.value = RequestState.Loading
            try {
                repository.getAllTasks()
                    .catch {
                        _allTasks.value = RequestState.Error(it)
                    }
                    .collect {
                        _allTasks.value = RequestState.Success(it)
                    }
            } catch (e: Exception) {
                _allTasks.value = RequestState.Error(e)
            }
        }
    }

    private val _selectedTask: MutableStateFlow<TodoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<TodoTask?> = _selectedTask

    fun getSelectedTask(id: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(id)
                .collect {
                    _selectedTask.value = it
                }
        }
    }

    fun switchTheme() {
        viewModelScope.launch {
            app.dataStore.edit { settings ->
                val DARK_THEME = booleanPreferencesKey("DARK_THEME")
                val currentCounterValue = settings[DARK_THEME] ?: false
                settings[DARK_THEME] = !currentCounterValue
            }
        }
    }

    fun updateTaskField(todoTask: TodoTask?) {
        if (todoTask != null) {
            id.value = todoTask.id
            title.value = todoTask.title
            desc.value = todoTask.desc
            priority.value = todoTask.priority
        } else {
            id.value = 0
            title.value = ""
            desc.value = ""
            priority.value = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if(newTitle.length <= Constants.MAX_TITLE_LENGTH) {
            title.value = newTitle
        }
    }

    fun validateField(): Boolean {
        return title.value.isNotEmpty() && desc.value.isNotEmpty()
    }

    fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask = TodoTask(
                title = title.value,
                desc = desc.value,
                priority = priority.value
            )

            repository.addTask(todoTask = todoTask)
        }
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask = TodoTask(
                id = id.value,
                title = title.value,
                desc = desc.value,
                priority = priority.value
            )

            repository.updateTask(todoTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val todoTask = TodoTask(
                id = id.value,
                title = title.value,
                desc = desc.value,
                priority = priority.value
            )

            repository.deleteTask(todoTask)
        }
    }

    fun handleDatabaseAction(action: Action) {
        when(action) {
            Action.ADD -> {
                addTask()
            }

            Action.UPDATE -> {
                updateTask()
            }

            Action.DELETE -> {
                deleteTask()
            }

            Action.DELETE_ALL -> {

            }

            Action.UNDO -> {
                addTask()
            }

            else -> {

            }
        }

        this.action.value = Action.NO_ACTION
    }
}