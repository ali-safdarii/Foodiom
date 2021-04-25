package com.mehrsoft.foody.data.local

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter

@Database(
        entities = [RecipesEntity::class],
        version = 1,
        exportSchema = false
)


abstract class RecipesDatabase() :RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

}