package com.example.mytodoapp.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mytodoapp.entities.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User) : Long

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("select * from tbl_user where email = :email")
    suspend fun getUser(email : String) : User?

    //fun getUser(email : String) : LiveData<User>

    @Query("select * from tbl_user")
    fun getAllUser() : LiveData<List<User>>
}