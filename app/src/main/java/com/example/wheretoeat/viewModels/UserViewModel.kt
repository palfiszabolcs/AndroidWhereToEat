package com.example.wheretoeat.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.wheretoeat.database.FavoritesDatabase
import com.example.wheretoeat.database.repo.FavoritesRepository
import com.example.wheretoeat.database.repo.UserRepository
import com.example.wheretoeat.database.UserData
import com.example.wheretoeat.database.UserDatabase
import com.example.wheretoeat.fragments.API.RestaurantData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val userRepository: UserRepository
    val favRepo: FavoritesRepository
    lateinit var currentUser: UserData
    var favorites: LiveData<List<RestaurantData>>

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        val favDao = FavoritesDatabase.getDatabase(application).favoritesDao()
        userRepository = UserRepository(userDao)
        favRepo = FavoritesRepository(favDao)
        favorites = favRepo.favorites
    }

    fun addUser(user: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUser(user)
        }
    }

    fun updateUser(user: UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUser(user)
        }
    }

    fun addToFavorites(restaurant: RestaurantData) {
        viewModelScope.launch {
            favRepo.addRestaurant(restaurant)
        }
    }

    fun deleteFromFavorites(restaurant: Int) {
        viewModelScope.launch {
            favRepo.deleteRestaurant(restaurant)
        }
    }
}
