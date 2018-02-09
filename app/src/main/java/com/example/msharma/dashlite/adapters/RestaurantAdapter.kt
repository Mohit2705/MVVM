package com.example.msharma.dashlite.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.msharma.dashlite.R
import com.example.msharma.dashlite.SharedPrefernceHelper
import com.example.msharma.dashlite.activities.DetailActivity
import com.example.msharma.dashlite.data.Restaurant


class RestaurantAdapter(val sharedPrefernceHelper: SharedPrefernceHelper) : RecyclerView.Adapter<ItemViewHolder>(), RecyclerViewClickListener {

    var favList = HashSet<String>()

    init {
        favList.addAll(sharedPrefernceHelper.getFavRestaurents())
    }

    override fun onFavortie(isFavorite: Boolean, position: Int) {
        val id = list[position].id
        if (isFavorite) {
            sharedPrefernceHelper.favoriteRestaurent(id)
        } else {
            sharedPrefernceHelper.unFavRestaurant(id)
        }
    }

    private var list: List<Restaurant> = emptyList()

    override fun onClick(view: View, position: Int) {
        DetailActivity.startDetailActivity(view.context, list[position].id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ItemViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.checkedItem.isChecked = favList.contains(list[position].id)
        holder.restaurantDesc.text = list[position].description
        holder.foodTime.text = list[position].duration
        holder.title.text = list[position].name
        Glide.with(holder.restaurantImage.context).load(list[position].imageUrl).into(holder.restaurantImage)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(items: List<Restaurant>) {
        list = items
        notifyDataSetChanged()
    }

}

interface RecyclerViewClickListener {
    fun onClick(view: View, position: Int)
    fun onFavortie(isFavorite: Boolean, position: Int)
}

class ItemViewHolder(view: View, private val recyclerViewClickListener: RecyclerViewClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {

    val restaurantImage: ImageView = view.findViewById(R.id.image)
    val title: TextView = view.findViewById(R.id.title)
    val restaurantDesc: TextView = view.findViewById(R.id.description)
    val foodTime: TextView = view.findViewById(R.id.time)
    val checkedItem: CheckBox = view.findViewById(R.id.checkBox)

    init {
        view.setOnClickListener(this)
        checkedItem.setOnCheckedChangeListener { _, isChecked ->
            recyclerViewClickListener.onFavortie(isChecked, adapterPosition)
        }

    }

    override fun onClick(view: View) {
        recyclerViewClickListener.onClick(view, adapterPosition)
    }

}
