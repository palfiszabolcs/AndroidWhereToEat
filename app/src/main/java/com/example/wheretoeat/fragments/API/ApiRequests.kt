package com.example.wheretoeat.fragments.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequests {

    @GET("restaurants?country=US")
    fun load(@Query("page") page: Int): Call<ResponseData>

    @GET("restaurants?country=US")
    fun search(@Query("name") name: String, @Query("page") page: Int): Call<ResponseData>

    @GET("cities")
    fun getCities() : Call<CitiesData>

    @GET("restaurants?country=US")
    fun filter(@Query("city") city: String, @Query("price") price: String): Call<ResponseData>
}