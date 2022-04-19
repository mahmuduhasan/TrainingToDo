package com.example.mytodoapp.repositories

import androidx.lifecycle.LiveData
import com.example.mytodoapp.daos.TodoDao
import com.example.mytodoapp.entities.Todo

class TodoRepository(val todoDao: TodoDao) {
    suspend fun insertTodo(todo: Todo){
        todoDao.insertTodo(todo)
    }

    suspend fun updateTodo(todo: Todo){
        todoDao.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo){
        todoDao.deleteTodo(todo)
    }

    fun getTodo(id : Long) : LiveData<Todo> = todoDao.getTodo(id)

    fun getAllTodo() : LiveData<List<Todo>> = todoDao.getAllTodo()
}