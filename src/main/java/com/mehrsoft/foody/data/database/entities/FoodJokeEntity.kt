package com.mehrsoft.foody.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mehrsoft.foody.common.Constants.Companion.FOOD_JOKE_TABLE


import com.mehrsoft.foody.models.FoodJoke

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}