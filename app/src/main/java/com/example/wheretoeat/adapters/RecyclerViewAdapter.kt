package com.example.wheretoeat.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wheretoeat.fragments.API.RestaurantData
import com.example.wheretoeat.R

class RecyclerViewAdapter(val context: Context, restaurantsList: ArrayList<RestaurantData>, private var listener: Listener?) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    private val restaurants: ArrayList<RestaurantData> = restaurantsList

    interface Listener {
        fun onClick(restaurant: RestaurantData)
        fun addToFavorites(restaurant: RestaurantData)
        fun deleteFromFavorites(restaurant: RestaurantData)
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card = itemView.findViewById<CardView>(R.id.card)
        val cardImage = itemView.findViewById<ImageView>(R.id.card_image)
        val cardTitle = itemView.findViewById<TextView>(R.id.card_title)
        val cardAddress = itemView.findViewById<TextView>(R.id.card_address)
        val cardPrice = itemView.findViewById<RatingBar>(R.id.card_price)
        val cardFavoriteButton = itemView.findViewById<ToggleButton>(R.id.card_favorite_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view, parent, false)
        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentItem = restaurants[position]
        holder.card.setOnClickListener {
            listener?.onClick(currentItem)
        }
        val request = Glide.with(context)
        request.load(currentItem.image_url).into(holder.cardImage)
        holder.cardTitle.text = currentItem.name
        holder.cardAddress.text = currentItem.address
        holder.cardPrice.rating = currentItem.price.toFloat()
        holder.cardFavoriteButton.isChecked = currentItem.favorite

        holder.cardFavoriteButton.setOnClickListener {
            if (holder.cardFavoriteButton.isChecked) {
                listener?.addToFavorites(currentItem)
            } else {
                listener?.deleteFromFavorites(currentItem)
            }
        }
    }

    override fun getItemCount() = restaurants.size

    fun addMoreItems(list: ArrayList<RestaurantData>) {
        restaurants.addAll(list)
        notifyDataSetChanged()
    }

    fun addMoreSearchItems(list: ArrayList<RestaurantData>) {
        restaurants.addAll(list)
        notifyDataSetChanged()
    }

    fun addSearch(list: ArrayList<RestaurantData>) {
        restaurants.clear()
        restaurants.addAll(list)
        notifyDataSetChanged()
    }

    fun addFilter(list: ArrayList<RestaurantData>) {
        restaurants.clear()
        restaurants.addAll(list)
        notifyDataSetChanged()

    }

}
