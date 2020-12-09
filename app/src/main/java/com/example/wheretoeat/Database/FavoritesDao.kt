package com.example.wheretoeat.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wheretoeat.fragments.API.RestaurantData

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRestaurant(restaurant: RestaurantData)

    @Query("DELETE FROM favorites where id = (:restaurant)")
    suspend fun deleteRestaurant(restaurant: Int)

    @Query("SELECT * FROM favorites")
    fun getFavorites() : LiveData<List<RestaurantData>>
}