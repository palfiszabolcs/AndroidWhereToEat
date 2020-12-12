package com.example.wheretoeat.fragments.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
        companion object{
            val retrofit = Retrofit.Builder()
//                .baseUrl("https://opentable.herokuapp.com/api/")
                .baseUrl("https://ratpark-api.imok.space/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
}