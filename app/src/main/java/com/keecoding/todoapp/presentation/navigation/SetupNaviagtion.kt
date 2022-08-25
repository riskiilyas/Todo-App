package com.keecoding.todoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.keecoding.todoapp.data.util.Constants.LIST_SCREEN
import com.keecoding.todoapp.presentation.navigation.destinations.listComposable
import com.keecoding.todoapp.presentation.navigation.destinations.taskComposable

@Composable
fun SetupNavigation(
    navController: NavHostController
) {
    val screen = remember(navController) {
        Screens(navController)
    }

    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {
        listComposable(navigateToTaskScreen = screen.task)
        taskComposable(navigateToListScreen = screen.list)
    }
}