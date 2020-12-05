package com.example.wheretoeat.fragments.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequests {

    @GET("restaurants?state=ca")
    fun load(@Query("page") page: Int): Call<ResponseData>

    @GET("restaurants?state=ca")
    fun search(@Query("name") name: String, @Query("page") page: Int): Call<ResponseData>
}