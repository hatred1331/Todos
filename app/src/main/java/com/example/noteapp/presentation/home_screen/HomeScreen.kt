package com.example.noteapp.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.noteapp.R
import com.example.noteapp.domain.model.Todo
import com.example.noteapp.presentation.MainViewModel
import com.example.noteapp.presentation.common.mySnackBar
import com.example.noteapp.presentation.common.topAppBarTextStyle
import com.example.noteapp.presentation.home_screen.components.AlertDialog_HomeSc
import com.example.noteapp.presentation.home_screen.components.EmptyTaskScreen
import com.example.noteapp.presentation.home_screen.components.TodoCard
import com.example.noteapp.ui.theme.secondaryDark
import com.example.noteapp.ui.theme.tertiaryDark
import kotlinx.coroutines.CoroutineScope

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    onUpdate: (id: Int) -> Unit
){
    val context = LocalContext.current

    val todos : List<Todo> by
            mainViewModel.getAllTodos.collectAsStateWithLifecycle(initialValue = emptyList(), lifecycleOwner =  androidx.compose.ui.platform.LocalLifecycleOwner.current)

    var openDialog : Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    val scope: CoroutineScope = rememberCoroutineScope()
    val snackbarHostState : SnackbarHostState = remember { SnackbarHostState()}

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Todos",
                    style = topAppBarTextStyle
                )
            })
        }, 
        floatingActionButton = {
            FloatingActionButton(onClick = {openDialog = true}) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues : PaddingValues ->
        AlertDialog_HomeSc(
            openDialog = openDialog,
            onClose = { openDialog = false },
            mainViewModel = mainViewModel
        )

        if (todos.isEmpty()){
            EmptyTaskScreen(paddingValues = paddingValues)
        } else {
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                items(
                    items = todos,
                    key = {it.id}
                    ) {todo: Todo ->
                    if(todo.isImportant){
                        TodoCard(
                            todo = todo,
                            onDone = {
                                mainViewModel.deleteTodo(todo = todo)
                                mySnackBar(
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                    //msg = "DONE! -> \"${todo.task}\"",
                                    msg = context.getString(R.string.done),
                                    actionLabel = context.getString(R.string.undo),
                                    onAction = { mainViewModel.undoDeletedTodo() }
                                )
                            },
                            onUpdate = onUpdate,
                            color = tertiaryDark
                        )
                    } else {
                        TodoCard(
                            todo = todo,
                            onDone = {
                                mainViewModel.deleteTodo(todo = todo)
                                mySnackBar(
                                    scope = scope,
                                    snackbarHostState = snackbarHostState,
                                    msg = context.getString(R.string.done),
                                    actionLabel = context.getString(R.string.undo),
                                    onAction = { mainViewModel.undoDeletedTodo() }
                                )
                            },
                            onUpdate = onUpdate,
                            color = secondaryDark
                        )
                    }

                }
            }
        }
    }
}