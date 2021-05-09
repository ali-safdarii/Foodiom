package com.mehrsoft.foody.ui.fragments.favorite

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehrsoft.foody.R
import com.mehrsoft.foody.adapters.FavoriteRecipesAdapter
import com.mehrsoft.foody.common.showToast
import com.mehrsoft.foody.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_recipes.*

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment(R.layout.fragment_favorite_recipes) {


    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter: FavoriteRecipesAdapter by lazy {
        FavoriteRecipesAdapter(
            requireActivity(),
            mainViewModel
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        favoriteRecipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        favoriteRecipesRecyclerView.adapter = mAdapter
        readFavorite()
    }

    private fun readFavorite() {
        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner, Observer { data ->

            if (data.isNullOrEmpty()) {
                no_data_textView.visibility = View.VISIBLE
                no_data_imageView.visibility = View.VISIBLE
            } else
                mAdapter.setData(data)
        })
    }


    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.all_favorite_delete_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAllFavoriteMenu) {
            mainViewModel.deleteAllFavoriteRecipes()
            requireActivity().showToast("All Favorite recipes removed. ")

        }
        return super.onOptionsItemSelected(item)
    }*/

    override fun onDestroy() {
        super.onDestroy()
        mAdapter.clearContextualActionMode()
    }



}