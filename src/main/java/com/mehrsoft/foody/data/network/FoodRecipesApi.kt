package com.mehrsoft.foody.data.network

import com.mehrsoft.foody.models.FoodJoke
import com.mehrsoft.foody.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
   suspend fun getRecipes(
        @QueryMap queries: Map<String,String>
    ): Response<FoodRecipe>


    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
            @QueryMap searchQuery: Map<String, String>
    ): Response<FoodRecipe>


    @GET("food/jokes/random")
    suspend fun foodJoke(
        @Query("apiKey") apiKey: String
    ): Response<FoodJoke>


}