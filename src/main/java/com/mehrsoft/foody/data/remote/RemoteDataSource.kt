package com.mehrsoft.foody.data.remote

import com.mehrsoft.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
        private val foodRecipesApi: FoodRecipesApi
) {


    suspend fun getFoodRecipes(queries:Map<String,String>):Response<FoodRecipe>{

        return foodRecipesApi.getRecipes(queries)

    }

}