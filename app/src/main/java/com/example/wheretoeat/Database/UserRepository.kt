package com.example.wheretoeat.Database

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDataAccessObject) {
//    val readUser: LiveData<UserData> = userDao.readUserData()
//    lateinit var allUsers: List<UserData>


    suspend
    fun addUser(user: UserData){
        userDao.addUser(user)
    }

    fun readAll(){
        userDao.readAll()
    }
}