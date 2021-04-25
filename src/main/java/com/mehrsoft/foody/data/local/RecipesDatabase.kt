package com.mehrsoft.foody.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
        entities = [RecipesEntity::class],
        version = 1,
        exportSchema = false
)

//@TypeConverter(RecipesTypeConverter::class)
abstract class RecipesDatabase:RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

}