package com.mehrsoft.foody.ui.fragments.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import com.mehrsoft.foody.R
import com.mehrsoft.foody.common.Constants
import com.mehrsoft.foody.common.Constants.Companion.TAG
import com.mehrsoft.foody.models.Result
import kotlinx.android.synthetic.main.fragment_overview.view.*

import org.jsoup.Jsoup
import java.util.zip.Inflater

class OverviewFragment : Fragment(R.layout.fragment_overview) {

    private lateinit var result: Result
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments!=null){
            val args=arguments
            result= args!!.getParcelable(Constants.RECIPE_BUNDLE)!!

            Log.d(TAG, "onCreate: ${result.toString()}")
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view=inflater.inflate(R.layout.fragment_overview,container,false)

        view.main_imageView.load(result.image)
        view.title_textView.text = result.title
        view.likes_textView.text = result.aggregateLikes.toString()
        view.time_textView.text = result.readyInMinutes.toString()
        result.summary.let {
            val summary = Jsoup.parse(it).text()
            view.summary_textView.text = summary
        }

        if(result.vegetarian){
            view.vegetarian_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.vegetarian_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(result.vegan){
            view.vegan_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.vegan_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(result.glutenFree){
            view.gluten_free_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.gluten_free_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(result.dairyFree){
            view.dairy_free_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.dairy_free_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(result.veryHealthy){
            view.healthy_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.healthy_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(result.cheap){
            view.cheap_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.cheap_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        return view
    }

    }



