package com.keecoding.todoapp.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.keecoding.todoapp.presentation.navigation.SetupNavigation
import com.keecoding.todoapp.presentation.ui.theme.TodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private lateinit var navHostController: NavHostController
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
//                window.statusBarColor = if(isSystemInDarkTheme()) Color.parseColor("#0000FF") else Color.parseColor("#FCFCFC")
//                window.statusBarColor = MaterialTheme.colors.background.toArgb()
                navHostController = rememberNavController()
                SetupNavigation(navController = navHostController)
            }
        }
    }
}