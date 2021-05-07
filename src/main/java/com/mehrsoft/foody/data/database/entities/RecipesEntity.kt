package com.mehrsoft.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mehrsoft.foody.common.Constants.Companion.TABLE_NAME
import com.mehrsoft.foody.models.FoodRecipe


@Entity(tableName = TABLE_NAME)
class RecipesEntity(
        var recipesFood:FoodRecipe

)

{

    @PrimaryKey(autoGenerate = false)
    var id:Int=0
}