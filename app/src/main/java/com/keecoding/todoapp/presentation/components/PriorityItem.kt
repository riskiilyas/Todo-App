package com.keecoding.todoapp.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keecoding.todoapp.data.models.Priority
import com.keecoding.todoapp.presentation.ui.theme.PRIORITY_INDICATOR_SIZE
import com.keecoding.todoapp.presentation.ui.theme.Typography

@Composable
fun PriorityItem(
    priority: Priority
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier.size(PRIORITY_INDICATOR_SIZE),
        ) {
            drawCircle(color = priority.color)
        }
        Text(
            text = priority.name,
            style = Typography.subtitle1,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
@Preview
fun PriorityItemPrev() {
    PriorityItem(priority = Priority.MEDIUM)
}