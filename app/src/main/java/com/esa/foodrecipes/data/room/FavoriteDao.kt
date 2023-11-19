package com.esa.foodrecipes.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteItem: FavoriteItem)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<FavoriteItem>>

    @Delete
    suspend fun deleteFavorite(favoriteItem: FavoriteItem)
}