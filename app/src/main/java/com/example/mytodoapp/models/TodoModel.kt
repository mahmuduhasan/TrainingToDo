package com.example.mytodoapp.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.db.TodoAppDB
import com.example.mytodoapp.entities.Todo
import com.example.mytodoapp.repositories.TodoRepository
import kotlinx.coroutines.launch

class TodoModel(application: Application) : AndroidViewModel(application) {
    private lateinit var repository: TodoRepository

    init {
        val dao = TodoAppDB.getDB(application).getTodoDao()
        repository = TodoRepository(dao)
    }

    fun insertTodo(todo: Todo){
        viewModelScope.launch {
            repository.insertTodo(todo)
        }
    }

    fun updateTodo(todo: Todo){
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo){
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }

    fun getTodo(id : Long) : LiveData<Todo> = repository.getTodo(id)

    fun getAllTodo() : LiveData<List<Todo>> = repository.getAllTodo()
}