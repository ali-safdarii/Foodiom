package com.mehrsoft.foody.data

import com.mehrsoft.foody.data.network.FoodRecipesApi
import com.mehrsoft.foody.models.FoodJoke
import com.mehrsoft.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
        private val foodRecipesApi: FoodRecipesApi
) {


    suspend fun getFoodRecipes(queries:Map<String,String>):Response<FoodRecipe>{

        return foodRecipesApi.getRecipes(queries)

    }


    suspend fun searchRecipes(queries:Map<String,String>):Response<FoodRecipe>{

        return foodRecipesApi.searchRecipes(queries)
    }


    suspend fun foodJoke(apikey:String):Response<FoodJoke>{
        return foodRecipesApi.foodJoke(apikey)
    }

}