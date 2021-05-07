package com.mehrsoft.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

import com.mehrsoft.foody.common.Constants.Companion.FAVORITE_RECIPES_TABLE
import com.mehrsoft.foody.models.Result

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)