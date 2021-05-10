package com.mehrsoft.foody.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mehrsoft.foody.data.database.dao.RecipesDao
import com.mehrsoft.foody.data.database.entities.FavoritesEntity
import com.mehrsoft.foody.data.database.entities.FoodJokeEntity
import com.mehrsoft.foody.data.database.entities.RecipesEntity

@Database(
        entities = [RecipesEntity::class,FavoritesEntity::class,FoodJokeEntity::class],
        version = 1,
        exportSchema = false
)

@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase() :RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

}