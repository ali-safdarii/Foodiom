package com.mehrsoft.foody.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mehrsoft.foody.models.FoodRecipe

class RecipesTypeConverter {

    var gson:Gson= Gson()

    @TypeConverter
    fun foodRecipesToString(foodRecipe: FoodRecipe):String{

        return gson.toJson(foodRecipe)
    }



    @TypeConverter
    fun stringToFoodRecipe(data:String):FoodRecipe{

        val listType=object :TypeToken<FoodRecipe>(){}.type
        return gson.fromJson(data,listType)
    }
}