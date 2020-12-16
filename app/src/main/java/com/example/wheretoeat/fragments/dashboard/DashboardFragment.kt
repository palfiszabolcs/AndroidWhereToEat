package com.example.wheretoeat.fragments.dashboard

import PagingListener
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.wheretoeat.Database.FavoritesDatabase
import com.example.wheretoeat.R
import com.example.wheretoeat.RecyclerViewAdapter
import com.example.wheretoeat.fragments.API.ResponseData
import com.example.wheretoeat.fragments.API.RestaurantData
import com.example.wheretoeat.fragments.DetailedViewFragmentArgs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardFragment() : Fragment(), RecyclerViewAdapter.Listener, SearchView.OnQueryTextListener {


    private lateinit var dashboardViewModel: DashboardViewModel

    private fun loadFavorites(){
        val db = Room.databaseBuilder(
                requireContext().applicationContext,
                FavoritesDatabase::class.java,
                "favorites"
        ).build()
        val thread = Thread{
            dashboardViewModel.favorites = db.favoritesDao().getFavorites()
        }
        thread.start()
    }

    fun checkFavorite(favorites: List<RestaurantData>){
//        Log.d("fav", dashboardViewModel.restaurants.size.toString())
        favorites.forEach {
            it.favorite = false
        }

        dashboardViewModel.restaurants.forEach{
//            Log.d("fav", favorites.contains(it).toString())
            it.favorite = favorites.contains(it)
        }

    }

    private fun loadData(view: View){
        loadFavorites()

        dashboardViewModel.recyclerView = view.findViewById(R.id.recycler_view)
        dashboardViewModel.adapter = RecyclerViewAdapter(view.context, dashboardViewModel.restaurants, this)
        dashboardViewModel.recyclerView.layoutManager = LinearLayoutManager(this.context)
        dashboardViewModel.recyclerView.hasFixedSize()
        dashboardViewModel.req.load(1).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(view.context, "Failed request", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                dashboardViewModel.restaurants = response.body()!!.restaurants

                dashboardViewModel.favorites.observe(viewLifecycleOwner, {
                    checkFavorite(it)
                    dashboardViewModel.adapter.notifyDataSetChanged()
                })

                dashboardViewModel.adapter = RecyclerViewAdapter(view.context, dashboardViewModel.restaurants, this@DashboardFragment)
                dashboardViewModel.recyclerView.adapter = dashboardViewModel.adapter
            }
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val args: DashboardFragmentArgs? by navArgs()
//        Log.d("nav", args.toString())
        if((args?.city != "-") and (args?.price != "-")){
//            Toast.makeText(requireContext(), "filter", Toast.LENGTH_LONG).show()
            Log.d("filter", "filter")
            filter(view, args!!.city, args!!.price)
        }else{
            Log.d("filter", "loadData")
            loadData(view)
        }

        return view
    }


    private fun getMoreItems() {
        dashboardViewModel.req.load(dashboardViewModel.currentPage+1).enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {

                    if ( response.body()!!.total_entries >= dashboardViewModel.adapter.itemCount ){
//                        dashboardViewModel.restaurants.addAll(response.body()!!.restaurants)
                        dashboardViewModel.adapter.addMoreItems(response.body()!!.restaurants)
                    }
                    else{
                        Toast.makeText(view?.context, "No more restaurants!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(view?.context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        dashboardViewModel.currentPage++
        dashboardViewModel.isLoading = false

    }
    private fun getMoreFilterItems(city: String, price: String) {
        dashboardViewModel.req.filter(city, price, dashboardViewModel.currentPage+1).enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {

                    if ( response.body()!!.total_entries >= dashboardViewModel.adapter.itemCount ){
//                        dashboardViewModel.restaurants.addAll(response.body()!!.restaurants)
                        dashboardViewModel.adapter.addMoreItems(response.body()!!.restaurants)
                    }
                    else{
                        Toast.makeText(view?.context, "No more restaurants!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(view?.context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        dashboardViewModel.currentPage++
        dashboardViewModel.isLoading = false

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Dashboard"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val args: DashboardFragmentArgs? by navArgs()

        val layout_manager = dashboardViewModel.recyclerView.layoutManager
        dashboardViewModel.recyclerView.addOnScrollListener(object : PagingListener(layout_manager as LinearLayoutManager) {
            override fun isLastPage(): Boolean {
                return dashboardViewModel.isLastPage
            }

            override fun isLoading(): Boolean {
                return dashboardViewModel.isLoading
            }

            override fun loadMoreItems() {
                dashboardViewModel.isLoading = true
                if(dashboardViewModel.searchParam != ""){
                   getMoreSearchItems()
                }
                if((args?.city != "-") and (args?.price != "-")){
                    getMoreFilterItems(args!!.city, args!!.price)
                }
                else{
                    getMoreItems()
                }
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        setHasOptionsMenu(true);
    }

    private fun search(searchText: String) {
        dashboardViewModel.req.search(searchText, dashboardViewModel.currentPage).enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                    dashboardViewModel.adapter.addSearch(response.body()!!.restaurants)
//                    dashboardViewModel.adapter.addMoreSearchItems(response.body()!!.restaurants)
                }
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(view?.context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        dashboardViewModel.currentPage++
        dashboardViewModel.isLoading = false
    }

    fun getMoreSearchItems(){
        dashboardViewModel.req.search(dashboardViewModel.searchParam, dashboardViewModel.currentPage).enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
                        if ( response.body()!!.total_entries >= dashboardViewModel.adapter.itemCount ){
//                        dashboardViewModel.adapter.addSearch(response.body()!!.restaurants)
                        dashboardViewModel.adapter.addMoreSearchItems(response.body()!!.restaurants)
                    }
                    else{
                        Toast.makeText(view?.context, "No more restaurants to load!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(view?.context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        dashboardViewModel.currentPage++
        dashboardViewModel.isLoading = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.app_bar_menu, menu)
        val search = menu.findItem(R.id.app_bar_search)
        val filter = menu.findItem(R.id.app_bar_filter)
        val searchView = search.actionView as SearchView
//        searchView.setOnCloseListener {
//            dashboardViewModel.searchParam = "";
//            dashboardViewModel.restaurants.clear()
//            dashboardViewModel.reinitCounters()
//            loadData(requireView())
//            false;
//        }
        searchView.setOnQueryTextListener(this)
        filter.setOnMenuItemClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionNavigationDashboardToFilter())
            true
        }
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return if (p0 != null) {
            dashboardViewModel.reinitCounters()
            dashboardViewModel.searchParam = p0;
            search(p0)
            true;
        } else{
            Toast.makeText(this.context, "Please provide search text", Toast.LENGTH_SHORT).show()
            false;
        }
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false;
    }

    override fun onClick(restaurant: RestaurantData) {
        findNavController().navigate(DashboardFragmentDirections.actionNavigationDashboardToDetailedView(restaurant))
    }

    override fun addToFavorites(restaurant: RestaurantData) {
        restaurant.favorite = true
        dashboardViewModel.addToFavorites(restaurant)
    }

    override fun deleteFromFavorites(restaurant: RestaurantData) {
        dashboardViewModel.deleteFromFavorites(restaurant.id)
    }

    private fun filter(view: View, city: String, price: String) {
        dashboardViewModel.recyclerView = view.findViewById(R.id.recycler_view)
        dashboardViewModel.adapter = RecyclerViewAdapter(view.context, dashboardViewModel.restaurants, null)
        dashboardViewModel.recyclerView.layoutManager = LinearLayoutManager(this.context)
        dashboardViewModel.recyclerView.hasFixedSize()
        dashboardViewModel.recyclerView.adapter = dashboardViewModel.adapter
        dashboardViewModel.req.filter(city, price, 1).enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful) {
//                    Log.d("filter", response.body().toString())
                    dashboardViewModel.reinitCounters()
//                    dashboardViewModel.adapter.addSearch(response.body()!!.restaurants)
//                    dashboardViewModel.adapter.notifyDataSetChanged()
                    dashboardViewModel.adapter.addFilter(response.body()!!.restaurants)
                }
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(view.context, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })

    }
}