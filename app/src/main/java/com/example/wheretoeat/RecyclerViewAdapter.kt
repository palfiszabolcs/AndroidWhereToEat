package com.example.wheretoeat

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

class RecyclerViewAdapter(val context: Context, restaurantsList: ArrayList<RestaurantData>, private var listener: Listener?) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    private val restaurants: ArrayList<RestaurantData> = restaurantsList

    interface Listener {
        fun onClick(restaurant: RestaurantData)
        fun addToFavorites(restaurant: RestaurantData)
        fun deleteFromFavorites(restaurant: RestaurantData)
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card = itemView.findViewById<CardView>(R.id.card)
        val card_image = itemView.findViewById<ImageView>(R.id.card_image)
        val card_title = itemView.findViewById<TextView>(R.id.card_title)
        val card_address = itemView.findViewById<TextView>(R.id.card_address)
        val card_price = itemView.findViewById<RatingBar>(R.id.card_price)
        val card_favorite_button = itemView.findViewById<ToggleButton>(R.id.card_favorite_button)
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
        request.load(currentItem.image_url).into(holder.card_image)
        holder.card_title.text = currentItem.name
        holder.card_address.text = currentItem.address
        holder.card_price.rating = currentItem.price.toFloat()
        holder.card_favorite_button.isChecked = currentItem.favorite

//        Log.d("holder", currentItem.favorite.toString())
//        Log.d("holder", favorites.toString())

        holder.card_favorite_button.setOnClickListener {
            if (holder.card_favorite_button.isChecked) {
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
//        Log.d("filter", "adapter" + list.toString())
        restaurants.clear()
//        Log.d("filter", "adapter - after clear" + restaurants.toString())
        restaurants.addAll(list)
//        Log.d("filter", "adapter - after add" + restaurants.toString())
        notifyDataSetChanged()
        Log.d("filter", "adapter - after notify" + restaurants.toString())
    }

}
