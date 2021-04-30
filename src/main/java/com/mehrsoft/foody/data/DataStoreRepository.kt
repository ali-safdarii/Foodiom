package com.mehrsoft.foody.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mehrsoft.foody.common.Constants.Companion.DEFAULT_DIET_TYPE
import com.mehrsoft.foody.common.Constants.Companion.DEFAULT_MEAL_TYPE
import com.mehrsoft.foody.common.Constants.Companion.PREFERENCES_DIET_TYPE
import com.mehrsoft.foody.common.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.mehrsoft.foody.common.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.mehrsoft.foody.common.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.mehrsoft.foody.common.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)

    }

    suspend fun saveMealAndDietType(mealType: String,
                                    mealTypeId: Int,
                                    dietType: String,
                                    dietTypeId: Int) {
        context.dataStore.edit { preferences ->

            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }


    val readMealAndDietType: Flow<MealAndDietType> = context.dataStore.data
            .map { preferences ->
                // No type safety.
                val selectedMealType = preferences[PreferenceKeys.selectedMealType]
                        ?: DEFAULT_MEAL_TYPE
                val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
                val selectedDietType = preferences[PreferenceKeys.selectedDietType]
                        ?: DEFAULT_DIET_TYPE
                val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
                MealAndDietType(
                        selectedMealType,
                        selectedMealTypeId,
                        selectedDietType,
                        selectedDietTypeId
                )
            }


    data class MealAndDietType(
            val selectedMealType: String,
            val selectedMealTypeId: Int,
            val selectedDietType: String,
            val selectedDietTypeId: Int
    )
}








