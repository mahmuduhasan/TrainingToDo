package com.example.mytodoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mytodoapp.daos.TodoDao
import com.example.mytodoapp.daos.UserDao
import com.example.mytodoapp.entities.Todo
import com.example.mytodoapp.entities.User

@Database(entities = [Todo::class, User::class], version = 1)
abstract class TodoAppDB : RoomDatabase() {

    abstract fun getTodoDao() : TodoDao
    abstract fun getUserDao() : UserDao

    companion object{
        private var db : TodoAppDB? = null
        fun getDB(context: Context) : TodoAppDB{
            if(db == null){
                db = Room.databaseBuilder(
                    context.applicationContext,
                    TodoAppDB::class.java,
                    "todo"
                ).build()
                return db!!
            }
            return db!!
        }
    }
}