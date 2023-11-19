package com.esa.foodrecipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esa.foodrecipes.data.room.FavoriteItem
import com.esa.foodrecipes.databinding.ItemFoodBinding

class FavoriteAdapter(private var food: List<FavoriteItem?>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    var onItemClick: ((FavoriteItem) -> Unit)? = null
    fun updateData(newFood: List<FavoriteItem?>) {
        food = newFood
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemsBinding =
            ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = food[position]
        if (food != null) {
            holder.bind(food)
        }
        holder.itemView.setOnClickListener {
            food?.let { onItemClick?.invoke(it) }
        }
    }

    override fun getItemCount(): Int {
        return food.size
    }

    class ViewHolder(val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: FavoriteItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(food.imageUrl)
                    .into(poster)
                tvTitle.text = food.name
            }
        }
    }
}