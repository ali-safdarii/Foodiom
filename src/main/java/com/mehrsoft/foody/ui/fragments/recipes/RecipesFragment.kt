package com.mehrsoft.foody.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.*

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mehrsoft.foody.R
import com.mehrsoft.foody.adapters.IngredientsAdapter
import com.mehrsoft.foody.adapters.RecipesAdapter
import com.mehrsoft.foody.common.Constants.Companion.TAG
import com.mehrsoft.foody.common.NetworkListener
import com.mehrsoft.foody.common.NetworkResult
import com.mehrsoft.foody.common.observeOnce
import com.mehrsoft.foody.models.FoodRecipe
import com.mehrsoft.foody.models.Result
import com.mehrsoft.foody.ui.viewmodels.MainViewModel
import com.mehrsoft.foody.ui.viewmodels.RecipesViewModel
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipesFragment : Fragment(R.layout.fragment_recipes), SearchView.OnQueryTextListener {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    //private lateinit var mAdapter: RecipesAdapter
    private lateinit var mView: View
    private lateinit var recyclerView: ShimmerRecyclerView
    lateinit var result: List<Result>

    private val args by navArgs<RecipesFragmentArgs>()
    private lateinit var networkStatusLayout: View
    private val mAdapter: RecipesAdapter by lazy { RecipesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_recipes, container, false)
        networkStatusLayout = mView.findViewById(R.id.networkStatusLayout);
        recyclerView = mView.findViewById<ShimmerRecyclerView>(R.id.recyclerView)
        val recipesFab=mView.findViewById<FloatingActionButton>(R.id.recipesFab)



        readDatabase()

        recipesFab.setOnClickListener {

            if(recipesViewModel.networkStatus)
            findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            else
                recipesViewModel.showNetworkStatus()
        }
        return mView
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu,menu)

        val  search=menu.findItem(R.id.menu_search)
        val searchView=search.actionView as? SearchView

        searchView?.isSubmitButtonEnabled=true
        searchView?.setOnQueryTextListener(this)

    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun readDatabase() {

        mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, { db ->

            if (db.isNotEmpty() && !args.backFromBottomSheet) {
                Log.d(TAG, "readDatabase Called")
                recyclerView.hideShimmer()
                mAdapter.setData(db[0].recipesFood)
                setupRecyclerView()

            } else {
                requestApiData()
            }
        })
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = mAdapter
        recyclerView.hideShimmer()
    }

    private fun requestApiData() {
        Log.d(TAG, "requestApiData: ")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {

                    showNetworkStatus(response)
                    result = response.data!!.results
                    //mAdapter = RecipesAdapter(result, requireContext())
                    response.data.let { mAdapter.setData(it) }
                    setupRecyclerView()
                }
                is NetworkResult.Error -> {
                    showNetworkStatus(response)
                    //recyclerView.hideShimmer()
                    Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                    ).show()

                    loadDataFromCache()
                }
                is NetworkResult.Loading -> {
                    //recyclerView.showShimmer()
                    showNetworkStatus(response)
                }
            }
        })
    }

    private fun searchApiData(searchQuery:String){
        recyclerView.showShimmer()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchedRecipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    recyclerView.hideShimmer()
                    val foodRecipe = response.data
                    foodRecipe?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    recyclerView.hideShimmer()
                    loadDataFromCache()
                    Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    recyclerView.showShimmer()
                }
            }
        })
    }
    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].recipesFood)

                }
            })
        }
    }


    private fun showNetworkStatus(apiResponse:NetworkResult<FoodRecipe>?) {
        networkStatusLayout.visibility = View.VISIBLE

        when (apiResponse) {
            is NetworkResult.Error -> {
                networkStatusLayout.visibility = View.VISIBLE
                recyclerView.hideShimmer()
                Log.d(TAG, "showNetworkStatus: Error")

            }
            is NetworkResult.Success -> {
                networkStatusLayout.visibility = View.INVISIBLE
                recyclerView.hideShimmer()
                Log.d(TAG, "showNetworkStatus:Success ")
            }
            is NetworkResult.Loading -> {
                networkStatusLayout.visibility = View.INVISIBLE
                recyclerView.showShimmer()

                Log.d(TAG, "showNetworkStatus: Loading")
            }
        }

    }

}