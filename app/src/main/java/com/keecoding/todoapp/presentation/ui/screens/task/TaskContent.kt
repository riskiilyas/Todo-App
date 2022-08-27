package com.keecoding.todoapp.presentation.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.keecoding.todoapp.data.models.Priority
import com.keecoding.todoapp.presentation.components.PriorityDropDown
import com.keecoding.todoapp.presentation.ui.theme.LARGE_PADDING
import com.keecoding.todoapp.presentation.ui.theme.MEDIUM_PADDING

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    desc: String,
    onDescChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = onTitleChange,
            label = {
                Text(
                    text = "Title"
                )
            },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        
        PriorityDropDown(priority = priority, onPrioritySelected = onPrioritySelected)
        
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = desc, 
            onValueChange = onDescChange,
            label = {
                Text(text = "Description")
            },
            textStyle = MaterialTheme.typography.body1,
        )
    }
}

@Composable
@Preview
fun TaskContentPrev() {
    TaskContent(
        title = "",
        onTitleChange = {},
        desc = "",
        onDescChange = {},
        priority = Priority.LOW,
        onPrioritySelected = {}    )
}