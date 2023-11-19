package com.esa.foodrecipes.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.esa.foodrecipes.data.FavoriteRepository
import com.esa.foodrecipes.data.room.FavoriteItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository) :
    ViewModel() {
    val favorites: LiveData<List<FavoriteItem>> = repository.favorites.asLiveData()

    fun insertFavorite(favoriteItem: FavoriteItem) {
        viewModelScope.launch {
            repository.insertFavorite(favoriteItem)
        }
    }

    fun deleteFavorite(favoriteItem: FavoriteItem) {
        viewModelScope.launch {
            repository.deleteFavorite(favoriteItem)
        }
    }
}