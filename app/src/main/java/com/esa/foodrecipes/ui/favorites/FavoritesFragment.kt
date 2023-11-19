package com.esa.foodrecipes.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.esa.foodrecipes.adapter.FavoriteAdapter
import com.esa.foodrecipes.databinding.FragmentFavoritesBinding
import com.esa.foodrecipes.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val vmFavorite: FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFavorite()
    }

    private fun setupFavorite() {
        favoriteAdapter = FavoriteAdapter(emptyList())

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            setHasFixedSize(true)
            adapter = favoriteAdapter
        }

        favoriteAdapter.onItemClick = { item ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("foodId", item.id)
            startActivity(intent)
        }

        vmFavorite.favorites.observe(viewLifecycleOwner) { favorites ->
            favoriteAdapter.updateData(favorites)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}