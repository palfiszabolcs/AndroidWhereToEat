package com.example.wheretoeat.fragments.dashboard

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheretoeat.DashboardItemData
import com.example.wheretoeat.R
import com.example.wheretoeat.RecyclerViewAdapter
import com.example.wheretoeat.fragments.API.ApiRequests
import com.example.wheretoeat.fragments.API.CountryData
import com.example.wheretoeat.fragments.API.RestaurantData
import com.example.wheretoeat.fragments.API.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        val restaurants = ArrayList<CountryData>()
//        restaurants += DashboardItemData(1, "title1", "address1", "4")
//        restaurants += DashboardItemData(2, "title2", "address2", "4")
//        restaurants += DashboardItemData(3, "title3", "address3", "4")
//        restaurants += DashboardItemData(4, "title4", "address4", "4")
//        restaurants += DashboardItemData(5, "title5", "address5", "4")
//        restaurants += DashboardItemData(6, "title6", "address6", "4")
//        restaurants += DashboardItemData(6, "title7", "address7", "4")
//        restaurants += DashboardItemData(6, "title8", "address8", "4")
//        restaurants += DashboardItemData(6, "title9", "address9", "4")
//        restaurants += DashboardItemData(6, "title10", "address10", "4")
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view)


        val restaurant = RetrofitClient.retrofit.create(ApiRequests::class.java)
        restaurant.create().enqueue(object: Callback<CountryData> {

            override fun onFailure(call: Call<CountryData>, t: Throwable) {
                Log.d("rest", t.toString())
            }

            override fun onResponse(call: Call<CountryData>, response: Response<CountryData>) {
                Log.d("rest", response.body()!!.restaurants.toString())
                recyclerView.adapter = context?.let { RecyclerViewAdapter(it, response.body()!!.restaurants) }
            }
        })

//        Log.d("rest", rest.toString())
//        recyclerView.adapter = this.context?.let { RecyclerViewAdapter(it, rest) }
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.hasFixedSize()

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Dashboard"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.app_bar_menu, menu)
    }


}