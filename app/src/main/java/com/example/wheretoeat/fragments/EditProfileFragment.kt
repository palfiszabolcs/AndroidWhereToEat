package com.example.wheretoeat.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.wheretoeat.database.UserData
import com.example.wheretoeat.database.UserDatabase
import com.example.wheretoeat.viewModels.UserViewModel
import com.example.wheretoeat.R


class EditProfileFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var phone: EditText
    lateinit var address: EditText
    lateinit var image: ImageView
    lateinit var saveButton : Button
    lateinit var changeButton : Button
    var imageUri : String? = null

    private fun loadUserData() {
        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            UserDatabase::class.java,
            "user"
        ).build()
        val thread = Thread{
            userViewModel.currentUser = db.userDao().readUserData(1)

            name.setText(userViewModel.currentUser.name)
            email.setText(userViewModel.currentUser.email)
            phone.setText(userViewModel.currentUser.phone)
            address.setText(userViewModel.currentUser.address)
            image.setImageURI(userViewModel.currentUser.image?.toUri())
            imageUri = userViewModel.currentUser.image
        }
        thread.start()
        thread.join()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Edit Profile"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        name = view.findViewById(R.id.edit_profile_name)
        email = view.findViewById(R.id.edit_profile_email)
        phone = view.findViewById(R.id.edit_profile_phone)
        address = view.findViewById(R.id.edit_profile_address)
        saveButton = view.findViewById(R.id.save_button)
        changeButton = view.findViewById(R.id.change_button)
        image = view.findViewById(R.id.edit_profile_picture)
        loadUserData()

        changeButton.setOnClickListener {
            pickImageFromGallery()
        }

        saveButton.setOnClickListener {
            val user = UserData(
                1,
                name.text.toString(),
                address.text.toString(),
                phone.text.toString(),
                email.text.toString(),
                imageUri,
            )
            userViewModel.updateUser(user)
            findNavController().popBackStack()
        }
        return view;
    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, 666)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 666) {
            imageUri = data?.data.toString()
            image.setImageURI(imageUri!!.toUri())
        }
    }
}