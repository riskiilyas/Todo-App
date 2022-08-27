package com.keecoding.todoapp.presentation.ui.screens.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.keecoding.todoapp.R
import com.keecoding.todoapp.data.models.Priority
import com.keecoding.todoapp.data.models.TodoTask
import com.keecoding.todoapp.data.util.RequestState
import com.keecoding.todoapp.presentation.ui.theme.*

@ExperimentalMaterialApi
@Composable
fun ListContent(
    tasks: RequestState<List<TodoTask>>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if(tasks is RequestState.Success) {
        LazyColumn {
            items(
                count = tasks.data.size,
                key = { i ->
                    tasks.data[i].id
                }
            ) { index ->
                TaskItem(
                    todoTask = tasks.data[index],
                    navigateToTaskScreen = navigateToTaskScreen
                )
            }
        }
    } else {
        EmptyTask()
    }
}

@ExperimentalMaterialApi
@Composable
fun TaskItem(
    todoTask: TodoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.taskItemBackgroundColor,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(todoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row(

            ) {
                Text(
                    text = todoTask.title,
                    color = MaterialTheme.colors.taskItemTextColor,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.weight(8f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .width(PRIORITY_INDICATOR_SIZE)
                            .height(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = todoTask.priority.color
                        )
                    }
                }
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = todoTask.desc,
                color = MaterialTheme.colors.taskItemTextColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun EmptyTask() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_task))
        LottieAnimation(composition, iterations = Int.MAX_VALUE, modifier = Modifier.size(320.dp))
        Text(
            text = "No Tasks Available...",
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun TaskItemPrev(

) {
    TaskItem(todoTask = TodoTask(0,"Title", "some random text", Priority.MEDIUM), navigateToTaskScreen = {})
}