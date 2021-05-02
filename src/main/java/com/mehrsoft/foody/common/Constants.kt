package com.mehrsoft.foody.common

class Constants {

    companion object {

        const val TAG="Foody"
        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY = "1d6a54054d344c98a82159c719c667dd"


        // API Query Keys
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"


        //ROOM DATABASE

        const val DATABASE_NAME="recipes_database"
        const val TABLE_NAME="recipes_table"


        //Bottom Sheet and Preferences
        const val DEFAULT_MEAL_TYPE="main_course"
        const val DEFAULT_DIET_TYPE="gluten_free"
        const val DEFAULT_RECIPE_NUMBER="50"

        const val PREFERENCES_NAME = "foody_preferences"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCES_BACK_ONLINE = "back_online"
    }

}