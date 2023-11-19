package com.esa.foodrecipes.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteItem::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}