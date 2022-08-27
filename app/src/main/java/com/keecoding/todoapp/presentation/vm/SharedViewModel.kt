package com.keecoding.todoapp.presentation.vm

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keecoding.todoapp.data.local.dataStore
import com.keecoding.todoapp.data.models.TodoTask
import com.keecoding.todoapp.data.repo.RepositoryImpl
import com.keecoding.todoapp.data.util.RequestState
import com.keecoding.todoapp.data.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: RepositoryImpl,
    private val app: Application
): ViewModel() {

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
}