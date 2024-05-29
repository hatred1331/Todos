package com.example.noteapp.presentation.update_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.presentation.MainViewModel
import com.example.noteapp.presentation.common.taskTextStyle
import com.example.noteapp.presentation.common.topAppBarTextStyle

@ExperimentalMaterial3Api
@Composable
fun UpdateScreen(
    id: Int,
    mainViewModel: MainViewModel,
    onBack: ()  -> Unit
){
    val task: String = mainViewModel.state.task
    //!!!!!
    val title: String = mainViewModel.state.title
    //!!!!!
    val isImportant: Boolean = mainViewModel.state.isImportant
    
    LaunchedEffect(
        key1 = true,
        block = {
            mainViewModel.getTodoById(id = id)
        }) 
    
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Update Todo",
                    style = topAppBarTextStyle
                )
            },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                })
        }
    ) { paddingValues : PaddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Button(onClick = {
                mainViewModel.updateTodo()
                onBack()
            }) {
                Text(
                    text = "Save Task",
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            TextField(
                value = title,
                onValueChange = {title: String ->
                    mainViewModel.onTitleChange(title)
                },
                label = {
                    Text(
                        text = "Title",
                        fontFamily = FontFamily.Monospace
                    )
                },
                shape = RectangleShape,
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.Sentences
                ),
                textStyle = taskTextStyle
            )
            TextField(
                value = task,
                onValueChange = {task: String ->
                    mainViewModel.onTaskChange(task)
                },
//                modifier = Modifier
//                    .fillMaxSize(.9f),
                label = {
                    Text(
                        text = "Task",
                        fontFamily = FontFamily.Monospace
                    )
                },
                shape = RectangleShape,
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.Sentences
                ),
                textStyle = taskTextStyle
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Important",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.size(8.dp))
                Checkbox(
                    checked = isImportant,
                    onCheckedChange = {newValue: Boolean ->
                        mainViewModel.updateIsImportant(newValue = newValue)
                    })
            }
            //Spacer(modifier = Modifier.size(8.dp))
//            Button(onClick = {
//                mainViewModel.updateTodo(mainViewModel.todo)
//                onBack()
//            }) {
//                Text(
//                    text = "Save Task",
//                    fontSize = 16.sp
//                )
//            }
        }

    }
}