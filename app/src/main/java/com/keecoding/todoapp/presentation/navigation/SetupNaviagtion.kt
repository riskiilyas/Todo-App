package com.keecoding.todoapp.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.keecoding.todoapp.data.util.Constants.LIST_SCREEN
import com.keecoding.todoapp.presentation.navigation.destinations.listComposable
import com.keecoding.todoapp.presentation.navigation.destinations.taskComposable
import com.keecoding.todoapp.presentation.vm.SharedViewModel

@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController) {
        Screens(navController)
    }

    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {
        listComposable(navigateToTaskScreen = screen.list, sharedViewModel)
        taskComposable(navigateToListScreen = screen.task, sharedViewModel)
    }
}