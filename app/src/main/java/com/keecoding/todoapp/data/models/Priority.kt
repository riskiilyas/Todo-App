package com.keecoding.todoapp.data.models

import androidx.compose.ui.graphics.Color
import com.keecoding.todoapp.presentation.ui.theme.HighPriorityColor
import com.keecoding.todoapp.presentation.ui.theme.LowPriorityColor
import com.keecoding.todoapp.presentation.ui.theme.MediumPriorityColor
import com.keecoding.todoapp.presentation.ui.theme.NonePriorityColor


enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}
