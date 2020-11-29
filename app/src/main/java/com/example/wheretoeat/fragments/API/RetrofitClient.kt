package com.example.wheretoeat.fragments.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
        companion object{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://opentable.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
}