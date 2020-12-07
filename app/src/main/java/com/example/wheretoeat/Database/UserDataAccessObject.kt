package com.example.wheretoeat.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

const val userId = 1;
@Dao
interface UserDataAccessObject {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: UserData)

    @Query("SELECT * FROM user WHERE id like $userId")
    fun readUserData(): LiveData<UserData>
}