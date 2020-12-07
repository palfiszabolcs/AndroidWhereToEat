package com.example.wheretoeat.Database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val readData: LiveData<UserData>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readData = repository.readUser
    }

    fun addUser(user: UserData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }
}