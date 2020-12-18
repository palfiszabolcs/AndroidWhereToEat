package com.example.wheretoeat.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.wheretoeat.database.FavoritesDatabase
import com.example.wheretoeat.database.UserDatabase
import com.example.wheretoeat.viewModels.UserViewModel
import com.example.wheretoeat.adapters.FavoritesRecyclerViewAdapter
import com.example.wheretoeat.R
import com.example.wheretoeat.fragments.API.RestaurantData
import com.example.wheretoeat.viewModels.ProfileViewModel


class ProfileFragment : Fragment(), FavoritesRecyclerViewAdapter.Listener {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var userViewModel: UserViewModel
    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var phone: TextView
    lateinit var address: TextView
    lateinit var image: ImageView

    private fun loadFavorites(){
        val db = Room.databaseBuilder(
                requireContext().applicationContext,
                FavoritesDatabase::class.java,
                "favorites"
        ).build()
        val thread = Thread{
            userViewModel.favorites = db.favoritesDao().getFavorites()
        }
        thread.start()

    }
    private fun loadUserData() {
        val db = Room.databaseBuilder(
                requireContext().applicationContext,
                UserDatabase::class.java,
                "user"
        ).build()
        val thread = Thread{
            userViewModel.currentUser = db.userDao().readUserData(1)
            name.text = userViewModel.currentUser.name
            email.text = userViewModel.currentUser.email
            phone.text = userViewModel.currentUser.phone
            address.text = userViewModel.currentUser.address
            if(userViewModel.currentUser.image != null){
                image.setImageURI(userViewModel.currentUser.image!!.toUri())
            }
        }
        thread.start()
        thread.join()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        name = view.findViewById(R.id.profile_name)
        email = view.findViewById(R.id.profile_email)
        phone = view.findViewById(R.id.profile_phone)
        address = view.findViewById(R.id.profile_address)
        image = view.findViewById(R.id.profile_picture)

        loadUserData()
        loadFavorites()

        profileViewModel.recyclerView = view.findViewById(R.id.profile_recycler)
        val params: ViewGroup.LayoutParams = profileViewModel.recyclerView.layoutParams
        params.height = RecyclerView.LayoutParams.WRAP_CONTENT
        profileViewModel.recyclerView.layoutParams = params
        profileViewModel.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        profileViewModel.recyclerView.hasFixedSize()
        profileViewModel.adapter = FavoritesRecyclerViewAdapter(requireContext(), this@ProfileFragment)
        profileViewModel.recyclerView.adapter = profileViewModel.adapter

        userViewModel.favorites.observe(this.viewLifecycleOwner, {
            profileViewModel.adapter.setData(it)
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Profile"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        setHasOptionsMenu(true);
    }

    override fun onClick(restaurant: RestaurantData) {
        findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToDetailedView(restaurant))
    }

    override fun addToFavorites(restaurant: RestaurantData) {
        userViewModel.addToFavorites(restaurant)
    }

    override fun deleteFromFavorites(restaurant: RestaurantData) {
        userViewModel.deleteFromFavorites(restaurant.id)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_menu, menu)
        val editButton = menu.findItem(R.id.edit_profile_button)
        editButton.setOnMenuItemClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionNavigationProfileToEditProfileFragment())
            true
        }

    }

}