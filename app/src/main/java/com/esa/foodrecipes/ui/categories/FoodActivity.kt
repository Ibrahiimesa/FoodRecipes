package com.esa.foodrecipes.ui.categories

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.esa.foodrecipes.adapter.ListFoodAdapter
import com.esa.foodrecipes.data.Resource
import com.esa.foodrecipes.databinding.ActivityFoodBinding
import com.esa.foodrecipes.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodBinding
    private val vm: FoodViewModel by viewModels()
    private lateinit var foodAdapter: ListFoodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val categoryName = intent.getStringExtra("categoryName")
        val searchQuery = intent.getStringExtra("searchQuery")

        setupList()

        if (categoryName != null) {
            vm.fetchFoodList(categoryName)
        }

        if (searchQuery != null) {
            vm.fetchSearchFoodList(searchQuery)
        }
    }

    private fun setupList() {
        foodAdapter = ListFoodAdapter(emptyList())

        binding.rvFood.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            setHasFixedSize(true)
            adapter = foodAdapter
        }

        vm.foodList.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.meals?.let { foodAdapter.updateData(it) }
                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()

                }
            }
        }

        foodAdapter.onItemClick = { item ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("foodId", item.idMeal)
            startActivity(intent)
        }
    }
}