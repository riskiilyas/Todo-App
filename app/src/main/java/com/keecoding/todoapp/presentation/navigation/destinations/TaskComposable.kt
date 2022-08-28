package com.keecoding.todoapp.presentation.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.keecoding.todoapp.data.util.Action
import com.keecoding.todoapp.data.util.Constants
import com.keecoding.todoapp.data.util.Constants.TASK_ARGUMENT_KEY
import com.keecoding.todoapp.data.util.Constants.TASK_SCREEN
import com.keecoding.todoapp.presentation.ui.screens.task.TaskScreen
import com.keecoding.todoapp.presentation.vm.SharedViewModel

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        sharedViewModel.getSelectedTask(taskId)

        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = selectedTask) {
            if(selectedTask != null || taskId == -1) {
                sharedViewModel.updateTaskField(selectedTask)
            }
        }

        TaskScreen(navigateToListScreen = navigateToListScreen, selectedTask = selectedTask, sharedViewModel = sharedViewModel)
    }
}