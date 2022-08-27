package com.keecoding.todoapp.presentation.navigation

import androidx.navigation.NavHostController
import com.keecoding.todoapp.data.util.Action
import com.keecoding.todoapp.data.util.Constants.LIST_SCREEN

class Screens(navController: NavHostController) {
    val task: (Action) -> Unit = {
        navController.navigate("list/${it.name}") {
            popUpTo(LIST_SCREEN) {
                inclusive = true
            }
        }
    }

    val list: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}