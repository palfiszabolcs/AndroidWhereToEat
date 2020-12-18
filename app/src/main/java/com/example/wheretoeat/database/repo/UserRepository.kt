package com.example.wheretoeat.database.repo

import com.example.wheretoeat.database.dao.UserDataAccessObject
import com.example.wheretoeat.database.UserData

class UserRepository(private val userDao: UserDataAccessObject) {

    suspend fun addUser(user: UserData){
        userDao.addUser(user)
    }

    fun readAll(){
        userDao.readAll()
    }

    suspend fun updateUser(user: UserData){
        userDao.updateUser(user)
    }
}