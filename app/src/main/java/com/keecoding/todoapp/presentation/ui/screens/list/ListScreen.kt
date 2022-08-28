package com.keecoding.todoapp.presentation.ui.screens.list

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.keecoding.todoapp.R
import com.keecoding.todoapp.data.util.Action
import com.keecoding.todoapp.data.util.SearchAppBarState
import com.keecoding.todoapp.presentation.ui.theme.fabBackgroundColor
import com.keecoding.todoapp.presentation.vm.SharedViewModel
import kotlinx.coroutines.launch

@Composable
@ExperimentalMaterialApi
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }

    val allTasks = sharedViewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchtextState: String by sharedViewModel.searcTextState

    val action by sharedViewModel.action

    val scaffoldState = rememberScaffoldState()

    DisplaySnackbar(
        scaffoldState = scaffoldState,
        handleDatabaseAction = {
            sharedViewModel.handleDatabaseAction(action)
        },
        onUndoClicked = {
            sharedViewModel.action.value = it
        },
        taskTitle = sharedViewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
                 ListAppBar(
                     sharedViewModel = sharedViewModel,
                     searchAppBarState = searchAppBarState,
                     searchTextState = searchtextState
                 )
        },
        floatingActionButton = {
            ListFab(navigateToTaskScreen)
        },
    ) {
        ListContent(
            tasks = allTasks.value,
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add, 
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White,
        )
    }
}

@Composable
fun DisplaySnackbar(
    scaffoldState: ScaffoldState,
    handleDatabaseAction: () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    handleDatabaseAction()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if(action != Action.NO_ACTION) {
            scope.launch {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = "${action.name}: $taskTitle",
                    actionLabel = setActionLabel(action)
                )

                undoDeletedTask(
                    action, snackbarResult, onUndoClicked
                )
            }
        }
    }
}

private fun setActionLabel(action: Action): String {
    return if(action.name == " DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if(snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}
//@Composable
//@Preview
//private fun ListScreenPreview() {
//    ListScreen(navigateToTaskScreen = {},
//    )
//}