package com.example.wheretoeat.database.dao
import androidx.room.*
import com.example.wheretoeat.database.UserData

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