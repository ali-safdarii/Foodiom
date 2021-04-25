package com.mehrsoft.foody.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehrsoft.foody.ui.viewmodels.MainViewModel
import com.mehrsoft.foody.R
import com.mehrsoft.foody.adapters.RecipesAdapter
import com.mehrsoft.foody.common.NetworkResult
import com.mehrsoft.foody.ui.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.*

@AndroidEntryPoint
class RecipesFragment : Fragment(R.layout.fragment_recipes) {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private lateinit var mAdapter:RecipesAdapter
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_recipes, container, false)


        requestApiData()

        return mView
    }

    private fun requestApiData() {
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    mAdapter = RecipesAdapter(response.data!!.results, requireContext())

                    recyclerView.hideShimmer()
                    response.data.let { mAdapter.setData(it) }
                    recyclerView.layoutManager=LinearLayoutManager(requireContext())
                    recyclerView.adapter=mAdapter


                }
                is NetworkResult.Error -> {
                    recyclerView.hideShimmer()
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

}