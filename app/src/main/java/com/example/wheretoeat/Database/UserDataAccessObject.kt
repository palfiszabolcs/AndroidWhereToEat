package com.example.wheretoeat.Database

import androidx.lifecycle.LiveData
import androidx.room.*

const val userId = 1;
@Dao
interface UserDataAccessObject {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: UserData)

    @Query("SELECT* FROM user")
    fun readAll(): List<UserData>

    @Query("SELECT * FROM user WHERE uid IN (:userID)")
    fun readUserData(userID: Int): UserData

    @Update(entity = UserData::class)
    suspend fun updateUser(user: UserData)
}