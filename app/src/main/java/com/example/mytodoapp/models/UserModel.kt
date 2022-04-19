package com.example.mytodoapp.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.db.TodoAppDB
import com.example.mytodoapp.entities.User
import kotlinx.coroutines.launch

class UserModel(application: Application) : AndroidViewModel(application) {
    private val userDao = TodoAppDB.getDB(application).getUserDao()
    val errorMessageLD : MutableLiveData<String> = MutableLiveData()
    var user : User? = null

    fun login(email:String, password: String, callBack: (Long) -> Unit){
        viewModelScope.launch {
            user = userDao.getUser(email)
            if(user != null){
                if(user!!.password == password){
                    callBack(user!!.userId)
                }else{
                    errorMessageLD.value = "Incorrect Password"
                }
            }else{
                errorMessageLD.value = "Email does not exist"
            }
        }
    }

    fun register(newUser : User, callBack: (Long) -> Unit){
        viewModelScope.launch {
            user = userDao.getUser(newUser.email)
            if(user != null){
                errorMessageLD.value = "Email already exist"
            }else{
                val rowId = userDao.insertUser(newUser)
                user = newUser.apply {
                    userId = rowId
                }
                callBack(rowId)
            }
        }
    }
}