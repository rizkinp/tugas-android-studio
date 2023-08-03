package com.example.projectutstodolist04

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class TodoListViewModel(application: Application) : AndroidViewModel(application) {

    private val todoListRepository: TodoListRepository
    val todoList: LiveData<List<Todo>>

    init {
        val todoListDao = TodoListDatabase.getDatabase(application).todoListDao()
        todoListRepository = TodoListRepository(todoListDao)
        todoList = todoListRepository.getAllTodo()
    }

    fun insert(todo: Todo) {
        val viewModelScope = null
        viewModelScope.launch {
            todoListRepository.insert(todo)
        }
    }

    fun update(todo: Todo) {
        viewModelScope.launch {
            todoListRepository.update(todo)
        }
    }

    fun delete(todo: Todo) {
        viewModelScope.launch {
            todoListRepository.delete(todo)
        }
    }
}
