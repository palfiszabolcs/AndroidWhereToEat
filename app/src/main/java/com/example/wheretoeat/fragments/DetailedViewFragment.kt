package com.example.wheretoeat.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.wheretoeat.R
import java.util.*


class DetailedViewFragment : Fragment() {

    val arg: DetailedViewFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detailed_view_menu, menu)
        val favorite = menu.findItem(R.id.detailed_favorite)
        favorite.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_off)
    }

    var favorite = false
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(favorite == false){
            item.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_on))
            favorite=true;
        }else{
            item.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_off))
            favorite = false;
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detailed_view, container, false)
        val image = view.findViewById<ImageView>(R.id.detailed_image)
        val address = view.findViewById<TextView>(R.id.address_text)
        val city = view.findViewById<TextView>(R.id.city_text)
        val area = view.findViewById<TextView>(R.id.area_text)
        val state = view.findViewById<TextView>(R.id.state_text)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val callButton = view.findViewById<Button>(R.id.call_button)
        val reserveButton = view.findViewById<Button>(R.id.reserve_button)
        val mapButton = view.findViewById<Button>(R.id.map_button)


        ratingBar.rating = arg.restaurant.price.toFloat()
        ratingBar.setIsIndicator(true)

        val request = Glide.with(this)
        request.load(arg.restaurant.image_url).into(image)
        address.text = "Address: " + arg.restaurant.address
        city.text = "City: " + arg.restaurant.city
        area.text = "Area: " + arg.restaurant.area
        state.text = "State: " + arg.restaurant.state

        callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + arg.restaurant.phone)
            startActivity(intent)
        }

        reserveButton.setOnClickListener {
            Toast.makeText(this.context, "Feature not available", Toast.LENGTH_SHORT).show()
        }

        mapButton.setOnClickListener {
            val uri = "http://maps.google.com/maps?q=loc:" + arg.restaurant.lat.toString() + "," + arg.restaurant.lng.toString()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = arg.restaurant.name
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}