package com.example.wheretoeat

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wheretoeat.fragments.API.RestaurantData

class RecyclerViewAdapter(val context: Context, val restaurants: ArrayList<RestaurantData>, private var listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>(){
//class RecyclerViewAdapter(val context: Context, val restaurants: ArrayList<RestaurantData>) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>(){
    var favoriteList = ArrayList<RestaurantData>()

    interface Listener{
        fun onClick(restaurant: RestaurantData)
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

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
            listener.onClick(currentItem)
        }
        val request = Glide.with(context)
        request.load(currentItem.image_url).into(holder.card_image)
        holder.card_title.text = currentItem.name
        holder.card_address.text = currentItem.address
        holder.card_price.rating = currentItem.price.toFloat()
        holder.card_favorite_button.isChecked = favoriteList.contains(currentItem)
        holder.card_favorite_button.setOnClickListener{
            if(holder.card_favorite_button.isChecked){
                favoriteList.add(currentItem)
                Log.d("fav", favoriteList.toString())
            }else{
                favoriteList.remove(currentItem)
                Log.d("fav", favoriteList.toString())
            }
        }
    }

    override fun getItemCount() = restaurants.size

    fun addMoreItems(list : ArrayList<RestaurantData>){
        restaurants.addAll(list)
        notifyDataSetChanged()
    }

    fun addMoreSearchItems(list : ArrayList<RestaurantData>){
        restaurants.addAll(list)
        notifyDataSetChanged()
    }

    fun addSearch(list : ArrayList<RestaurantData>){
        restaurants.clear()
        restaurants.addAll(list)
        notifyDataSetChanged()
    }


}
