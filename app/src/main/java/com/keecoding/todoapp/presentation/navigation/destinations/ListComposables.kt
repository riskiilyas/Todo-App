package com.keecoding.todoapp.presentation.navigation.destinations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.keecoding.todoapp.data.util.Constants.LIST_ARGUMENT_KEY
import com.keecoding.todoapp.data.util.Constants.LIST_SCREEN
import com.keecoding.todoapp.presentation.ui.screens.list.ListScreen
import com.keecoding.todoapp.presentation.vm.SharedViewModel

@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) {
        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel
        )
    }
}