package com.example.noteapp.presentation.home_screen.components

import android.app.AlertDialog
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.R
import com.example.noteapp.domain.model.Todo
import com.example.noteapp.presentation.MainViewModel
import com.example.noteapp.presentation.common.taskTextStyle
import com.example.noteapp.presentation.common.toastMsg


import kotlinx.coroutines.job
import java.util.Date
import kotlin.coroutines.coroutineContext


@Composable
fun AlertDialog_HomeSc(
    openDialog: Boolean,
    onClose: () -> Unit,
    mainViewModel: MainViewModel
) {
    var taskText: String by remember { mutableStateOf("")}
    var titleText: String by remember { mutableStateOf("")}
    var isImportant: Boolean by remember { mutableStateOf(false)}

    val todo = Todo(0, title = titleText, task = taskText, isImportant = isImportant, date = Date())

    val focusRequester = FocusRequester()
    val context : Context = LocalContext.current

    if(openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = "Todo",
                    fontFamily = FontFamily.Serif
                )
            },
            text = {
                LaunchedEffect(
                    key1 = true,
                    block = {
                        coroutineContext.job.invokeOnCompletion {
                            focusRequester.requestFocus()
                        }
                    })

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    // TITLE
                    TextField(
                        value = titleText,
                        onValueChange = {titleText = it},
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.add_title),
                                fontFamily = FontFamily.Monospace
                            )
                        },
                        shape = RectangleShape,
                        modifier = Modifier
                            .focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        trailingIcon = {
                            IconButton(onClick = { titleText = "" }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = null
                                )
                            }
                        },
                        textStyle = taskTextStyle
                    )

                    // TASK
                    TextField(
                        value = taskText,
                        onValueChange = {taskText = it},
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.add_task),
                                fontFamily = FontFamily.Monospace
                            )
                        },
                        shape = RectangleShape,
                        modifier = Modifier
                            .focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        trailingIcon = {
                            IconButton(onClick = { taskText = "" }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = null
                                )
                            }
                        },
                        textStyle = taskTextStyle
                    )

                    Row(
                        modifier = Modifier
                        .fillMaxSize(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = stringResource(id = R.string.important),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Checkbox(checked = isImportant,
                                onCheckedChange = {isImportant = it})
                    }
                }
            },
            onDismissRequest = {
                onClose()
                titleText = ""
                taskText = ""
                isImportant = false
            },
            confirmButton = {
                Button(onClick = {
                    if(titleText.isNotBlank() && taskText.isNotBlank()) {
                        mainViewModel.insertTodo(todo)
                        titleText = ""
                        taskText = ""
                        isImportant = false
                        onClose()
                    } else {
                        toastMsg(
                        context,
                            R.string.empty_task.toString()
                        )
                    }
                }) {
                    Text(text = stringResource(id = R.string.save))
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    onClose()
                    titleText = ""
                    taskText = ""
                    isImportant = false
                }) {
                    Text(text = stringResource(id = R.string.close))
                }
            }
        )
    }
}