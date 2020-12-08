package com.example.wheretoeat.Database

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val repository: UserRepository
//    val allData: List<UserData>
    lateinit var currentUser: UserData

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
//        allData = repository.allUsers
//        Log.d("read",repository.allUsers.toString())
    }

    fun addUser(user: UserData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun readAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.readAll()
        }
    }
}
