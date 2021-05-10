package com.mehrsoft.foody.ui.fragments.foodjoke

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mehrsoft.foody.R
import com.mehrsoft.foody.common.*
import com.mehrsoft.foody.common.Constants.Companion.API_KEY
import com.mehrsoft.foody.common.Constants.Companion.TAG
import com.mehrsoft.foody.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_food_joke.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FoodJokeFragment : Fragment(R.layout.fragment_food_joke) {

    private lateinit var mainViewModel: MainViewModel

    private var foodJoke_Defualt = "No Food Joke"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner,
            { response ->

                when (response) {
                    is NetworkResult.Success -> {

                        if (response.data != null) {
                            foodJoke_Defualt = response.data.text
                            food_joke_textView.text = response.data!!.text
                            foodJoke_cardView.makeVisible()
                            foodJokeProgressBar.makeInvisible()

                        }

                    }
                    is NetworkResult.Error -> {
                        loadDataFromCache()
                        requireActivity().showToast(response.message!!)
                        foodJoke_cardView.makeGone()
                    }
                    is NetworkResult.Loading -> {
                        Log.d(TAG, "Loading")

                        foodJoke_cardView.makeGone()
                        foodJokeProgressBar.makeVisible()

                    }
                }
            })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.food_joke_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.shareFoodJokeMenu) {

            val shareIntent = Intent().apply {

                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, foodJoke_Defualt)
                this.type = "text/plain"

            }

            startActivity(shareIntent)

        }


        return super.onOptionsItemSelected(item)
    }


    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readFoodJoke.observe(viewLifecycleOwner, { database ->
                if (!database.isNullOrEmpty()) {
                    food_joke_textView.text = database[0].foodJoke.text
                    foodJoke_Defualt = database[0].foodJoke.text

                    foodJoke_cardView.makeVisible()
                    foodJokeProgressBar.makeInvisible()

                }
            })
        }
    }

}