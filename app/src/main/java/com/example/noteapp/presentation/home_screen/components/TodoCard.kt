package com.example.noteapp.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.domain.model.Todo
import com.example.noteapp.presentation.common.taskTextStyle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun TodoCard(
    todo: Todo,
    onDone: () -> Unit,
    onUpdate: (id: Int) -> Unit,
    color: Color

) {
    Card(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row (
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = { onDone()},
                modifier = Modifier
                ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null
                )

            }
            Column(
                modifier = Modifier
                    .weight(3f)
            ) {
                Text(
                    text = todo.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = taskTextStyle,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = todo.task,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = taskTextStyle
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = formatDate(todo.date),
                    fontWeight = FontWeight.Light,
                    fontSize = 10.sp
                    )
            }
            Column (
                modifier = Modifier
                    .weight(0.5f)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            ){
                if (todo.isImportant) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = null,
                        modifier = Modifier
                    )

                }
                IconButton(
                    onClick = { onUpdate(todo.id) },
                    modifier = Modifier

                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null
                    )
                }

            }

        }
    }
}

private fun formatDate(date: Date): String{
    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
    return sdf.format(date.time)
}

