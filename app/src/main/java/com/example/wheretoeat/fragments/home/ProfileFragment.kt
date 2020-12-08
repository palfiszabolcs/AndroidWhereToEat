package com.example.wheretoeat.fragments.home

import android.app.ActionBar
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.wheretoeat.Database.UserDatabase
import com.example.wheretoeat.Database.UserViewModel
import com.example.wheretoeat.R
import com.example.wheretoeat.RecyclerViewAdapter
import com.example.wheretoeat.fragments.API.ResponseData
import com.example.wheretoeat.fragments.API.RestaurantData
import com.example.wheretoeat.fragments.dashboard.DashboardFragmentDirections
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment(), RecyclerViewAdapter.Listener {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var userViewModel: UserViewModel
    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var phone: TextView
    lateinit var address: TextView
    lateinit var image: ImageView

    private fun loadFavorites(view: View){
        profileViewModel.recyclerView = view.findViewById(R.id.profile_recycler)
        val params: ViewGroup.LayoutParams = profileViewModel.recyclerView.layoutParams
        params.height = ActionBar.LayoutParams.WRAP_CONTENT
        profileViewModel.recyclerView.layoutParams = params
        profileViewModel.adapter = RecyclerViewAdapter(view.context, profileViewModel.restaurants, this)
        profileViewModel.req.load(1).enqueue(object : Callback<ResponseData> {

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(view.context, "Failed request", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                profileViewModel.restaurants = response.body()!!.restaurants
                profileViewModel.adapter = RecyclerViewAdapter(view.context, profileViewModel.restaurants, this@ProfileFragment)
                profileViewModel.recyclerView.adapter = profileViewModel.adapter
            }
        })
        profileViewModel.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

//        profileViewModel.recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL)
        profileViewModel.recyclerView.hasFixedSize()
    }
    private fun loadUserData() {
        val db = Room.databaseBuilder(
                requireContext().applicationContext,
                UserDatabase::class.java,
                "user"
        ).build()
        val thread = Thread{
            userViewModel.currentUser = db.userDao().readUserData(1)
//            Log.d("readAll", userViewModel.currentUser.toString())

            name.text = userViewModel.currentUser.name
            email.text = userViewModel.currentUser.email
            phone.text = userViewModel.currentUser.phone
            address.text = userViewModel.currentUser.address
//            image.setImageResource(R.drawable.dollar_grey)
        }
        thread.start()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        name = view.findViewById<TextView>(R.id.profile_name)
        email = view.findViewById<TextView>(R.id.profile_email)
        phone = view.findViewById<TextView>(R.id.profile_phone)
        address = view.findViewById<TextView>(R.id.profile_address)
        image = view.findViewById<ImageView>(R.id.profile_picture)

        loadFavorites(view)
        loadUserData()



        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Profile"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        setHasOptionsMenu(true);
    }

    override fun onClick(restaurant: RestaurantData) {
        findNavController().navigate(DashboardFragmentDirections.actionNavigationDashboardToDetailedView(restaurant))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_menu, menu)
        val editButton = menu.findItem(R.id.edit_profile_button)
        editButton.setOnMenuItemClickListener {
            Toast.makeText(requireContext(), "edit!", Toast.LENGTH_SHORT).show()
            false
        }

    }

}