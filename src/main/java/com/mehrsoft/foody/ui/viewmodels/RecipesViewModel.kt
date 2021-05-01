package com.mehrsoft.foody.ui.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mehrsoft.foody.common.Constants.Companion.API_KEY
import com.mehrsoft.foody.common.Constants.Companion.DEFAULT_DIET_TYPE
import com.mehrsoft.foody.common.Constants.Companion.DEFAULT_MEAL_TYPE

import com.mehrsoft.foody.common.Constants.Companion.DEFAULT_RECIPE_NUMBER
import com.mehrsoft.foody.common.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.mehrsoft.foody.common.Constants.Companion.QUERY_API_KEY
import com.mehrsoft.foody.common.Constants.Companion.QUERY_DIET
import com.mehrsoft.foody.common.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.mehrsoft.foody.common.Constants.Companion.QUERY_NUMBER
import com.mehrsoft.foody.common.Constants.Companion.QUERY_TYPE
import com.mehrsoft.foody.data.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(application: Application, private val dataStoreRepository: DataStoreRepository) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()
    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }


    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = DEFAULT_RECIPE_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = DEFAULT_MEAL_TYPE
        queries[QUERY_DIET] = DEFAULT_DIET_TYPE
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    private fun saveBackOnline(backOnline: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveBackOnline(backOnline)
    }

    fun showNetworkStatus():Boolean {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection. ", Toast.LENGTH_LONG).show()
            saveBackOnline(true)

        }else if(networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "Internet Connection is Available. ", Toast.LENGTH_LONG)
                    .show()
                saveBackOnline(false)
            }
        }

        return networkStatus
    }

}