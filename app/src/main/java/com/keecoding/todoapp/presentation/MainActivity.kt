package com.keecoding.todoapp.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.keecoding.todoapp.data.local.dataStore
import com.keecoding.todoapp.presentation.navigation.SetupNavigation
import com.keecoding.todoapp.presentation.ui.theme.TodoAppTheme
import com.keecoding.todoapp.presentation.vm.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private lateinit var navHostController: NavHostController
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = sharedViewModel.darkThemeState.collectAsState(initial = false)

            TodoAppTheme(
                darkTheme = darkTheme.value
            ) {
                navHostController = rememberNavController()
                SetupNavigation(
                    navController = navHostController,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }
}