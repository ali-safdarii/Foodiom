package com.mehrsoft.foody.adapters
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.mehrsoft.foody.R
import com.mehrsoft.foody.common.Constants
import com.mehrsoft.foody.common.Constants.Companion.TAG
import com.mehrsoft.foody.common.RecipesDiffUtil
import com.mehrsoft.foody.models.FoodRecipe
import com.mehrsoft.foody.models.Result
import com.mehrsoft.foody.ui.fragments.recipes.RecipesFragmentDirections
import kotlinx.android.synthetic.main.ingredients_row_layout.view.*
import org.jsoup.Jsoup


class RecipesAdapter() : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var result= emptyList<Result>()

    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {


        fun bind(result: Result){

            val titleTextView = itemView.findViewById<TextView>(R.id.title_textView)
            val descriptionTextView = itemView.findViewById<TextView>(R.id.description_textView)
            val heartTextView = itemView.findViewById<TextView>(R.id.heart_textView)
            val clockTextView = itemView.findViewById<TextView>(R.id.clock_textView)
            val leafTextView = itemView.findViewById<TextView>(R.id.leaf_textView)
            val recipeImageView = itemView.findViewById<ImageView>(R.id.recipe_imageView)
            val leafImageView = itemView.findViewById<ImageView>(R.id.leaf_imageView)


            itemView.apply {

                recipeImageView.load(result.image) {
                    crossfade(600)
                    error(R.drawable.error_placeholder)
                }

                titleTextView.text=result.title
                descriptionTextView.text= Jsoup.parse(result.summary).text()
                heartTextView.text= result.aggregateLikes.toString()
                clockTextView.text=result.readyInMinutes.toString()



                if(result.vegan){
                    leafTextView.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.green
                        )
                    )

                    leafImageView.setColorFilter(
                        ContextCompat.getColor(
                            context,
                            R.color.green
                        )
                    )
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater=LayoutInflater.from(parent.context).inflate(R.layout.recipes_row_layout,parent,false)
        return MyViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val recipeResult=result[position]
        holder.bind(recipeResult)

        holder.itemView.setOnClickListener {


            try {

                val action=RecipesFragmentDirections.
                actionRecipesFragmentToDetailsActivity(recipeResult)
                it.findNavController().navigate(action)

                Log.d(TAG, "onBindViewHolder: Called")

            }catch (e:Exception){

                Log.d(TAG, e.message.toString())
            }



        }
    }

    override fun getItemCount(): Int {
        return result.size
    }


    fun setData(newData: FoodRecipe){
        val recipesDiffUtil =
            RecipesDiffUtil(result, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        result = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }

}