package com.keecoding.todoapp.presentation.vm

import android.app.Application
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keecoding.todoapp.data.local.dataStore
import com.keecoding.todoapp.data.models.TodoTask
import com.keecoding.todoapp.data.repo.RepositoryImpl
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

    val darkThemeState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSED)
    val searcTextState: MutableState<String> = mutableStateOf("")

    private val _allTasks = MutableStateFlow<List<TodoTask>>(emptyList())
    val allTasks = _allTasks as StateFlow<List<TodoTask>>

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
            repository.getAllTasks().collect {
                _allTasks.value = it
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