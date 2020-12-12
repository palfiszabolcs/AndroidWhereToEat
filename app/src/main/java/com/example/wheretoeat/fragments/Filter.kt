package com.example.wheretoeat.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.wheretoeat.R
import com.example.wheretoeat.fragments.API.ApiRequests
import com.example.wheretoeat.fragments.API.CitiesData
import com.example.wheretoeat.fragments.API.RetrofitClient
import com.example.wheretoeat.fragments.dashboard.DashboardFragmentDirections
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Filter : Fragment() {

    var req = RetrofitClient.retrofit.create(ApiRequests::class.java)
    lateinit var cities : List<String>
    private val prices = listOf("1", "2", "3", "4")

    fun loadCities(view: View, spinner: Spinner){

        req.getCities().enqueue(object : Callback<CitiesData> {
            override fun onFailure(call: Call<CitiesData>, t: Throwable) {
                Toast.makeText(view.context, "Failed request" + t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<CitiesData>, response: Response<CitiesData>) {
                cities = response.body()!!.cities
                Log.d("cities", cities.toString())
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, cities)
                spinner.adapter = adapter
            }
        })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_filter, container, false)
        val citiesSpinner = view.findViewById<Spinner>(R.id.dropdown_city)
        loadCities(view, citiesSpinner)
        val priceSpinner = view.findViewById<Spinner>(R.id.dropdown_price)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, prices)
        priceSpinner.adapter = adapter

        val applyButton = view.findViewById<Button>(R.id.applyFilter_button)
        applyButton.setOnClickListener {
            val city = citiesSpinner.selectedItem.toString()
            val price = priceSpinner.selectedItem.toString()
            findNavController().navigate(FilterDirections.actionFilterToNavigationDashboard(city, price))

//            Toast.makeText(requireContext(), city + price, Toast.LENGTH_LONG).show()
        }


        return view
    }


}
