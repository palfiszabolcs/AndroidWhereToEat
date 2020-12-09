package com.example.wheretoeat.fragments.API

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class ResponseData(
    val total_entries: Int,
    val per_page: Int,
    val current_page: Int,
    val restaurants: ArrayList<RestaurantData>
)

@Entity(tableName = "favorites")
data class RestaurantData(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val area: String = "",
    val postal_code: String = "",
    val country: String = "",
    val phone: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val price: Int = 0,
    val reserve_url: String = "",
    val mobile_reserve_url: String = "",
    val image_url: String = "",
    var favorite: Boolean = false
) : Serializable