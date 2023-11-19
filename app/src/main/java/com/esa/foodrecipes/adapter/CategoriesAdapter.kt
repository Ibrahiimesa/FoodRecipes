package com.esa.foodrecipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esa.foodrecipes.data.response.CategoriesItem
import com.esa.foodrecipes.databinding.ItemCategoriesBinding

class CategoriesAdapter(private var categories: List<CategoriesItem?>) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    var onItemClick: ((CategoriesItem) -> Unit)? = null
    fun updateData(newCategories: List<CategoriesItem?>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemsBinding =
            ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        if (category != null) {
            holder.bind(category)
        }
        holder.itemView.setOnClickListener {
            category?.let { onItemClick?.invoke(it) }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class ViewHolder(val binding: ItemCategoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoriesItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .asBitmap()
                    .load(category.strCategoryThumb)
                    .into(poster)
                tvTitle.text = category.strCategory
            }
        }
    }
}