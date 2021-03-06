package com.example.wheretoeat.activities

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wheretoeat.database.UserData
import com.example.wheretoeat.viewModels.UserViewModel
import com.example.wheretoeat.R

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

//        uncomment funtion to add user on first run
//        addFirstUser()

        navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_dashboard -> {
                    navView.visibility = View.VISIBLE
                }

                R.id.navigation_profile -> {
                    navView.visibility = View.VISIBLE
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                else ->{
                    navView.visibility = View.GONE
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)

                }
            }
        }
        navView.setupWithNavController(navController)


    }

    fun addFirstUser(){
        val userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val thread = Thread{
        val user = UserData(
            1,
            "Palfi Szabolcs",
            "Saromberke 517p",
            "0712345678",
            "palfiszabolcs@icloud.com",
            null,
        )
        userViewModel.addUser(user)
        }
        thread.start()
        thread.join()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}