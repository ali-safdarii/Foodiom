package com.mehrsoft.foody.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mehrsoft.foody.models.FoodRecipe
import com.mehrsoft.foody.models.Result
class RecipesTypeConverter {

  private  var gson:Gson= Gson()

    @TypeConverter
    fun foodRecipesToString(foodRecipe: FoodRecipe):String{

        return gson.toJson(foodRecipe)
    }



    @TypeConverter
    fun stringToFoodRecipe(data:String):FoodRecipe{

        val listType=object :TypeToken<FoodRecipe>(){}.type
        return gson.fromJson(data,listType)
    }

    @TypeConverter
    fun resultToString(result: Result): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(data: String):  Result{
        val listType = object : TypeToken<Result>() {}.type
        return gson.fromJson(data, listType)
    }
}