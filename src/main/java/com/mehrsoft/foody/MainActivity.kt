package com.mehrsoft.foody

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mehrsoft.foody.common.Constants.Companion.TAG
import com.mehrsoft.foody.common.NetworkListener
import com.mehrsoft.foody.ui.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    lateinit var networkListener: NetworkListener
    private lateinit var recipesViewModel: RecipesViewModel
    private lateinit var bannerView:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bannerView=findViewById(R.id.bannerLayout)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.btnNavView)
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
                setOf(R.id.recipesFragment, R.id.favoriteRecipesFragment, R.id.foodJokeFragment)

        )

        bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)


        recipesViewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)
        networkListener = NetworkListener()

        recipesViewModel.readBackOnline.observe(this, {

            recipesViewModel.backOnline = it
        })



        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(this@MainActivity)
                    .collect { status ->
                        recipesViewModel.networkStatus = status

                         val status=recipesViewModel.showNetworkStatus()

                        recipesViewModel.showNetworkStatus()

                        if (status)
                            bannerView.visibility=View.GONE
                        else
                            bannerView.visibility=View.VISIBLE

                        Log.d(TAG, "status: ${status.toString()}")

                    }

        }




    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }

}