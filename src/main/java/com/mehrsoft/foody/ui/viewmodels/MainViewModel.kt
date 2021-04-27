package com.mehrsoft.foody.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.mehrsoft.foody.common.NetworkResult
import com.mehrsoft.foody.data.database.RecipesEntity
import com.mehrsoft.foody.data.repository.FoodRecipeRepository
import com.mehrsoft.foody.models.FoodRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(
    private val repository: FoodRecipeRepository, private val application: Application

) : ViewModel() {


    //Room Database
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readDatabase().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {

            repository.local.insertRecipes(recipesEntity)

        }


    //Retrofit
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    fun getRecipes(query: Map<String, String>) = viewModelScope.launch {

        getRecipesSafeCall(query)
    }

    private suspend fun getRecipesSafeCall(query: Map<String, String>) {

        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getFoodRecipes(query)
                recipesResponse.value = handleFoodResponse(response)

                val foodRecipe=recipesResponse.value!!.data

                if (foodRecipe!=null)
                    offlineCatchRecipes(foodRecipe)

            } catch (e: Exception) {

                recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCatchRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity=RecipesEntity(recipesFood = foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun handleFoodResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("Api Key Limited.")
            }

            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not Found.")
            }

            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }


    private fun hasInternetConnection(): Boolean {

        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false

        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false

        }

    }

}