package com.esa.foodrecipes.data

import com.esa.foodrecipes.data.room.FavoriteDao
import com.esa.foodrecipes.data.room.FavoriteItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(private val favoriteDao: FavoriteDao) {
    val favorites: Flow<List<FavoriteItem>> = favoriteDao.getFavorites()

    suspend fun insertFavorite(favoriteItem: FavoriteItem) {
        favoriteDao.insertFavorite(favoriteItem)
    }

    suspend fun deleteFavorite(favoriteItem: FavoriteItem) {
        favoriteDao.deleteFavorite(favoriteItem)
    }
}