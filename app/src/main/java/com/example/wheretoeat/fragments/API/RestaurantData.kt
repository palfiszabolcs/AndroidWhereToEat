package com.example.wheretoeat.fragments.API

import java.io.Serializable

data class ResponseData(
    val total_entries: Int,
    val per_page: Int,
    val current_page: Int,
    val restaurants: ArrayList<RestaurantData>
)

data class RestaurantData(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val state: String,
    val area: String,
    val postal_code: String,
    val country: String,
    val phone: String,
    val lat: Double,
    val lng: Double,
    val price: Int,
    val reserve_url: String,
    val mobile_reserve_url: String,
    val image_url: String,
) : Serializable