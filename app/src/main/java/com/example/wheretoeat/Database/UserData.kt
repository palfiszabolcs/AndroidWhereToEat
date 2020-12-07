package com.example.wheretoeat.Database

import android.graphics.Picture
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.wheretoeat.fragments.API.RestaurantData

@Entity(tableName = "user")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    @Ignore val profilePic: Picture?,
    val address: String = "",
    val phoneNum: String = "",
    val email: String = "",
    @Ignore val favorites: ArrayList<RestaurantData>?
)