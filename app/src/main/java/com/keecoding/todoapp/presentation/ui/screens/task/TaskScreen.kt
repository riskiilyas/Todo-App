package com.keecoding.todoapp.presentation.ui.screens.task

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.keecoding.todoapp.data.models.Priority
import com.keecoding.todoapp.data.models.TodoTask
import com.keecoding.todoapp.data.util.Action
import com.keecoding.todoapp.data.util.showToast
import com.keecoding.todoapp.presentation.vm.SharedViewModel

@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel,
    selectedTask: TodoTask?
) {
    val title: String by sharedViewModel.title
    val desc: String by sharedViewModel.desc
    val priority: Priority by sharedViewModel.priority
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskAppBar(
                navigateToListScreen = {
                    if (it == Action.NO_ACTION) {
                        navigateToListScreen(it)
                    } else {
                        if(sharedViewModel.validateField()) {
                            navigateToListScreen(it)
                        } else {
                            context.showToast("Input Invalid!")
                        }
                    }
                },
                selectedTask = selectedTask)
        },
        content = {
            TaskContent(
                title = title,
                onTitleChange = sharedViewModel::updateTitle,
                desc = desc,
                onDescChange = {
                    sharedViewModel.desc.value = it
                },
                priority = priority,
                onPrioritySelected = {
                    sharedViewModel.priority.value = it
                }
            )
        }
    )
}