package com.esa.foodrecipes.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteItem(
    @PrimaryKey val id: String,
    val name: String?,
    val imageUrl: String?,
)