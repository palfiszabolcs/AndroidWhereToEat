package com.example.wheretoeat.fragments.home

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.wheretoeat.RecyclerViewAdapter
import com.example.wheretoeat.fragments.API.ApiRequests
import com.example.wheretoeat.fragments.API.RestaurantData
import com.example.wheretoeat.fragments.API.RetrofitClient

class ProfileViewModel : ViewModel() {

    var restaurants = ArrayList<RestaurantData>()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerViewAdapter
    var req = RetrofitClient.retrofit.create(ApiRequests::class.java)
    var isLastPage:Boolean = false //false
    var isLoading :Boolean = false //false
    var currentPage = 1
    var searchParam = ""
}