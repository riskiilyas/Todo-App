package com.keecoding.todoapp.presentation.ui.screens.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.keecoding.todoapp.R
import com.keecoding.todoapp.data.models.Priority
import com.keecoding.todoapp.presentation.components.PriorityItem
import com.keecoding.todoapp.presentation.ui.theme.*
import androidx.compose.material.TextFieldDefaults
import androidx.compose.ui.Alignment
import com.keecoding.todoapp.data.util.SearchAppBarState
import com.keecoding.todoapp.presentation.vm.SharedViewModel


@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {
    when(searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSortClicked = {

                },
                onDeleteClicked = {

                },
                sharedViewModel
            )
        }

        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChanged = {
                    sharedViewModel.searcTextState.value = it
                },
                onCloseClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                },
                onSearchClicked = {

                }
            )
        }
    }
}

@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit,
    sharedViewModel: SharedViewModel
) {
    TopAppBar(
        title = {
            Text(
                text = "Tasks",
                color = MaterialTheme.colors.topAppBarContentColor
            ) },
        actions = {
            DefaultListAppBarActions(
                onSearchClicked = {
                    onSearchClicked()
                },
                onSortClicked = {

                },
                onDeleteClicked = {

                },
                sharedViewModel = sharedViewModel
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}

@Composable
fun DefaultListAppBarActions(
    sharedViewModel: SharedViewModel,
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    SearchAction(onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    MoreAction(onDeleteClicked = onDeleteClicked, sharedViewModel = sharedViewModel)
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {

    IconButton(onClick = { onSearchClicked() }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search Tasks",
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true}
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_filter_list_24),
            contentDescription = "Sort Tasks",
            tint = MaterialTheme.colors.topAppBarContentColor
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(Priority.LOW)
            }) {
                PriorityItem(priority = Priority.LOW)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(Priority.HIGH)
            }) {
                PriorityItem(priority = Priority.HIGH)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onSortClicked(Priority.NONE)
            }) {
                PriorityItem(priority = Priority.NONE)
            }
        }
    }
}

@Composable
fun MoreAction(
    sharedViewModel: SharedViewModel,
    onDeleteClicked: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val darkTheme = sharedViewModel.darkThemeState.collectAsState(initial = false)
    IconButton(
        onClick = { expanded = true}
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
            contentDescription = "Delete All",
            tint = MaterialTheme.colors.topAppBarContentColor
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                onDeleteClicked()
                expanded = false
            }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Delete All",
                        style = Typography.subtitle2,
                        modifier = Modifier.padding(start = MEDIUM_PADDING)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_delete_24),
                        contentDescription = "Delete All",
                        modifier = Modifier.padding(start = LARGE_PADDING)
                    )
                }
            }

            DropdownMenuItem(onClick = {}) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Night Mode",
                        style = Typography.subtitle2,
                        modifier = Modifier.padding(start = MEDIUM_PADDING)
                    )
                    Switch(
                        modifier = Modifier.padding(start = LARGE_PADDING),
                        checked = darkTheme.value,
                        onCheckedChange = {
                            sharedViewModel.switchTheme()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
            ,
            value = text, 
            onValueChange = onTextChanged,
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "Search",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine =  true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(ContentAlpha.disabled),
                    onClick = {  }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isBlank()) {
                            onCloseClicked()
                        } else {
                            onTextChanged("")
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}


//@Composable
//@Preview
//private fun DefaultListAppBarPreview() {
//    DefaultListAppBar(
//        onSearchClicked = {
//
//        },
//        onSortClicked = {
//
//        },
//        onDeleteClicked = {
//
//        }
//    )
//}

@Composable
@Preview
private fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Search",
        onTextChanged = {},
        onCloseClicked = {  },
        onSearchClicked = { }
    )
}