package com.example.wheretoeat.fragments.dashboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.Database.FavoritesDatabase
import com.example.wheretoeat.Database.FavoritesRepository
import com.example.wheretoeat.RecyclerViewAdapter
import com.example.wheretoeat.fragments.API.ApiRequests
import com.example.wheretoeat.fragments.API.RestaurantData
import com.example.wheretoeat.fragments.API.RetrofitClient
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
//
        var restaurants = ArrayList<RestaurantData>()
        var favorites : LiveData<List<RestaurantData>>
        val dao = FavoritesDatabase.getDatabase(application).favoritesDao()
        val favRepo = FavoritesRepository(dao)

        lateinit var recyclerView: RecyclerView
        lateinit var adapter:RecyclerViewAdapter
        var req = RetrofitClient.retrofit.create(ApiRequests::class.java)
        var isLastPage:Boolean = false //false
        var isLoading :Boolean = false //false
        var currentPage = 1
        var searchParam = ""

        init {
                favorites = favRepo.favorites
        }

        fun reinitCounters() {
                isLastPage = false //false
                isLoading = false //false
                currentPage = 1
        }

        fun addToFavorites(restaurant: RestaurantData){
                viewModelScope.launch {
                        favRepo.addRestaurant(restaurant)
                }
        }

        fun deleteFromFavorites(restaurant: Int){
                viewModelScope.launch {
                        favRepo.deleteRestaurant(restaurant)
                }
        }


//


}