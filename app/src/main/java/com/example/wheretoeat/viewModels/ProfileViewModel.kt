package com.example.wheretoeat.viewModels

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.adapters.FavoritesRecyclerViewAdapter

class ProfileViewModel : ViewModel() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FavoritesRecyclerViewAdapter
}