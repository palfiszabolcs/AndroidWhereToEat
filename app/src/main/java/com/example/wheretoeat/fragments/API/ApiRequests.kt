package com.example.wheretoeat.fragments.API

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET

interface ApiRequests {

    @GET("restaurants?state=ca")
    fun create(): Call<CountryData>
}