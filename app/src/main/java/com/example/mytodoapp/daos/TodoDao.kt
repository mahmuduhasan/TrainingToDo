package com.example.mytodoapp.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mytodoapp.entities.Todo

@Dao
interface TodoDao {
    @Insert
    suspend fun insertTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("select * from tbl_todo where todo_id = :todo_id")
    fun getTodo(todo_id : Long) : LiveData<Todo>

    @Query("select * from tbl_todo")
    fun getAllTodo() : LiveData<List<Todo>>

    @Query("select * from tbl_todo where user_id = :user_id")
    fun getAllTodoByUser(user_id : Long) : LiveData<List<Todo>>

    @Query("select * from tbl_todo where user_id = :user_id and isDone = :isDone")
    fun getAllTodoByCompleteCheck(user_id : Long, isDone : Boolean) : LiveData<List<Todo>>

    @Query("select * from tbl_todo where user_id = :user_id and priority = :priority")
    fun getAllTodoByPriorityCheck(user_id : Long, priority : String) : LiveData<List<Todo>>
}