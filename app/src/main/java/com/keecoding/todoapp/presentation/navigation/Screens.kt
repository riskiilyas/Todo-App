package com.keecoding.todoapp.presentation.navigation

import androidx.navigation.NavHostController
import com.keecoding.todoapp.data.util.Action
import com.keecoding.todoapp.data.util.Constants.LIST_SCREEN

class Screens(navController: NavHostController) {
    val list: (Action) -> Unit = {
        navController.navigate("list/${it.name}") {
            popUpTo(LIST_SCREEN) {
                inclusive = true
            }
        }
    }

    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}