package com.example.noteapp.presentation

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.repository.TodoRepository
import com.example.noteapp.domain.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel(){
    var state by mutableStateOf(NoteState())
        private set
    private val todo: Todo
        get() = state.run {
            Todo(
                id = id,
                title = title,
                task = task,
                isImportant = isImportant,
                date = date
            )
        }

    val getAllTodos : Flow<List<Todo>> = repository.getAllTodos()

    private var deletedTodo: Todo? = null

    fun insertTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTodo(todo = todo)
        }
    }

    fun updateTodo() {
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTodo(todo = todo)
        }
    }


    fun deleteTodo(todo: Todo) {
        deletedTodo = todo
        viewModelScope.launch {
            repository.deleteTodo(todo = todo)
        }
    }

    fun undoDeletedTodo(){
        deletedTodo?.let { todo: Todo ->
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertTodo(todo = todo)
            }
        }
    }

    fun getTodoById(id: Int) = viewModelScope.launch {
        repository.getTodoById(id).collectLatest{ todo ->
            state = state.copy(
                id = todo.id,
                title = todo.title,
                task = todo.task,
                date = todo.date,
                isImportant = todo.isImportant
            )
        }
}


    fun updateIsImportant(newValue: Boolean) {
        state = state.copy(isImportant = newValue)
    }

    fun onTitleChange(title: String){
        state = state.copy(title = title)
    }
    fun onTaskChange(task: String){
        state = state.copy(task = task)
    }



}

data class NoteState(
    val id: Int = 0,
    val title: String = "",
    val task: String = "",
    val date: Date = Date(),
    val isImportant: Boolean = false
)