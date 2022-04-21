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

    fun getAllTodoByUser(user_id : Long) : LiveData<List<Todo>> = todoDao.getAllTodoByUser(user_id)

    fun getAllTodoByCompleteCheck(user_id: Long, isDone : Boolean)
                    : LiveData<List<Todo>> = todoDao.getAllTodoByCompleteCheck(user_id,isDone)

    fun getAllTodoByPriorityCheck(user_id: Long, priority : String)
                    : LiveData<List<Todo>> = todoDao.getAllTodoByPriorityCheck(user_id,priority)
}