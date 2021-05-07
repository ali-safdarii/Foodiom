package com.mehrsoft.foody.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehrsoft.foody.R
import com.mehrsoft.foody.adapters.IngredientsAdapter
import com.mehrsoft.foody.common.Constants.Companion.RECIPE_BUNDLE
import com.mehrsoft.foody.models.ExtendedIngredient
import com.mehrsoft.foody.models.Result
import kotlinx.android.synthetic.main.fragment_ingredients.*


class IngredientsFragment : Fragment(R.layout.fragment_ingredients) {


    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val myBundle:Result = requireArguments().getParcelable(RECIPE_BUNDLE)!!

        ingredients_recyclerview.adapter = mAdapter
        ingredients_recyclerview.layoutManager = LinearLayoutManager(requireContext())

        myBundle.extendedIngredients?.let { mAdapter.setData(it) }

    }


}