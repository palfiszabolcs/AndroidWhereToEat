package com.example.wheretoeat.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.wheretoeat.Database.FavoritesDatabase
import com.example.wheretoeat.Database.FavoritesDatabase.Companion.getDatabase
import com.example.wheretoeat.Database.FavoritesRepository
import com.example.wheretoeat.FavoritesRecyclerViewAdapter
import com.example.wheretoeat.RecyclerViewAdapter
import com.example.wheretoeat.fragments.API.ApiRequests
import com.example.wheretoeat.fragments.API.RestaurantData
import com.example.wheretoeat.fragments.API.RetrofitClient
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FavoritesRecyclerViewAdapter

}