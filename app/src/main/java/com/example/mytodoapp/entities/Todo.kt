package com.example.mytodoapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_todo")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_id")
    val todoId : Long = 0,
    @ColumnInfo(name = "user_id")
    val userId : Long,
    val name : String,
    val priority : String,
    val date : String,
    val time : String,
    val isDone : Boolean = false
)
