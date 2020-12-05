package com.example.wheretoeat.fragments.dashboard

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.RecyclerViewAdapter
import com.example.wheretoeat.fragments.API.ApiRequests
import com.example.wheretoeat.fragments.API.RestaurantData
import com.example.wheretoeat.fragments.API.RetrofitClient

class DashboardViewModel : ViewModel() {
//        val restaurants: MutableLiveData<ArrayList<RestaurantData>> by lazy {
//                MutableLiveData<ArrayList<RestaurantData>>()
//        }
//        var recyclerView = MutableLiveData<RecyclerView>()
//        var adapter = MutableLiveData<RecyclerViewAdapter>()
//        var isLastPage = MutableLiveData<Boolean>() //false
//        var isLoading = MutableLiveData<Boolean>() //false
//        var req = MutableLiveData<ApiRequests>() //RetrofitClient.retrofit.create(ApiRequests::class.java)
//        var currentPage = MutableLiveData<Int>()

//        init {
//                isLastPage.value = false //false
//                isLoading.value = false //false
//                req.value = RetrofitClient.retrofit.create(ApiRequests::class.java)
//                currentPage.value = 1
//        }

        var restaurants = ArrayList<RestaurantData>()
        lateinit var recyclerView: RecyclerView
        lateinit var adapter:RecyclerViewAdapter
        var req = RetrofitClient.retrofit.create(ApiRequests::class.java)
        var isLastPage:Boolean = false //false
        var isLoading :Boolean = false //false
        var currentPage = 1
        var searchParam = ""

        fun reinitCounters() {
                isLastPage = false //false
                isLoading = false //false
                currentPage = 1
        }


//        val recyclerView: RecyclerView by lazy {
//                RecyclerView()
//        }
//        private var restArray = ArrayList<RestaurantData>()
////    private val _text = MutableLiveData<String>().apply {
////        value = "This is dashboard Fragment"
////    }
////    val text: LiveData<String> = _text
//    init {
//        loadRestaurants()
//        restaurants!!.value = restArray
//    }
//
//    private fun loadRestaurants(){
//        val restaurant = RetrofitClient.retrofit.create(ApiRequests::class.java)
//        restaurant.create().enqueue(object : Callback<CountryData> {
//            override fun onFailure(call: Call<CountryData>, t: Throwable) {
//                Log.d("rest", t.toString())
//            }
//
//            override fun onResponse(call: Call<CountryData>, response: Response<CountryData>) {
//                //                Log.d("rest", response.body()!!.restaurants.toString())
//                restArray = response.body()!!.restaurants
//            }
//        })
//    }


}