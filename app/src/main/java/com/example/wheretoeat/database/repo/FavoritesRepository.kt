package com.example.wheretoeat.database.repo

import androidx.lifecycle.LiveData
import com.example.wheretoeat.database.dao.FavoritesDao
import com.example.wheretoeat.fragments.API.RestaurantData

class FavoritesRepository(private val favDao: FavoritesDao) {
    val favorites: LiveData<List<RestaurantData>> = favDao.getFavorites()

    suspend fun addRestaurant(restaurant: RestaurantData){
        favDao.addRestaurant(restaurant)
    }

    suspend fun deleteRestaurant(restaurant: Int){
        favDao.deleteRestaurant(restaurant)
    }

    fun getRestaurants(){
        favDao.getFavorites()
    }
}