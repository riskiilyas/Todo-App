package com.keecoding.todoapp.presentation.ui.screens.task

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.keecoding.todoapp.data.models.TodoTask
import com.keecoding.todoapp.data.util.Action

@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    selectedTask: TodoTask?
) {
    Scaffold(
        topBar = {
            TaskAppBar(navigateToListScreen = navigateToListScreen, selectedTask = selectedTask)
        }
    ) {
        
    }
}