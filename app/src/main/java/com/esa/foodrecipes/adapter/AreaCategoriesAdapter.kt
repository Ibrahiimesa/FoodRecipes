package com.esa.foodrecipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.esa.foodrecipes.R
import com.esa.foodrecipes.data.response.AreaItem
import com.esa.foodrecipes.databinding.ItemAreaBinding

class AreaCategoriesAdapter(private var categories: List<AreaItem?>) :
    RecyclerView.Adapter<AreaCategoriesAdapter.ViewHolder>() {

    var onItemClick: ((AreaItem) -> Unit)? = null
    private var selectedPosition = RecyclerView.NO_POSITION
    fun updateData(newCategories: List<AreaItem?>) {
        categories = newCategories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemsBinding =
            ItemAreaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        if (category != null) {
            holder.bind(category, position)
        }

    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class ViewHolder(val binding: ItemAreaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: AreaItem, position: Int) {
            binding.apply {

                tvTitleArea.text = category.strArea

                if (position == 0 && selectedPosition == RecyclerView.NO_POSITION) {
                    root.setBackgroundResource(R.drawable.bg_rounded_selected)
                    tvTitleArea.setTextColor(ContextCompat.getColor(root.context, R.color.white))
                } else if (position == selectedPosition) {
                    root.setBackgroundResource(R.drawable.bg_rounded_selected)
                    tvTitleArea.setTextColor(ContextCompat.getColor(root.context, R.color.white))
                } else {
                    root.setBackgroundResource(R.drawable.bg_rounded_white)
                    tvTitleArea.setTextColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.purple_500
                        )
                    )
                }

                root.setOnClickListener {
                    selectedPosition = adapterPosition
                    notifyDataSetChanged()

                    onItemClick?.invoke(category)
                }
            }
        }
    }
}