package com.keecoding.todoapp.presentation.ui.screens.list

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.keecoding.todoapp.R
import com.keecoding.todoapp.data.util.SearchAppBarState
import com.keecoding.todoapp.presentation.ui.theme.fabBackgroundColor
import com.keecoding.todoapp.presentation.vm.SharedViewModel

@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchtextState: String by sharedViewModel.searcTextState

    Scaffold(
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

//@Composable
//@Preview
//private fun ListScreenPreview() {
//    ListScreen(navigateToTaskScreen = {},
//    )
//}