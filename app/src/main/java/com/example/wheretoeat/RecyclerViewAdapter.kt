package com.example.wheretoeat

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wheretoeat.fragments.API.CountryData
import com.example.wheretoeat.fragments.API.RestaurantData

class RecyclerViewAdapter(val context: Context, val restaurants: ArrayList<RestaurantData>) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>(){
    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val card_image = itemView.findViewById<ImageView>(R.id.card_image)
        val card_title = itemView.findViewById<TextView>(R.id.card_title)
        val card_address = itemView.findViewById<TextView>(R.id.card_address)
        val card_price = itemView.findViewById<TextView>(R.id.card_price)
        val card_favorite_button = itemView.findViewById<ImageButton>(R.id.card_farvorite_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view, parent, false)
        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = restaurants[position]

        val request = Glide.with(context)
        request.load(currentItem.image_url).into(holder.card_image)
        holder.card_title.text = currentItem.name
        holder.card_address.text = currentItem.address
        holder.card_price.text = currentItem.price.toString()
        Log.d("rest", currentItem.name )
    }

    override fun getItemCount() = restaurants.size

}
