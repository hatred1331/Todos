package com.example.noteapp.data.repository

import com.example.noteapp.data.local.TodoDao
import com.example.noteapp.domain.model.Todo
import com.example.noteapp.presentation.NoteState
import kotlinx.coroutines.flow.Flow

class TodoRepository(
   private val dao: TodoDao
) {
    suspend fun insertTodo(todo: Todo) = dao.insertTodo(todo = todo)

    suspend fun updateTodo(todo: Todo) = dao.updateTodo(todo = todo)

    suspend fun deleteTodo(todo: Todo) = dao.deleteTodo(todo = todo)

    fun getTodoById(id: Int): Flow<Todo> = dao.getTodoById(id = id)

    fun getAllTodos(): Flow<List<Todo>> = dao.getAllTodos()
}