package com.esa.foodrecipes.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.esa.foodrecipes.adapter.AreaCategoriesAdapter
import com.esa.foodrecipes.adapter.CategoriesAdapter
import com.esa.foodrecipes.adapter.ListFoodAdapter
import com.esa.foodrecipes.data.Resource
import com.esa.foodrecipes.databinding.FragmentHomeBinding
import com.esa.foodrecipes.ui.categories.FoodActivity
import com.esa.foodrecipes.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val vm: HomeViewModel by viewModels()
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var listFoodAdapter: ListFoodAdapter
    private lateinit var areaCategoriesAdapter: AreaCategoriesAdapter
    private var area: String? = "American"
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearch()
        setupCategoriesArea()
        setUpFoodArea()
        setUpCategories()
    }

    private fun setupSearch() {
        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch(query)
                searchView.clearFocus()
                searchView.setQuery("", false)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun performSearch(query: String?) {
        val intent = Intent(requireContext(), FoodActivity::class.java)
        intent.putExtra("searchQuery", query)
        startActivity(intent)
    }

    private fun setupCategoriesArea() {
        areaCategoriesAdapter = AreaCategoriesAdapter(emptyList())

        binding.rvCategoryArea.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            setHasFixedSize(true)
            adapter = areaCategoriesAdapter
        }

        vm.foodAreaCategories.observe(viewLifecycleOwner) { data ->
            try {
                data.meals?.let { areaCategoriesAdapter.updateData(it) }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error submitting list data", e)
            }
        }

        areaCategoriesAdapter.onItemClick = { item ->
            area = item.strArea

            item.strArea?.let { vm.fetchFoodArea(it) }
        }

        vm.fetchCategoriesArea()
    }

    private fun setUpFoodArea() {
        listFoodAdapter = ListFoodAdapter(emptyList())

        binding.rvFoodArea.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            setHasFixedSize(true)
            adapter = listFoodAdapter
        }

        vm.foodListArea.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.progressBarFood.visibility = View.GONE
                    binding.rvFoodArea.visibility = View.VISIBLE
                    resource.data?.meals?.let { listFoodAdapter.updateData(it) }
                }

                is Resource.Loading -> {
                    binding.rvFoodArea.visibility = View.INVISIBLE
                    binding.progressBarFood.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.progressBarFood.visibility = View.GONE
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        listFoodAdapter.onItemClick = { item ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("foodId", item.idMeal)
            startActivity(intent)
        }
        area?.let { vm.fetchFoodArea(it) }
    }

    private fun setUpCategories() {
        categoriesAdapter = CategoriesAdapter(emptyList())

        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, LinearLayoutManager.HORIZONTAL, false)

            setHasFixedSize(true)
            adapter = categoriesAdapter
        }

        vm.foodCategories.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.progressBarCategories.visibility = View.GONE
                    resource.data?.categories?.let { categoriesAdapter.updateData(it) }
                }

                is Resource.Loading -> {
                    binding.progressBarCategories.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.progressBarCategories.visibility = View.GONE
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        categoriesAdapter.onItemClick = { item ->
            val intent = Intent(activity, FoodActivity::class.java)
            intent.putExtra("categoryName", item.strCategory)
            startActivity(intent)
        }
        vm.fetchCategories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}