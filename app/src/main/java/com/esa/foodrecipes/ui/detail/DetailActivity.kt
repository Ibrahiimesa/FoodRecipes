package com.esa.foodrecipes.ui.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.esa.foodrecipes.R
import com.esa.foodrecipes.data.response.DetailItem
import com.esa.foodrecipes.data.room.FavoriteItem
import com.esa.foodrecipes.databinding.ActivityDetailBinding
import com.esa.foodrecipes.ui.favorites.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val vm: DetailViewModel by viewModels()
    private val vmFavorite: FavoriteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val foodId = intent.getStringExtra("foodId")

        setupView()

        if (foodId != null) {
            vm.fetchMealDetails(foodId)
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupView() {
        vm.foodDetail.observe(this) { detailsResponse ->
            detailsResponse?.meals?.get(0)?.let { data ->
                updateUI(data)
            }
        }
    }

    private fun updateUI(data: DetailItem) {
        Glide.with(this)
            .load(data.strMealThumb)
            .placeholder(R.drawable.baseline_food_bank_24)
            .into(binding.imgDetail)

        binding.apply {
            tvTitleDetail.text = data.strMeal
            tvCategory.text = data.strCategory
            tvInstructionsDetail.text = data.strInstructions
            btnYoutube.setOnClickListener {
                openYoutubeVideo(data.strYoutube)
            }
            val favoriteItem = data.idMeal?.let { it1 ->
                FavoriteItem(
                    id = it1,
                    name = data.strMeal,
                    imageUrl = data.strMealThumb,
                )
            }

            var favoriteState = vmFavorite.favorites.value?.any { it.id == data.idMeal } == true
            setFavorite(favoriteState)

            binding.imgFavorite.setOnClickListener {
                favoriteState = !favoriteState
                setFavorite(favoriteState)

                if (favoriteState) {
                    if (favoriteItem != null) {
                        vmFavorite.insertFavorite(favoriteItem)
                    }
                    Snackbar.make(binding.root, "Added to favorites", Snackbar.LENGTH_SHORT).show()
                } else {
                    if (favoriteItem != null) {
                        vmFavorite.deleteFavorite(favoriteItem)
                    }
                    Snackbar.make(binding.root, "Removed from favorites", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setFavorite(state: Boolean) {
        binding.imgFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                if (state) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
            )
        )
    }

    private fun openYoutubeVideo(youtubeUrl: String?) {
        if (!youtubeUrl.isNullOrBlank()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Unable to open YouTube", Toast.LENGTH_SHORT).show()
            }
        }
    }

}