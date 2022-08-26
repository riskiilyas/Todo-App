package com.keecoding.todoapp.presentation.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGray = Color(0xFFFCFCFC)

// Priorities
val LowPriorityColor = Color(0xFF00C980)
val MediumPriorityColor = Color(0xFFFFC114)
val HighPriorityColor = Color(0xFFFF4646)
val NonePriorityColor = Color(0xFFFFFFFF)

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.Blue else Color.Blue

val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else LightGray

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.Blue else Color.Black

val Colors.taskItemBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else Color.DarkGray

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isLight) Color.DarkGray else Color.LightGray